package ua.dragunovskiy.service;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.dragunovskiy.loader.ImageLoader;
import ua.dragunovskiy.pojo.TestPojo;

import java.util.ArrayList;
import java.util.List;

@Service
public class MongoService {
    @Autowired
    private ImageLoader imageLoader;

    @Autowired
    private RabbitMqService rabbitMqService;

    private final String mongoURI = "mongodb+srv://sdragunovskiy:Sdragunovskiy123@cluster0.3j4xorl.mongodb.net/?retryWrites=true&w=majority";
//    private String directory = "/Users/mac/Documents/tmp/";

    // insert list of parsing urls from RabbitMQ queue to MongoDB collection
    public void insertUrls(List<String> urls) {
        MongoClient mongoClient = MongoClients.create(mongoURI);
        MongoDatabase database = mongoClient.getDatabase("URLs_database");
        MongoCollection<Document> userDataCollection = database.getCollection("User_data");
        MongoCollection<Document> userDataHistoryCollection = database.getCollection("User_data_history");
        for (String url : urls) {
            Document document = new Document("", url);
            userDataCollection.insertOne(document);
            userDataHistoryCollection.insertOne(document);
        }
    }

    // this method get list of Document from MongoDB collection, process it into list
    // of urls (list of String) and download to local directory
    public void downloadImagesByUrls() {
        int count = 0;
        MongoClient mongoClient = MongoClients.create(mongoURI);
        MongoDatabase database = mongoClient.getDatabase("URLs_database");
        MongoCollection<Document> collection = database.getCollection("User_data");
        List<Document> urlsList = collection.find().into(new ArrayList<>());
        List<String> urlsStringList = new ArrayList<>();
        for (Document document : urlsList) {
            String doc = document.toString();
            int startIndex = doc.indexOf("https");
            String url = doc.substring(startIndex);
            url = url.replace("}", "");
            urlsStringList.add(url);
//            System.out.println(url);
            count++;
        }
        imageLoader.download(urlsStringList, "", rabbitMqService.getDirectory());
        Document filter = new Document();
        collection.deleteMany(filter);
        System.out.println(count);
    }

    // it's a test method which insert some object to MongoDB collection. In this version it's not use, but
    // maybe it will use in some future versions
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
        TestPojo testPojo = new TestPojo();
        testPojo.setName("Test");
        collection.insertOne(testPojo);
    }
}
