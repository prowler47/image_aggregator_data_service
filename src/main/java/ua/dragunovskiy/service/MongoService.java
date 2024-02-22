package ua.dragunovskiy.service;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.StringCodec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.dragunovskiy.pojo.Person;
import ua.dragunovskiy.pojo.TestPojo;

import java.util.List;

@Service
public class MongoService {

    private final String mongoURI = "mongodb+srv://sdragunovskiy:Sdragunovskiy123@cluster0.3j4xorl.mongodb.net/?retryWrites=true&w=majority";

    public void insert() {
        MongoClient mongoClient = MongoClients.create(mongoURI);
        MongoDatabase database = mongoClient.getDatabase("Test_database");
        MongoCollection<Document> collection = database.getCollection("Test_collection");
        Document document = new Document("name", "some string");
        collection.insertOne(document);
    }

    public void insertObject() {
        ConnectionString connectionString = new ConnectionString(mongoURI);
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();

        MongoClient mongoClient = MongoClients.create(clientSettings);
        MongoDatabase database = mongoClient.getDatabase("Test_database");
        MongoCollection<TestPojo> collection = database.getCollection("Test_collection", TestPojo.class);
        Person person = new Person();
        TestPojo testPojo = new TestPojo();
        testPojo.setName("Test");
//        person.setPersonId(1212);
        collection.insertOne(testPojo);

    }

    public void insertUrls(List<String> urls) {
        MongoClient mongoClient = MongoClients.create(mongoURI);
        MongoDatabase database = mongoClient.getDatabase("URLs_database");
        MongoCollection<Document> collection = database.getCollection("URLs_collection");
        for (String url : urls) {
            Document document = new Document(url, url);
            collection.insertOne(document);
        }
    }
}
