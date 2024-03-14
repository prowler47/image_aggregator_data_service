package ua.dragunovskiy.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MongoDbServiceTest {

    private static final String mongoURI = "mongodb+srv://sdragunovskiy:Sdragunovskiy123@cluster0.3j4xorl.mongodb.net/?retryWrites=true&w=majority";
    private static List<String> urls;

    @BeforeAll
    static void init() {
        urls = new ArrayList<>();
        urls.add("https://avatar.cdnpk.net/default_01.png");
        urls.add("https://avatar.freepik.com/default_03.png");
        urls.add("https://img.freepik.com/free-psd/illustration-daily-scene-with-person-doing-activity_776063-12.jpg");
    }

    @Test
    void insertUrlsToMongoDbDataBaseTest() {
        MongoClient mongoClient = MongoClients.create(mongoURI);
        MongoDatabase database = mongoClient.getDatabase("URLs_database");
        MongoCollection<Document> testCollection = database.getCollection("Test");
        for (String url : urls) {
            Document document = new Document("", url);
            testCollection.insertOne(document);
        }
        List<Document> urlsFromMongoDB = testCollection.find().into(new ArrayList<>());
        List<String> processedUrls = new ArrayList<>();
        assertTrue(compareUrlsLists(urlsFromMongoDB, processedUrls, urls));
    }

    @AfterAll
    static void deleteTestCollectionElements() {
        MongoClient mongoClient = MongoClients.create(mongoURI);
        MongoDatabase database = mongoClient.getDatabase("URLs_database");
        MongoCollection<Document> testCollection = database.getCollection("Test");
        Document filter = new Document();
        testCollection.deleteMany(filter);
    }

    private static boolean compareUrlsLists(List<Document> urlsFromMongoDB, List<String> processedUrls, List<String> urls) {
        for (Document doc : urlsFromMongoDB) {
            int startIndex = doc.toString().indexOf("https:");
            String processedUrl = doc.toString().substring(startIndex).replace("}", "");
            processedUrls.add(processedUrl);
        }
        return urls.equals(processedUrls);
    }
}
