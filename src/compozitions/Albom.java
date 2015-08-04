package compozitions;

import static org.junit.Assert.assertEquals;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.junit.Test;

public class Albom implements Serializable {
	private String name;
	private String janr;
	private ArrayList<Compozition> compozitions = new ArrayList<>();

	public Albom(String name, String janr) {
		this.name = name;
		this.janr = janr;
	}

	public ArrayList<Compozition> getCompozitions() {
		return compozitions;
	}

	public String getName() {
		return name;
	}

	public void addCompazition(Compozition compozition) {
		compozitions.add(compozition);
	}

	public String getJanr() {
		return janr;
	}

	public Albom getAlbomFilterJanr(String janr) {
		if (this.janr.equals(janr)) {
			return this;
		}
		return null;
	}
	@Deprecated
	void write(PrintStream out) {
		out.println("\tАльбом: " + name + ", " + janr);
		for (Compozition compozition : compozitions) {
			compozition.write(out);
		}
	}

	public void validate() throws ParseException {
		if ("".equals(name))
			throw new ParseException("У альбома нет имени");
		if ("".equals(janr))
			throw new ParseException("У альбома " + name + " нет жанра");
		if (compozitions.isEmpty())
			throw new ParseException("У альбома " + name + " нет композиции");
		for (Compozition compozition : compozitions) {
			compozition.validate();
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(150);
		sb.append("\tАльбом: " + name + ", " + janr + '\n');
		for (Compozition compozition : compozitions) {
			sb.append(compozition);
		}
		return sb.toString();
	}
}
