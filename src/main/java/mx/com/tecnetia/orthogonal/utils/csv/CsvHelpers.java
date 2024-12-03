package mx.com.tecnetia.orthogonal.utils.csv;

import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log4j2
public class CsvHelpers {

    private CsvHelpers() {
    }

    public static Path fileOutOnePath(String pathStr) throws URISyntaxException {
        URI uri = ClassLoader.getSystemResource(pathStr).toURI();
        return Paths.get(uri);
    }

    public static String readFile(Path path) {
        String response = "";
        try(FileReader fr = new FileReader(path.toString()); BufferedReader br = new BufferedReader(fr)) {
            String strLine;
            StringBuilder sb = new StringBuilder();
            while ((strLine = br.readLine()) != null) {
                sb.append(strLine);
            }
            response = sb.toString();
        } catch (Exception ex) {
            log.error("Ocurrió un error:{}", ex.getMessage());
        }
        return response;
    }


}
