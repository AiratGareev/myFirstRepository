package compozitions;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class SpravochnikTextLoader implements SpravochnikLoader {
	private Parser parser;
	private static SpravochnikLoader instance = new SpravochnikTextLoader();
	private static final Logger logger = LogManager.getRootLogger();

	private SpravochnikTextLoader() {
	}

	public static SpravochnikLoader getInstance() {
		return instance;
	}

	public Spravochnik loadSpravochnik(String fileName) {
		Spravochnik spravochnik = null;
		logger.info("�������� �������� ����� " + fileName);
		try {
			parser = new Parser(fileName);

			spravochnik = new Spravochnik();
			parser.next();
			while (parser.getType() == TypeItem.�����������)
				spravochnik.addIspolnitel(loadIspolnitel());
			logger.info("�������� ����� " + fileName + " ������� ���������");

		} catch (Exception e) {
			logger.error("��������� ��������� ���� " + fileName, e);
		}

		return spravochnik;
	}

	private Ispolnitel loadIspolnitel() {
		String name = parser.getNextData();
		Ispolnitel ispolnitel = new Ispolnitel(name);
		parser.next();
		while (parser.getType() == TypeItem.������) {
			ispolnitel.addAlbom(loadAlbom());
		}

		return ispolnitel;

	}

	private Albom loadAlbom() {
		String name = parser.getNextData();
		String janr = parser.getNextData();
		Albom albom = new Albom(name, janr);
		parser.next();
		while (parser.getType() == TypeItem.����������) {
			albom.addCompazition(loadCompazition());
		}

		return albom;

	}

	private Compozition loadCompazition() {
		String name = parser.getNextData();
		String length = parser.getNextData();
		Compozition compozition = new Compozition(name, length);
		parser.next();
		return compozition;
	}

	@Override
	public boolean saveSpravochnik(Spravochnik spravochnik, String fileName) {
		try {
			logger.info("�������� ���������� ����� " + fileName + extend());
			try (PrintStream out = new PrintStream(fileName + extend())) {
				out.print(spravochnik.toString());
			}
			logger.info("���������� ����� " + fileName + extend() + " ������� ���������");
			return true;
		} catch (FileNotFoundException e) {
			logger.error("��������� ��������� ���� " + fileName + extend(), e);
			return false;
		}
	}

	@Override
	public String extend() {
		return ".txt";
	}
}
