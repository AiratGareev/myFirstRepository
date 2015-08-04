package compozitions;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class Spravochnik implements Serializable {
	private ArrayList<Ispolnitel> ispolnitels = new ArrayList<>();

	Spravochnik() {
	}

	void addIspolnitel(Ispolnitel ispolnitel) {
		ispolnitels.add(ispolnitel);
	}

	public Object[] getDistinctJanr() {
		HashSet<String> janrs = new HashSet<>();
		for (Ispolnitel ispolnitel : ispolnitels) {
			janrs.addAll(ispolnitel.getDistinctJanr());
		}
		return janrs.toArray();
	}

	public void validate() throws ParseException {
		if (ispolnitels.isEmpty())
			throw new ParseException("Нет исполнителей");
		for (Ispolnitel ispolnitel : ispolnitels) {
			ispolnitel.validate();
		}
	}

	ArrayList<Ispolnitel> getIspolnitels() {
		return ispolnitels;
	}

	public Spravochnik getSpravochnicFilterJanr(String janr) {
		Spravochnik spravochnik = new Spravochnik();
		for (Ispolnitel ispolnitel : ispolnitels) {
			if (ispolnitel.getIspolnitelFilterJanr(janr) != null) {
				spravochnik.addIspolnitel(ispolnitel.getIspolnitelFilterJanr(janr));
			}
		}
		return spravochnik;
	}
	@Deprecated
	void write(String fileName) throws FileNotFoundException {
		try (PrintStream out = new PrintStream(fileName)) {
			for (Ispolnitel ispolnitel : ispolnitels) {
				ispolnitel.write(out);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(2000);
		for (Ispolnitel ispolnitel : ispolnitels) {
			sb.append(ispolnitel);
		}
		return sb.toString();
	}

}
