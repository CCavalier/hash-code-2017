package collection.interfaces;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Hello world!
 *
 */
public class App 
{

    public static List<Integer> videoSize = new ArrayList<>();

    public static List<Integer> videoAsked = new ArrayList<>();

    public static List<Integer> cacheSize = new ArrayList<>();

    public static List<Endpoint> endpoints = new ArrayList<>();

    public static  int nbVideos;

    public static int nbCache;

    public static int nbEndPoint;

    public static int initialCacheSize;

    public static int request;




    public static void main( String[] args ) throws URISyntaxException {
        FileService fileService = new FileService();
        URL urlInputFile = fileService.getClass().getClassLoader().getResource("kittens.in");
        int lineRead = 0;

        Optional<List<String>> listLines = fileService.readFile(urlInputFile.toURI());
        System.out.println("list size:" + listLines.get().size());

        String[] vals = listLines.get().get(lineRead).split(" ");
        lineRead++;
        //5 videos, 2 endpoints, 4 request descriptions, 3 caches 100MB each.
        nbVideos = Integer.valueOf(vals[0]);
        nbEndPoint = Integer.valueOf(vals[1]);
        request = Integer.valueOf(vals[2]);
        nbCache = Integer.valueOf(vals[3]);
        initialCacheSize = Integer.valueOf(vals[4]);
        System.out.println("nbVideos:" + nbVideos);
        System.out.println("nbEndPoint:" + nbEndPoint);
        System.out.println("request:" + request);
        System.out.println("nbCache:" + nbCache);
        System.out.println("initialCacheSize:" + initialCacheSize);


        //initialize cache size to intial size
        for (int i = 0; i < nbCache; i++) {
            cacheSize.add(initialCacheSize);
        }

        vals = listLines.get().get(lineRead).split(" ");
        lineRead++;
        for (int i = 0; i < nbVideos; i++) {
            videoSize.add(Integer.valueOf(vals[i]));
        }

        for (int i = 0; i < nbEndPoint; i++) {
            vals = listLines.get().get(lineRead).split(" ");
            lineRead++;
            int nbCache = 0;
            Endpoint ep = new Endpoint();
            ep.dtacenterLatency = Integer.valueOf(vals[0]);
            nbCache = Integer.valueOf(vals[1]);
            System.out.println("ep"+i+" latency:"+ep.dtacenterLatency);
            System.out.println("ep"+i+" nbCache:"+nbCache);

            for (int j = 0; j < nbCache; j++) {
                vals = listLines.get().get(lineRead).split(" ");
                lineRead++;
                //System.out.println("== latency to cache " + Integer.valueOf(vals[0]) + ":" + Integer.valueOf(vals[1]));
                ep.cacheToLatency.put(Integer.valueOf(vals[0]), Integer.valueOf(vals[1]));
            }
            endpoints.add(ep);

        }

        while (lineRead < listLines.get().size()) {
            vals = listLines.get().get(lineRead).split(" ");
            lineRead++;
            if(vals == null) break;
            int idEp =Integer.valueOf(vals[1]);
            endpoints.get(idEp).videoToRequests.put(Integer.valueOf(vals[0]), Integer.valueOf(vals[2]));

        }

        IntelligenceCache.doTheSmartThing();

        OutputService.writeOutAllTheThings();

        System.out.println( "FINI" );
    }
}
