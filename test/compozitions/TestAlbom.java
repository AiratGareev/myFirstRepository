package compozitions;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestAlbom {
	private Albom albom;

	@Before
	public void setUp() {
		albom = new Albom("������", "����");
	}

	@Test
	public void testConstruktor() {
		assertEquals("������", albom.getName());
		assertEquals("����", albom.getJanr());
	}
	
	@Test
	public void testValidate(){
		assertNull(albom.getAlbomFilterJanr("��������"));
		assertNotNull(albom.getAlbomFilterJanr("����"));
	}
	
	@Test(expected = ParseException.class)
	public void testValidte() throws ParseException {
		albom.validate();
	}
	
	@Test
	public void testToString() {
		assertEquals("\t������: ������, ����\n", albom.toString());
	}
}
