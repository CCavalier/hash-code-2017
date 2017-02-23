package collection.interfaces;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class OutputServiceTest {

	@Test
	public void testWriteOutAllTheThings() throws URISyntaxException {
		Map<Integer, Set<Integer>> cacheToVideos = new HashMap<Integer, Set<Integer>>();
		
		Set<Integer> set1 = new HashSet<Integer>();
		Set<Integer> set2 = new HashSet<Integer>(); 
		Set<Integer> set3 = new HashSet<Integer>();
		Set<Integer> set4 = new HashSet<Integer>();
		
		set1.add(1);
		set1.add(2);
		set2.add(3);
		set3.add(2);
		
		cacheToVideos.put(1, set1);
		cacheToVideos.put(2, set2);
		cacheToVideos.put(3, set3);
		cacheToVideos.put(5, set4);
		
		IntelligenceCache.cacheToVideos = cacheToVideos;
		
		OutputService.writeOutAllTheThings();
	}

}
