package ua.dragunovskiy.test;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.dragunovskiy.service.MongoService;

import java.util.ArrayList;
import java.util.List;

@Service
public class RabbitMqTest {

    @Autowired
    private MongoService mongoService;

//    @RabbitListener(queues = "Test")
//    public void getMessage(String message) {
//        List<String> listUrls = new ArrayList<>();
//        listUrls.add(message);
//        for (String URL : listUrls) {
//            System.out.println(URL);
//        }
//    }

    @RabbitListener(queues = "Test")
    public void insertMessagesToMongoDB(String message) {
       List<String> listUrls = new ArrayList<>();
       listUrls.add(message);
       mongoService.insertUrls(listUrls);
    }
}
