package io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Properties;
import java.util.Scanner;

import compozitions.AbsractFactory;
import compozitions.BinFactory;
import compozitions.DBFactory;
import compozitions.ParseException;
import compozitions.Spravochnik;
import compozitions.SpravochnikLoader;
import compozitions.TextFactory;
import compozitions.XMLFactory;

public class Main {

	static final int LOAD = 1;
	static final int PRINT = 2;
	static final int JANR_FILTER = 3;
	static final int SELECT_LOADER = 4;
	static final int SAVE = 5;
	static final int VALIDATE = 6;
	static final int EXIT = 7;
	static Spravochnik spravochnik;
	static Scanner scanner = new Scanner(System.in);
	static AbsractFactory factory;

	public static void main(String[] s) throws ParseException {
		System.out.println("����� ����������");
		selectLoader();
		int action = LOAD;
		do {
			switch (action) {
			case LOAD:
				load();
				break;
			case PRINT:
				System.out.println(spravochnik);
				break;
			case JANR_FILTER:
				janrFilter();
				break;
			case SELECT_LOADER:
				selectLoader();
				break;
			case SAVE:
				save();
				break;
			case VALIDATE:
				validate();
				break;
			case EXIT:
				System.exit(0);
			}
			System.out.println("1) ��������� ����");
			System.out.println("2) ����������� ��������� �����");
			System.out.println("3) ������������� �� �����");
			System.out.println("4) ������� ��������� �����������");
			System.out.println("5) ���������");
			System.out.println("6) ����������");
			System.out.println("7) �����");
			action = scanner.nextInt();
		} while (true);

	}

	private static void validate() {
		try {
			spravochnik.validate();
			System.out.println("������ ���");
		} catch (ParseException e) {
			System.err.println(e.getMessage());
		}

	}

	private static void save() {
		System.out.print("������� ��� �����: ");
		scanner.nextLine();
		boolean saveSpravochnik = factory.getLoader().saveSpravochnik(spravochnik, scanner.nextLine());
		if (saveSpravochnik) {
			System.out.println("���� ������� �������!");
		} else {
			System.out.println("��������� ��������� :(");
		}
	}

	private static void selectLoader() {
		System.out.println("�������� ��������� ����������:");
		System.out.println("1) ���������");
		System.out.println("2) ��������");
		System.out.println("3) XML");
		System.out.println("4) ��");
		
		final int TEXT = 1;
		final int BIN = 2;
		final int XML = 3;
		final int DB = 4;
		
		switch (scanner.nextInt()) {
		case TEXT:
			factory = new TextFactory();
			System.out.println("������ ��������� ���������");
			break;
		case BIN:
			factory = new BinFactory();
			System.out.println("������ �������� ���������");
			break;
		case XML:
			factory = new XMLFactory();
			System.out.println("������ XML ���������");
			break;
		case DB:
			factory = new DBFactory();
			System.out.println("������ �� ���������");
			break;
		default:
			System.out.println("��� ������ ����������");
		}
	}

	private static void janrFilter() {
		Object[] allJanrs = spravochnik.getDistinctJanr();
		System.out.println("�������� ����:");
		for (int i = 0; i < allJanrs.length; i++) {
			System.out.println((i + 1) + ") " + allJanrs[i]);
		}
		int selectedJanr = scanner.nextInt() - 1;
		spravochnik = spravochnik.getSpravochnicFilterJanr((String) allJanrs[selectedJanr]);
		System.out.println("�������������");
	}

	private static void load() throws ParseException {
		String[] list = getList();
		switch (list.length) {
		case 0:
			System.out.println("��� ������ ��� ��������");
			return;
		case 1:
			spravochnik = factory.getLoader().loadSpravochnik(list[0]);
			System.out.println("���� ��������: " + list[0]);
			break;
		default:
			System.out.println("�������� ���� ��� ��������:");
			for (int i = 0; i < list.length; i++) {
				System.out.println((i + 1) + ") " + list[i]);
			}
			int numFile = scanner.nextInt() - 1;
			spravochnik = factory.getLoader().loadSpravochnik(list[numFile]);
			if(spravochnik!=null)
				System.out.println("���� ��������: " + list[numFile]);
			else
				System.out.println("���-�� ����� �� ���");
		}
	}

	private static String[] getList() {
		if (factory instanceof DBFactory) {
			return factory.getLoader().extend().split(" ");
		} else {
			File curFile = new File(".");
			return curFile.list(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					if (name.endsWith(factory.getLoader().extend()))
						return true;
					return false;
				}
			});
		}

	}
}
