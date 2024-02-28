package ua.dragunovskiy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.dragunovskiy.service.MongoService;

@RestController
@RequestMapping("/")
public class MongoController {
    @Autowired
    private MongoService mongoService;

    @GetMapping("/insertObject")
    public String testForObject() {
        mongoService.insertObject();
        return "Insert object is success";
    }

    @GetMapping("/download")
    public String findUrls() {
        mongoService.downloadImagesByUrls();
        return "Download images is success";
    }
}
