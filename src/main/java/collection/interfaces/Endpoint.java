package collection.interfaces;

import java.util.Map;

/**
 * Created by charlotte on 23/02/17.
 */
public class Endpoint {

    int dtacenterLatency= 0;

    Map<Integer, Integer> cacheToLatency;

    Map<Integer, Integer> videoToRequests;
}
