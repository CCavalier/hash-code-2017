package collection.interfaces;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntelligenceCache {
	
	public static Map<Integer, Set<Integer>> cacheToVideos = new HashMap<Integer, Set<Integer>>();
	
	public static void doTheSmartThing() {
		// il faudrait une liste de choses qu'il nous reste à traiter ; 
		// 		- genre virer les vidéos trop grandes pour les caches 
		//		- et les endpoint reliés à aucun cache
		
		// on va remplir les vidéos
		Integer cacheID = 0;
		for(int videoID = 0; videoID<App.videoSize.size();videoID++) {
			if(estCeQueCaRentre(cacheID, videoID)) {
				addVideoToCache(cacheID, videoID);
			}else {
				if(cacheID < App.nbCache - 1) {
					cacheID++;
				}else {
					cacheID=0;
				}
			}
		}
	}
	
	// -------------------- garbage utils methods ----------------------------
	
	public static boolean estCeQueCaRentre(Integer cacheID, Integer videoID) {
		Integer tailleRestanteDuCache = App.cacheSize.get(cacheID);
		
		return tailleRestanteDuCache >= App.videoSize.get(videoID);
	}
	
	/**
	 * on ajoute la video videoID au cache cacheID
	 * @param videoID vidéo à ajouter
	 * @param cacheID cache auquel ajouter la vidéo
	 */
	public static void addVideoToCache(Integer cacheID, Integer videoID) {
		initList(cacheID); // toujours commencer par ça, Justin Case
		// on ajoute la vidéo à la liste de résultats
		cacheToVideos.get(cacheID).add(videoID);
		
		// on réduit la taille disponible du cache en question
		Integer tailleRestanteDuCache = App.cacheSize.get(cacheID);
		tailleRestanteDuCache -= App.videoSize.get(videoID);
		App.cacheSize.set(cacheID, tailleRestanteDuCache);
	}
	
	private static void initList(Integer cacheID) {
		if(cacheToVideos.get(cacheID) == null) {
			cacheToVideos.put(cacheID, new HashSet<Integer>());
		}
	}
}
