package collection.interfaces;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
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
		for(cacheID = 0 ; cacheID < App.nbCache ; cacheID++) {
			Set<Integer>videos =cacheToVideos.get(cacheID);
			Set<Integer>videosCp=new HashSet<>(cacheToVideos.get(cacheID));
			for (int videoID:videosCp){
				boolean isUseless = false;
				for (Endpoint endpoint:App.endpoints) {
					isUseless = !(endpoint.cacheToLatency.containsKey(cacheID) && endpoint.videoToRequests.containsKey(videoID));
					if(isUseless)break;
				}
				if(isUseless){
					deleteVideo(videoID, cacheID);
					ajouterVideosRandom(cacheID);
				}
			}
		
//		boolean isVideoInutile = true;
//		while(isVideoInutile) {
//			boolean changement = false;
//			for(Entry<Integer, Set<Integer>> entry : cacheToVideos.entrySet()) {
//				cacheID = entry.getKey();
//				// on parcourt les vidéos
//				for ( Integer videoID : entry.getValue()) {
//					// on regarde si la vidéo courante est inutile
//					if (estVideoInutile(videoID, cacheID)) {
//						deleteVideo(videoID, cacheID);
//						ajouterVideosRandom(cacheID);
//						changement = true;
//					}
//				}
//			}
//			isVideoInutile = changement;
		}
		
		// on prend la vidéo la moins demandée d'un cache, on la remplace par la vidéo la plus demandée pour ce cache qui n'y est pas déjà
		for(Entry<Integer, Set<Integer>> entry : cacheToVideos.entrySet()) {
			cacheID = entry.getKey();
			// on parcourt les endpoints de ce cache
			Map<Integer, Integer> videoIDToNbAskMin = new HashMap<Integer, Integer>(entry.getValue().size());
			// première boucle : on remplit la liste de nombre de demandes par vidéo
			for(int endpointID = 0 ; endpointID < App.nbEndPoint ; endpointID++) {
				if(ceEndpointestRelieACeCache(endpointID, cacheID)) {
					Endpoint endpoint = App.endpoints.get(endpointID);
					// on parcourt les vidéos de ce endpoint
					for(Entry<Integer, Integer> endpointEntry : endpoint.videoToRequests.entrySet()) {
						Integer endpointEntryVideoID = endpointEntry.getKey();
						Integer endpointEntryVideoAsk = endpointEntry.getValue();
						if(cacheToVideos.get(cacheID).contains(endpointEntryVideoID)) {
							if(videoIDToNbAskMin.containsKey(endpointEntryVideoID)) {
								videoIDToNbAskMin.put(endpointEntryVideoID, videoIDToNbAskMin.get(endpointEntryVideoID)+endpointEntryVideoAsk);
							}else {
								videoIDToNbAskMin.put(endpointEntryVideoID,endpointEntryVideoAsk);
							}
						}
					}
				}
			}
			// deuxième boucle : on sait quelles sont les vidéos les moins demandées
			// ou plutôt... on les cherche
			// deuxième parocurs : min
			boolean browse = false;
			Integer minId = null;
			Integer min = null;
			for(Entry<Integer, Integer> entryVid : videoIDToNbAskMin.entrySet()) {
				if(!browse) {
					minId = entryVid.getKey();
					min = entryVid.getValue();
					browse = true;
				}
				if(entryVid.getValue() < min) {
					min = entryVid.getValue();
					minId = entryVid.getKey();
				}
			}
			
			// trouvons la vidéo absente la plus swag
			// on parcourt les endpoints de ce cache
			Map<Integer, Integer> videoIDToNbAskMax = new HashMap<Integer, Integer>();
			// troisième boucle : on remplit la liste de nombre de demandes par vidéo non incluse dans le cache
			for(int endpointID = 0 ; endpointID < App.nbEndPoint ; endpointID++) {
				if(ceEndpointestRelieACeCache(endpointID, cacheID)) {
					Endpoint endpoint = App.endpoints.get(endpointID);
					// on parcourt les vidéos de ce endpoint
					for(Entry<Integer, Integer> endpointEntry : endpoint.videoToRequests.entrySet()) {
						Integer endpointEntryVideoID = endpointEntry.getKey();
						Integer endpointEntryVideoAsk = endpointEntry.getValue();
						if(!cacheToVideos.get(cacheID).contains(endpointEntryVideoID)) {
							if(videoIDToNbAskMax.containsKey(endpointEntryVideoID)) {
								videoIDToNbAskMax.put(endpointEntryVideoID, videoIDToNbAskMax.get(endpointEntryVideoID)+endpointEntryVideoAsk);
							}else {
								videoIDToNbAskMax.put(endpointEntryVideoID,endpointEntryVideoAsk);
							}
						}
					}
				}
			}
			// quatrième boucle : on sait quelles sont les vidéos les plus demandées
			// ou plutôt... on les cherche
			// quatrième parcours : max
			browse = false;
			Integer maxId = null;
			Integer max = null;
			for(Entry<Integer, Integer> entryVid : videoIDToNbAskMax.entrySet()) {
				if(!browse) {
					maxId = entryVid.getKey();
					max = entryVid.getValue();
					browse = true;
				}
				if(entryVid.getValue() > max) {
					max = entryVid.getValue();
					maxId = entryVid.getKey();
				}
			}
			
			// maintenant, on a la vidéo la plus demandée qui n'y est pas, et la vidéo la moins demandée qui y es... on intervertit
			if(App.cacheSize.get(cacheID) + App.videoSize.get(minId) - App.videoSize.get(maxId) > 0 ) {
				deleteVideo(minId, cacheID);
				addVideoToCache(cacheID, maxId);
			}
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
		if(estCeQueCaRentre(cacheID, videoID)) {
			initList(cacheID); // toujours commencer par ça, Justin Case
			// on ajoute la vidéo à la liste de résultats
			cacheToVideos.get(cacheID).add(videoID);
			
			// on réduit la taille disponible du cache en question
			Integer tailleRestanteDuCache = App.cacheSize.get(cacheID);
			tailleRestanteDuCache -= App.videoSize.get(videoID);
			App.cacheSize.set(cacheID, tailleRestanteDuCache);
		}
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
