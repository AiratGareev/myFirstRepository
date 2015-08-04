package compozitions;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestIspolnitel {
	private Ispolnitel ispolnitel;
	@Before
	public void setUp() throws Exception {
		ispolnitel = new Ispolnitel("Боярский");
	}

	@Test
	public void testConstruktor() {
		assertEquals("Боярский", ispolnitel.getName());
	}
	
	@Test(expected = ParseException.class)
	public void testValidte() throws ParseException {
		ispolnitel.validate();
	}
	
	@Test
	public void testGetDistinctJanr(){
		assertTrue(ispolnitel.getDistinctJanr().isEmpty());
	}

	@Test
	public void testGetIspolnitelFilterJanr(){
		assertNull(ispolnitel.getIspolnitelFilterJanr("Классика"));
	}
	
	
}
