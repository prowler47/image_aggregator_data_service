package ua.dragunovskiy.loader;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ImageLoader {
    void download(List<String> urlsList, String key, String directory);
}
