package compozitions;


public class TextFactory extends AbsractFactory{

	@Override
	public SpravochnikLoader getLoader() {
		return SpravochnikTextLoader.getInstance();
	}

}
