package compozitions;

public class BinFactory extends AbsractFactory {

	@Override
	public SpravochnikLoader getLoader() {
		return SpravochnikBinaryLoader.getInstance();
	}

}
