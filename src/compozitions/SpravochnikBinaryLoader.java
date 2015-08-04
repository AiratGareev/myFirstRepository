package compozitions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class SpravochnikBinaryLoader implements SpravochnikLoader {
	private static SpravochnikLoader instance = new SpravochnikBinaryLoader();
	private static final Logger logger = LogManager.getRootLogger();

	private SpravochnikBinaryLoader() {
	} 

	public static SpravochnikLoader getInstance() {
		return instance;
	}
	@Override
	public Spravochnik loadSpravochnik(String fileName){
		logger.info("Началась загрузка файла " + fileName);
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))){
			Spravochnik spravochnik = (Spravochnik)ois.readObject();
			logger.info("Загрузка файла " + fileName + " Успешно завершена");
			return spravochnik;
		} catch (Exception e) {
			logger.error("Неудолось загрузить файл " + fileName, e);
		}
		return null;
	}

	@Override
	public boolean saveSpravochnik(Spravochnik spravochnik, String fileName) {
		logger.info("Началось сохранение файла " + fileName + extend());
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName + extend()))){
			oos.writeObject(spravochnik);
			logger.info("Сохранение файла " + fileName + extend() + " Успешно завершена");
			return true;
		} catch (IOException e) {
			logger.error("Неудалось сохранить файл " + fileName + extend(), e);
		}
		return false;
	}
	@Override
	public String extend() {
		return ".bin";
	}
}
