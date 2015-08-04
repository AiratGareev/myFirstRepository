package compozitions;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

class SpravochnikXMLoader implements SpravochnikLoader {
	private static SpravochnikLoader spravochnikLoader = new SpravochnikXMLoader();
	private static final Logger logger = LogManager.getRootLogger();
	private SpravochnikXMLoader() {
	}

	public static SpravochnikLoader getInstance() {
		return spravochnikLoader;
	}

	

	@Override
	public Spravochnik loadSpravochnik(String fileName) {

		try {
			logger.info("Началась загрузка XML файла " + fileName);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;
			db = dbf.newDocumentBuilder();
			Document document = db.parse(fileName);
			Element spravochnikNode = document.getDocumentElement();
			NodeList childNodes = spravochnikNode.getChildNodes();
			Spravochnik spravochnik = new Spravochnik();
			for (int i = 0; i < childNodes.getLength(); i++) {
				if (childNodes.item(i).getNodeName().equals("ispolnitel"))
					spravochnik.addIspolnitel(getIspolnitel(childNodes.item(i)));
			}
			logger.info("Загрузка файла " + fileName + " Успешно завершена");
			return spravochnik;
		} catch (Exception e) {
			logger.error("Неудолось загрузить XML файл " + fileName, e);
			e.printStackTrace();
		}

		return null;
	}

	private Ispolnitel getIspolnitel(Node ispolnitelNode) {
		NodeList childNodes = ispolnitelNode.getChildNodes();
		Ispolnitel ispolnitel = new Ispolnitel(
				ispolnitelNode.getAttributes().getNamedItem("name").getTextContent().trim());
		for (int i = 0; i < childNodes.getLength(); i++) {
			if (childNodes.item(i).getNodeName().equals("albom"))
				ispolnitel.addAlbom(getAlbom(childNodes.item(i)));
		}
		return ispolnitel;
	}

	private Albom getAlbom(Node albomNode) {
		NodeList childNodes = albomNode.getChildNodes();
		Albom albom = new Albom(albomNode.getAttributes().getNamedItem("name").getTextContent().trim(),
				albomNode.getAttributes().getNamedItem("janr").getTextContent().trim());
		for (int i = 0; i < childNodes.getLength(); i++) {
			if (childNodes.item(i).getNodeName().equals("compozition"))
				albom.addCompazition(getCompozition(childNodes.item(i)));
		}
		return albom;
	}

	private Compozition getCompozition(Node compozitionNode) {
		Compozition compozition = new Compozition(
				compozitionNode.getAttributes().getNamedItem("name").getTextContent().trim(),
				compozitionNode.getAttributes().getNamedItem("length").getTextContent().trim());
		return compozition;
	}

	@Override
	public boolean saveSpravochnik(Spravochnik spravochnik, String fileName) {

		try {
			logger.info("Началось сохранение файла " + fileName + extend());
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;
			db = dbf.newDocumentBuilder();
			Document document = db.newDocument();
			Element root = document.createElement("spravochnik");
			for (Ispolnitel ispolnitel : spravochnik.getIspolnitels()) {
				root.appendChild(getIspolnitelNode(ispolnitel, document));
			}
			document.appendChild(root);
			DOMSource domSource = new DOMSource(document);
			StreamResult out = new StreamResult(fileName + extend());
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.transform(domSource, out);
			logger.info("Сохранение файла " + fileName + extend() + " Успешно завершена");
			return true;
		} catch (Exception e) {
			logger.error("Неудалось сохранить файл " + fileName + extend(), e);
		}
		return false;
	}

	private Node getIspolnitelNode(Ispolnitel ispolnitel, Document document) {
		Element ispolnitelNode = document.createElement("ispolnitel");
		ispolnitelNode.setAttribute("name", ispolnitel.getName());
		for (Albom albom : ispolnitel.getAlboms()) {
			ispolnitelNode.appendChild(getAlbomNode(albom, document));
		}
		return ispolnitelNode;
	}

	private Node getAlbomNode(Albom albom, Document document) {
		Element albomNode = document.createElement("albom");
		albomNode.setAttribute("name", albom.getName());
		albomNode.setAttribute("janr", albom.getJanr());
		for (Compozition compozition : albom.getCompozitions()) {
			albomNode.appendChild(getCompozitionNode(compozition, document));
		}
		return albomNode;
	}

	private Node getCompozitionNode(Compozition compozition, Document document) {
		Element compozitionNode = document.createElement("compozition");
		compozitionNode.setAttribute("name", compozition.getName());
		compozitionNode.setAttribute("length", compozition.getLength());
		return compozitionNode;
	}

	@Override
	public String extend() {
		return ".xml";
	}

}
