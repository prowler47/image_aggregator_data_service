package ua.dragunovskiy.loader;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LoaderTest {

   @Mock
   ImageLoaderToFile imageLoader = new ImageLoaderToFile();

    static List<String> urls;
    static List<String> notValidUrls;
    static String directory;

    @BeforeAll
    static void init() {
        urls = new ArrayList<>();
        notValidUrls = new ArrayList<>();
        urls.add("https://img.freepik.com/premium-photo/waterfall-jungle-tropical-forest-generative-ai_845977-1028.jpg");
        notValidUrls.add("https://avatar.freepik.co/default_03.png");
        directory = "/Users/mac/Documents/tmp";
    }

    @Test
    void downloadFileSuccessTest() {
        imageLoader.download(urls, "", directory);
        File dir = new File(directory);
        File[] files = dir.listFiles();
        assertTrue(files[0].length() > 0);
    }

    @Test
    void downloadFileWithKeySuccessTest() {
        String key = "waterfall";
        imageLoader.download(urls, key, directory);
        File dir = new File(directory + "/" + key);
        assertTrue(dir.listFiles().length > 0);
    }

    @Test
    void downloadFileInvalidUrl() {
        imageLoader.download(notValidUrls, "", directory);
        File dir = new File(directory);
        assertEquals(0, dir.listFiles().length);
    }
}
