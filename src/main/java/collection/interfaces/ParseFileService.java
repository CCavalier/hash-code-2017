package collection.interfaces;

import java.net.URI;
import java.util.Iterator;
import java.util.List;

public class ParseFileService {

	public static void parse(URI uri) {
		FileService fileService = new FileService();
		// on récupère l'optional
		List<String> list = fileService.readFile(uri).get();
		
		Iterator<String> iterator = list.iterator();

		String line = iterator.next();
		System.out.println(line);
		String[] firstLine = line.split("\\s+"); // découpage
		// do something with first line

		while(iterator.hasNext()) {
			line = iterator.next();
			System.out.println(line);
			// do something with the line
		}
	}

}
