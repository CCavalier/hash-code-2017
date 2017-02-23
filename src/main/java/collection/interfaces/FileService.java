package collection.interfaces;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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
					.collect(Collectors.toList()); // pour en r�cup une liste
			value = Optional.of(list);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return value;
    }

    public void writeFile(URI url, String valueToWrite) throws IOException, URISyntaxException {
        Files.write(Paths.get(url), valueToWrite.getBytes());
        //Files.write(Paths.get(url), valueToWrite.getBytes());
        // dans les faits, � la fin on faisait �a :
		/*
        URI outputFile = new File(new File(newUrl.toURI()).getParentFile().toURI().getPath()+"resultat.txt").toURI();
		Files.write(Paths.get(outputFile), valueToWrite.toString().getBytes());
		*/
        // mais c'�tait parce qu'il fallait concat�ner le nom du fichier. On garde �a sous le coude
    }

	public static URI generateURI(String outputFileName) throws URISyntaxException {
		FileService fileService = new FileService();
		
		URL urlInputFile = fileService.getClass().getClassLoader().getResource("kittens.in");
		
        URI outputFile = new File(new File(urlInputFile.toURI()).toURI().getPath().substring(0, new File(urlInputFile.toURI()).toURI().getPath().lastIndexOf('.'))+".out").toURI();
        
		return outputFile;
	}



}
