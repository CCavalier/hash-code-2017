package collection.interfaces;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;

public class OutputService {
	
	private static FileService fileService = new FileService();
	
	private static URI outputFile;
	
	public static void writeOutAllTheThings() {
		String aEcrire = "";
		
		String entryFileName = "kittens.in";
		
		String entryFileNamePure = entryFileName.substring(0, entryFileName.indexOf("."));
		
		String outputFileName = entryFileNamePure + ".out";
				
        outputFile = null;
		try {
			outputFile = new File(new URI(outputFileName)).toURI();
		} catch (URISyntaxException e1) {
			// yoleau
			e1.printStackTrace();
		}
		
		// on écrit le nombre de caches qu'on a traité
		Integer nbCaches = IntelligenceCache.cacheToVideos.size();
		
		ecrire(nbCaches.toString());
		
		for (Map.Entry<Integer,Set<Integer>> entry : IntelligenceCache.cacheToVideos.entrySet())
		{
			// déjà on écrit le numéro du cache
			aEcrire = entry.getKey().toString() + " ";
			// ensuite l'id de chaque vidéo qu'il contient
			for(Integer videoID : entry.getValue()) {
				aEcrire += videoID + " ";
			}
			ecrire(aEcrire);
		}
	}
	
	private static void ecrire(String valueToWrite) {
		try {
			fileService.writeFile(outputFile, valueToWrite);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
