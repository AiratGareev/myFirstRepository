package compozitions;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestCompozition {
	private Compozition compazition;

	@Before
	public void setUp() {
		compazition = new Compozition("���", "2.16");
	}

	@Test
	public void testConstrurtor() {
		assertEquals("���", compazition.getName());
		assertEquals("2.16", compazition.getLength());
	}

	@Test(expected = ParseException.class)
	public void testValidate() throws ParseException {
		compazition = new Compozition("", "1.20");
		compazition.validate();
	}

	@Test
	public void testToString() {
		assertEquals("\t\t����������: ���, 2.16\n", compazition.toString());
	}

}
