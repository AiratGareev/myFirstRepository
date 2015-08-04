package compozitions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class SpravochnikDBLoader implements SpravochnikLoader {
	private static SpravochnikDBLoader instance = new SpravochnikDBLoader();
	private Connection connection;
	private static final Logger logger = LogManager.getRootLogger();
	private HashMap<String, Integer> map = new HashMap<>();

	private SpravochnikDBLoader() {
	}

	public static SpravochnikLoader getInstance() {
		return instance;
	}

	@Override
	public Spravochnik loadSpravochnik(String fileName) {
		Spravochnik spravochnik = null;
		String sql = "Select и.имя, а.имя, а.жанр, к.имя, к.длительность\n"
				+ "from Справочник с, Исполнитель и, Альбом а, Компазиция к \n"
				+ "where с.код = ? and и.код_справочника =с.код and а.код_исполнителя =и.код and к.код_альбома =а.код";
		try (PreparedStatement statment = getConnection().prepareStatement(sql)) {
			statment.setInt(1, map.get(fileName));
			ResultSet executeQuery = statment.executeQuery();
			spravochnik = new Spravochnik();
			String ispolnitelName = "";
			String albomName = "";
			Ispolnitel curIspolnitel = null;
			Albom curAlbom = null;
			while (executeQuery.next()) {
				if (!ispolnitelName.equals(executeQuery.getString(1))) {
					ispolnitelName = executeQuery.getString(1);
					curIspolnitel = new Ispolnitel(executeQuery.getString(1));
					spravochnik.addIspolnitel(curIspolnitel);
				}
				if (!albomName.equals(executeQuery.getString(2))) {
					albomName = executeQuery.getString(2);
					curAlbom = new Albom(executeQuery.getString(2), executeQuery.getString(3));
					curIspolnitel.addAlbom(curAlbom);
				}
				curAlbom.addCompazition(
						new Compozition(executeQuery.getString(4), String.valueOf(executeQuery.getDouble(5))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		return spravochnik;
	}

	private Connection getConnection() {
		if (connection == null)
			connection = connect();
		return connection;
	}

	private Connection connect() {
		loadDriver();
		try {
			connection = (Connection) DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system",
					"5716");

			logger.info("Соединение с базой данных установлено");

		} catch (SQLException ex) {

			logger.error("Не удалось подключится к базе данных", ex);
		}
		return connection;
	}

	private void loadDriver() {
		Locale.setDefault(Locale.US);
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			logger.info("Драйвер загружан");
		} catch (ClassNotFoundException ex) {
			logger.error("Драйвер не загрузился", ex);
		}
	}

	@Override
	public boolean saveSpravochnik(Spravochnik spravochnik, String fileName) {
		try {
			String sql = "insert into справочник values (spr_seq.nextval, ? )";
			PreparedStatement statment = getConnection().prepareStatement(sql);
			statment.setString(1, fileName);
			statment.executeUpdate();
			statment.close();
			for (Ispolnitel ispolnitel : spravochnik.getIspolnitels()) {
				sql = "insert into Исполнитель values (isp_seq.nextval, ?,spr_seq.currval)";
				statment = getConnection().prepareStatement(sql);
				statment.setString(1, ispolnitel.getName());
				statment.executeUpdate();
				statment.close();
				for (Albom albom : ispolnitel.getAlboms()) {
					sql = "insert into Альбом values (alb_seq.nextval, ?,?, isp_seq.currval)";
					statment = getConnection().prepareStatement(sql);
					statment.setString(1, albom.getName());
					statment.setString(2, albom.getJanr());
					statment.executeUpdate();
					statment.close();
					for (Compozition compozition : albom.getCompozitions()) {
						sql = "insert into Компазиция values (kpz_seq.nextval, ?,?,alb_seq.currval)";
						statment = getConnection().prepareStatement(sql);
						statment.setString(1, compozition.getName());
						statment.setString(2, compozition.getLength());
						statment.executeUpdate();
						statment.close();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
			return false;
		}
		return true;
	}

	@Override
	public String extend() {
		map.clear();
		try (Statement statement = getConnection().createStatement()) {
			ResultSet query = statement.executeQuery("select * from справочник");
			StringBuilder sb = new StringBuilder();
			while (query.next()) {
				map.put(query.getString(2) + "_" + query.getInt(1), query.getInt(1));
				sb.append(query.getString(2) + "_" + query.getInt(1) + " ");
			}
			return sb.toString();
		} catch (SQLException e) {
			logger.error(e);
		}
		return "";
	}

}
