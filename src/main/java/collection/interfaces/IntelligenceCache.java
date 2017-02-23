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
		//		- 
		
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
		
		// ici, on a rempli les vidéos aléatoirement
		// on va ignorer les vidéos inutiles
//		for(int videoID = 0; videoID < App.nbVideos ; videoID++) {
//			if(estVideoTropLourde(videoID)) {
//				
//			}
//		}
		
		// on va remplacer des vidéos inutiles par des vidéos random jusqu'à ce qu'il n'y ait plus de vidéo inutiles
		boolean isVideoInutile = true;
		while(isVideoInutile) {
			boolean changement = false;
			for(cacheID = 0 ; cacheID < App.nbCache ; cacheID++) {
				// on parcourt les vidéo
				for ( int videoID = 0; videoID < cacheToVideos.get(cacheID).size() ; videoID ++) {
					// on regarde si la vidéo courante est inutile
					if (estVideoInutile(videoID, cacheID)) {
						deleteVideo(videoID, cacheID);
						ajouterVideosRandom(cacheID);
						changement = true;
					}
				}
			}
			isVideoInutile = changement;
		}
	}
	
	// -------------------- garbage utils methods ----------------------------
	
	private static void ajouterVideosRandom(Integer cacheID) {
		for ( int videoID = 0; videoID < cacheToVideos.get(cacheID).size() ; videoID ++) {
			// on regarde si la vidéo courante est utile et pas déjà présente
			if (!estVideoInutile(videoID, cacheID) && !cacheToVideos.get(cacheID).contains(videoID)) {
				if(estCeQueCaRentre(cacheID, videoID)) {
					addVideoToCache(cacheID, videoID);
				}
			}
		}
	}

	private static boolean estVideoInutile(int videoID, Integer cacheID) {
		boolean res = true;
		for(int endpointID = 0 ; endpointID < App.nbEndPoint ; endpointID++) {
			if(ceEndpointestRelieACeCache(endpointID, cacheID)) {
				Endpoint endpoint = App.endpoints.get(endpointID);
				res = !endpoint.videoToRequests.containsKey(videoID);
				if (!res) {
					break;
				}
			}
		}
		return res ;
	}

	private static boolean ceEndpointestRelieACeCache(int endpointID, Integer cacheID) {
		boolean res = false;
		
		Endpoint endpoint = App.endpoints.get(endpointID);
		
		res = endpoint.cacheToLatency.containsKey(cacheID);

		return res;
	}

	private static boolean estVideoTropLourde(int videoID) {
		return App.videoSize.get(videoID) > App.initialCacheSize;
	}

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

	private static void deleteVideo(int videoID, Integer cacheID) {
		// on supprime la vidéo de la liste des résultats
		cacheToVideos.get(cacheID).remove(videoID);
		
		// on augmente la taille disponible du cache en question
		Integer tailleRestanteDuCache = App.cacheSize.get(cacheID);
		tailleRestanteDuCache += App.videoSize.get(videoID);
		App.cacheSize.set(cacheID, tailleRestanteDuCache);
	}
	
	private static void initList(Integer cacheID) {
		if(cacheToVideos.get(cacheID) == null) {
			cacheToVideos.put(cacheID, new HashSet<Integer>());
		}
	}
}
