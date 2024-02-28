package ua.dragunovskiy.pojo;

import lombok.Data;

// this is just simplest POJO class for testing insert custom objects to
// MongoDB collection. In this version it's no use, but it can be useful in
// future versions
@Data
public class TestPojo {
    private String name;
}
