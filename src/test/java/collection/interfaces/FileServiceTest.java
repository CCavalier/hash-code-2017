package collection.interfaces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

public class FileServiceTest {
	
	FileService fileService = new FileService();
	
	URL urlInputFile;
	
	@Before
	public void setUp() {
		urlInputFile = this.getClass().getClassLoader().getResource("test.txt");
	}
	
	@Test
	public void readFileTest() throws URISyntaxException {
		Optional<List<String>> value = fileService.readFile(urlInputFile.toURI());
		assertNotEquals(Optional.empty(), value);
		assertEquals(false, value.get().isEmpty());
		assertEquals("qwe", value.get().get(0));
	}
	
	@Test
	public void writeFileTest() throws URISyntaxException, IOException{
		URI outputFile = new File(new File(urlInputFile.toURI()).getParentFile().toURI().getPath()+"newFile.txt").toURI();
		String expectedOutput = "aze";
		fileService.writeFile(outputFile, expectedOutput);
		Optional<List<String>> value = fileService.readFile(outputFile);
		assertNotEquals(value, Optional.empty());
		assertEquals(false, value.get().isEmpty());
		assertEquals("aze", value.get().get(0));
	}
}
