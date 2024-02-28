package ua.dragunovskiy.loader;

import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ImageLoaderToFile implements ImageLoader {

    // this method download binary data as file in format png, jpg and gif
    // to local directory
    @Override
    public void download(List<String> imageURLs, String key, String directory) {
        String suffix = "";
        for (String imageURL : imageURLs) {
            try {
                URL url = new URL(imageURL);
                InputStream inputStream = url.openStream();
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int read;
                byte[] data = new byte[5 * 1024 * 1024];
                while ((read = inputStream.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, read);
                }
                inputStream.close();
                buffer.flush();

                if (imageURL.endsWith(".png")) {
                    suffix = ".png";
                }
                if (imageURL.endsWith(".jpg")) {
                    suffix = ".jpg";
                }
                if (imageURL.endsWith(".gif")) {
                    suffix = ".gif";
                }
                Path path = Paths.get(directory + key);
                Files.createDirectories(path);

                File file = File.createTempFile(imageURL, suffix, new File(path.toString()));
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(buffer.toByteArray());
                fileOutputStream.close();

            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
}
