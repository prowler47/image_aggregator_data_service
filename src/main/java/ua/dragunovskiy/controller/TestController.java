package ua.dragunovskiy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.dragunovskiy.service.MongoService;

@RestController
@RequestMapping("/")
public class TestController {
    @Autowired
    private MongoService mongoService;

    @GetMapping("/insert")
    public String test() {
        mongoService.insert();
        return "Success";
    }

    @GetMapping("/insertObject")
    public String testForObject() {
        mongoService.insertObject();
        return "Success";
    }
}
