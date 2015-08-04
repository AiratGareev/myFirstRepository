package compozitions;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestAlbom {
	private Albom albom;

	@Before
	public void setUp() {
		albom = new Albom("Альбом", "Жанр");
	}

	@Test
	public void testConstruktor() {
		assertEquals("Альбом", albom.getName());
		assertEquals("Жанр", albom.getJanr());
	}
	
	@Test
	public void testValidate(){
		assertNull(albom.getAlbomFilterJanr("классика"));
		assertNotNull(albom.getAlbomFilterJanr("Жанр"));
	}
	
	@Test(expected = ParseException.class)
	public void testValidte() throws ParseException {
		albom.validate();
	}
	
	@Test
	public void testToString() {
		assertEquals("\tАльбом: Альбом, Жанр\n", albom.toString());
	}
}
