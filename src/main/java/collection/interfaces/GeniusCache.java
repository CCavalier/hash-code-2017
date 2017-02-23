package collection.interfaces;

public class GeniusCache {
	// faire un arbre donnant la pondération des branches : 
	//		les racines étant les caches et le data center
	//		les feuilles étant les endpoint
	//		les pondérations sur les arêtes étant les latences entre les endpoint et les caches/data center
	// dans un deuxième temps, créer un arbre secondaire contenant toutes les vidéos
	// relier les vidéos aux caches
	// pondérer les liens entre vidéo et cache EN ADDITIONNANT LE NOMBRE DE REQUETES POUR CETTE VIDEO DE TOUS LES ENDPOINTS RELIES A CE CACHE
	// ensuite diviser ces pondérations par le poids de la vidéo de l'arrête
	// puis vient l'insertion :
	//		insérer dans chaque noeud la vidéo dont l'arête à la pondération (divisée) la plus forte
	//		puis, si elle rentre, la seconde plus forte
	//		et ainsi de suite jusqu'à ce qu'on ne puisse plus insérer la moindre vidéo
	// ce n'est toujours pas parfait (#NPComplet) mais c'est déjà pas mal ;)
}
