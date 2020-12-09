package donjinkrawler;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class FileUtils {

    public static List<URL> getResourceFolderFiles(String folder) throws URISyntaxException, IOException {
        List<URL> urls = new ArrayList<>();
        URI uri = Thread.currentThread().getContextClassLoader().getResource(folder).toURI();
        Path myPath;
        if (uri.getScheme().equals("jar")) {

            FileSystem fileSystem;
            try {
                fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
            } catch (FileSystemAlreadyExistsException e) {
                fileSystem = FileSystems.getFileSystem(uri);
            }
            myPath = fileSystem.getPath("/" + folder);
        } else {
            myPath = Paths.get(uri);
        }
        Stream<Path> walk = Files.walk(myPath, 1);
        for (Iterator<Path> it = walk.iterator(); it.hasNext(); ) {
            Path path = it.next();
            if (!path.toString().equals("/" + folder)) {
                urls.add(path.toUri().toURL());
            }
        }
        return urls;
    }

}
