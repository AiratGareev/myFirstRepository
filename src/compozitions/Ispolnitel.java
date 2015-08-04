package compozitions;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Ispolnitel implements Serializable {
	private String name;
	private ArrayList<Albom> alboms = new ArrayList<>();

	public Ispolnitel(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public ArrayList<Albom> getAlboms() {
		return alboms;
	}

	public void addAlbom(Albom albom) {
		alboms.add(albom);
	}

	void validate() throws ParseException {
		if ("".equals(name))
			throw new ParseException("У исполнителя нет имени");

		if (alboms.isEmpty())
			throw new ParseException("У исполнителя " + name + " нет альбомов");
		for (Albom albom : alboms) {
			albom.validate();
		}
	}

	public Set getDistinctJanr() {
		HashSet<String> janrs = new HashSet<>();
		for (Albom albom : alboms) {
			janrs.add(albom.getJanr());
		}
		return janrs;
	}

	public Ispolnitel getIspolnitelFilterJanr(String janr) {
		boolean isValid = false;
		for (Albom albom : alboms) {
			if (albom.getJanr().equals(janr)) {
				isValid = true;
				break;
			}
		}

		if (isValid) {
			Ispolnitel ispolnitel = new Ispolnitel(name);
			for (Albom albom : alboms) {
				if (albom.getAlbomFilterJanr(janr) != null) {
					ispolnitel.addAlbom(albom.getAlbomFilterJanr(janr));
				}
			}
			return ispolnitel;
		}
		return null;
	}
	@Deprecated
	void write(PrintStream out) {

		out.println("Исполнитель: " + name);
		for (Albom albom : alboms) {
			albom.write(out);
		}

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(300);
		sb.append("Исполнитель: " + name + '\n');
		for (Albom albom : alboms) {
			sb.append(albom);
		}
		return sb.toString();
	}
}
