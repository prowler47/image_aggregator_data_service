package ua.dragunovskiy.loader;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ImageLoader {
    int download(List<String> urlsList, String key);
}
