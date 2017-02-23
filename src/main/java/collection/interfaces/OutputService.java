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

	private static String chaine = "";
	
	public static void writeOutAllTheThings() throws URISyntaxException {
		String aEcrire = "";
		String entryFileName = App.FILE_CHOOSEN;
		String entryFileNamePure = entryFileName.substring(0, entryFileName.indexOf("."));
		String outputFileName = entryFileNamePure + ".out";

		outputFile = FileService.generateURI(outputFileName);
		
		if(outputFile == null) {
			System.err.println("Le fichier de sortie a pas pu être créé!");
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
		
		cetteFoisEcris();
	}

	private static void ecrire(String valueToWrite) {
		chaine  += valueToWrite + "\n";
	}
	
	private static void cetteFoisEcris() {
		try {
			fileService.writeFile(outputFile, chaine);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
