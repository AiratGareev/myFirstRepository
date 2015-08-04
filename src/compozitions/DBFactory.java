package compozitions;

public class DBFactory extends AbsractFactory {

	@Override
	public SpravochnikLoader getLoader() {
		return SpravochnikDBLoader.getInstance();
	}

}
