package compozitions;

public class XMLFactory extends AbsractFactory {

	@Override
	public SpravochnikLoader getLoader() {
		return SpravochnikXMLoader.getInstance();
	}

}
