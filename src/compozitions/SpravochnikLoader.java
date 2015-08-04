package compozitions;

public interface SpravochnikLoader {
	public Spravochnik loadSpravochnik(String fileName);
	public boolean saveSpravochnik(Spravochnik spravochnik, String fileName);
	public String extend();
}
