package collection.interfaces;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by charlotte on 22/02/17.
 */
public class FileService {

    public Optional<List<String>> readFile(URI uri){
    	Optional<List<String>> value = Optional.empty();

		try (Stream<String> stream = Files.lines(Paths.get(uri))) {
			List<String> list = stream
					.collect(Collectors.toList()); // pour en récup une liste
			value = Optional.of(list);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return value;
    }

    public void writeFile(URI url, String valueToWrite) throws IOException, URISyntaxException {
        Files.write(Paths.get(url), valueToWrite.getBytes());
        //Files.write(Paths.get(url), valueToWrite.getBytes());
        // dans les faits, à la fin on faisait ça :
		/*
        URI outputFile = new File(new File(newUrl.toURI()).getParentFile().toURI().getPath()+"resultat.txt").toURI();
		Files.write(Paths.get(outputFile), valueToWrite.toString().getBytes());
		*/
        // mais c'était parce qu'il fallait concaténer le nom du fichier. On garde ça sous le coude
    }



}
