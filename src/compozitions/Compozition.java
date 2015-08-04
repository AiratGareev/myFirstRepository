package compozitions;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.Serializable;

public class Compozition implements Serializable {
	private String name;
	private String length;

	public Compozition(String name, String length) {
		this.name = name;
		this.length = length;
	}

	public String getName() {
		return name;
	}

	public String getLength() {
		return length;
	}

	@Deprecated
	void write(PrintStream out) {
		out.println("\t\t����������: " + name + ", " + length);
	}

	public void validate() throws ParseException {
		if ("".equals(name))
			throw new ParseException("� ���������� ��� �����");
		if ("".equals(length))
			throw new ParseException("� ���������� ��� ������");
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(40);
		sb.append("\t\t����������: " + name + ", " + length + '\n');
		return sb.toString();
	}

}
