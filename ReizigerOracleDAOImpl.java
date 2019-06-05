import java.sql.*;
import java.util.ArrayList;


public class ReizigerOracleDAOImpl extends OracleBaseDAO implements ReizigerDAO {

	public ReizigerOracleDAOImpl() throws SQLException {
		super();
	}


	public Reiziger save(Reiziger r) {
		try {
			PreparedStatement pstmnt = conn.prepareStatement("INSERT INTO reiziger (reizigerid, voorletters, tussenvoegsel, achternaam, gebortedatum) VALUES (?, ?, ?, ?, ?)");
			pstmnt.setInt(1, r.getReizigerID());
			pstmnt.setString(2, r.getVoorletters());
			pstmnt.setString(3, r.getTussenvoegsel());
			pstmnt.setString(4, r.getAchternaam());
			pstmnt.setDate(5, r.getGeboortedatum());

			pstmnt.executeUpdate();

			for (OVChipkaart k : r.getOvchipkaarten()) {
				OVChipkaartDAOOracleDB ovdao = new OVChipkaartDAOOracleDB();
				ovdao.save(k);
			}

			pstmnt.close();
		}
		catch (SQLException sqlex) {
			System.out.println("SQLException: " + sqlex.getMessage());
		}

		return r;
	}

	public Reiziger update(Reiziger r) {
		try {
			PreparedStatement pstmnt = conn.prepareStatement("UPDATE reiziger SET reizigerid = ?, voorletters = ?, tussenvoegsel = ?, achternaam = ?, gebortedatum = ? WHERE reizigerid = ?");
			pstmnt.setInt(1, r.getReizigerID());
			pstmnt.setString(2, r.getVoorletters());
			pstmnt.setString(3, r.getTussenvoegsel());
			pstmnt.setString(4, r.getAchternaam());
			pstmnt.setDate(5, r.getGeboortedatum());
			pstmnt.setInt(6, r.getReizigerID());

			pstmnt.executeUpdate();

			OVChipkaartOracleDAOImpl ovdao = new OVChipkaartOracleDAOImpl();
			for (OVChipkaart k : r.getOvchipkaarten()) {
			}

			pstmnt.close();
		}
		catch (SQLException sqlex) {
			System.out.println("SQLException: " + sqlex.getMessage());
		}

		return r;
	}

	public boolean delete(Reiziger r) {
		try {
			OVChipkaartOracleDAOImpl ovdao = new OVChipkaartOracleDAOImpl();
			for (OVChipkaart k : r.getOvchipkaarten()) {
				ovdao.delete(k);
			}

			PreparedStatement pstmnt = conn.prepareStatement("DELETE FROM reiziger WHERE reizigerid = ?");
			pstmnt.setInt(1, r.getReizigerID());

			pstmnt.executeUpdate();

			pstmnt.close();

			return true;
		}
		catch (SQLException sqlex) {
			System.out.println("SQLException: " + sqlex.getMessage());
		}

		return false;
	}

	public ArrayList<Reiziger> findByGBdatum(Date date) {
		ArrayList<Reiziger> reizigers = new ArrayList<Reiziger>();

		try {
			PreparedStatement pstmnt = conn.prepareStatement("SELECT reizigerid, voorletters, tussenvoegsel, achternaam, gebortedatum FROM reiziger WHERE gebortedatum = ?");
			pstmnt.setDate(1, date);

			ResultSet vResult = pstmnt.executeQuery();
			while (vResult.next()) {
				Reiziger r = new Reiziger(
						vResult.getInt("reizigerid"),
						vResult.getString("voorletters"),
						vResult.getString("tussenvoegsel"),
						vResult.getString("achternaam"),
						vResult.getDate("gebortedatum")
				);

				OVChipkaartOracleDAOImpl ovdao = new OVChipkaartOracleDAOImpl();
				ArrayList<OVChipkaart> kaarten = ovdao.findByReiziger(r);
				for (OVChipkaart k : kaarten) {
					r.voegToeOvchipkaart(k);
				}

				reizigers.add(r);
			}

			vResult.close();
			pstmnt.close();
		}
		catch (SQLException sqlex) {
			System.out.println("SQLException: " + sqlex.getMessage());
		}
		return reizigers;
	}

	public ArrayList<Reiziger> findAll() {
		ArrayList<Reiziger> reizigers = new ArrayList<Reiziger>();

		try {
			Statement stmnt = conn.createStatement();

			OVChipkaartOracleDAOImpl ovdao = new OVChipkaartOracleDAOImpl();

			ResultSet vResult = stmnt.executeQuery("SELECT reizigerid, voorletters, tussenvoegsel, achternaam, gebortedatum FROM reiziger");
			while (vResult.next()) {
				Reiziger r = new Reiziger();
				r.setReizigerID(vResult.getInt("reizigerid"));
				r.setVoorletters(vResult.getString("voorletters"));
				r.setTussenvoegsel(vResult.getString("tussenvoegsel"));
				r.setAchternaam(vResult.getString("achternaam"));
				r.setGeboortedatum(vResult.getDate("gebortedatum"));

				List<OVChipkaart> kaarten = ovdao.findByReiziger(r);
				for (OVChipkaart k : kaarten) {
					r.voegToeOvchipkaart(k);
				}

				reizigers.add(r);
			}

			vResult.close();
			stmnt.close();
		}
		catch (SQLException sqlex) {
			System.out.println("SQLException: " + sqlex.getMessage());
		}
		return reizigers;
	}
}
