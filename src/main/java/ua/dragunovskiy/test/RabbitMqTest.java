package ua.dragunovskiy.test;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqTest {

    @RabbitListener(queues = "Test")
    public void getMessage(String message) {
        System.out.println(message);
    }
}
