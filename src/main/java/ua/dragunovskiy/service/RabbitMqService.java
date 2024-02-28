package ua.dragunovskiy.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RabbitMqService {
    @Autowired
    private MongoService mongoService;

    // this method listening RabbitMQ queue "Test" and read
    // appears messages, then put it into list and after
    // insertUrls(..) inserting this list to MongoDb collection
    @RabbitListener(queues = "Test")
    public void insertMessagesToMongoDB(String message) {
       List<String> listUrls = new ArrayList<>();
       listUrls.add(message);
       mongoService.insertUrls(listUrls);
    }
}
