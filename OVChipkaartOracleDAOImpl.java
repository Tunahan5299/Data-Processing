import java.sql.*;
import java.util.ArrayList;


public class OVChipkaartOracleDAOImpl extends OracleBaseDAO implements OVChipkaartDAO {

	public OVChipkaartOracleDAOImpl() throws SQLException {
		super();
	}

	public OVChipkaart save(OVChipkaart k) {
		try {
			PreparedStatement pstmnt = conn.prepareStatement("INSERT INTO ov_chipkaart (kaartNummer, geldigTot, klasse, saldo, reizigerID) VALUES (?, ?, ?, ?, ?)");
			pstmnt.setInt(1, k.getKaartNummer());
			pstmnt.setDate(2, k.getGeldigTot());
			pstmnt.setInt(3, k.getKlasse());
			pstmnt.setFloat(4, k.getSaldo());
			pstmnt.setInt(5, k.getEigenaar().getReizigerID());

			pstmnt.executeUpdate();

			pstmnt.close();
		}
		catch (SQLException sqlex) {
			System.out.println("SQLException: " + sqlex.getMessage());
		}

		return k;
	}

	public OVChipkaart update(OVChipkaart k) {
		try {
			PreparedStatement pstmnt = conn.prepareStatement("UPDATE ov_chipkaart SET geldigTot = ?, klasse = ?, saldo = ?, reizigerID = ? WHERE kaartNummer = ?");
			pstmnt.setDate(1, k.getGeldigTot());
			pstmnt.setInt(2, k.getKlasse());
			pstmnt.setFloat(3, k.getSaldo());
			pstmnt.setInt(4, k.getEigenaar().getReizigerID());
			pstmnt.setInt(5, k.getKaartNummer());

			pstmnt.executeUpdate();

			pstmnt.close();
		}
		catch (SQLException sqlex) {
			System.out.println("SQLException: " + sqlex.getMessage());
		}

		return k;
	}

	public boolean delete(OVChipkaart k) {
		try {
			PreparedStatement pstmnt = conn.prepareStatement("DELETE FROM ov_chipkaart_product WHERE kaartNummer = ?");
			pstmnt.setInt(1, k.getKaartNummer());
			pstmnt.executeUpdate();

			pstmnt = conn.prepareStatement("DELETE FROM ov_chipkaart WHERE kaartNummer = ?");
			pstmnt.setInt(1, k.getKaartNummer());
			pstmnt.executeUpdate();

			return true;
		} catch (SQLException sqlex) {
			System.out.println("SQLException: " + sqlex.getMessage());
		}

		return false;
	}

	public ArrayList<OVChipkaart> findByReiziger(Reiziger r) {
		ArrayList<OVChipkaart> kaarten = new ArrayList<OVChipkaart>();

		try {
			ProductOracleDAOImpl pdao = new ProductOracleDAOImpl();

			PreparedStatement pstmnt = conn.prepareStatement("SELECT kaartNummer, geldigTot, klasse, saldo FROM ov_chipkaart WHERE reizigerid = ?");
			pstmnt.setInt(1, r.getReizigerID());

			ResultSet vResult = pstmnt.executeQuery();
			while (vResult.next()) {
				OVChipkaart kaart = new OVChipkaart();
				kaart.setKaartNummer(vResult.getInt("kaartNummer"));
				kaart.setGeldigTot(vResult.getDate("geldigTot"));
				kaart.setKlasse(vResult.getInt("klasse"));
				kaart.setSaldo(vResult.getFloat("saldo"));
				kaart.setEigenaar(r);

				kaart.setProducten(pdao.findByOVChipkaart(kaart));

				kaarten.add(kaart);
			}

			vResult.close();
			pstmnt.close();
		}
		catch (SQLException sqlex) {
			System.out.println("SQLException: " + sqlex.getMessage());
		}
		return kaarten;
	}

	public ArrayList<OVChipkaart> findAll() {
		ArrayList<OVChipkaart> kaarten = new ArrayList<OVChipkaart>();

		try {
			ReizigerDAO reizigerDAO = new ReizigerOracleDAOImpl();
			ArrayList<Reiziger> reizigers = reizigerDAO.findAll();

			for (Reiziger r : reizigers) {
				kaarten.addAll(r.getOvchipkaarten());
			}
		}
		catch (SQLException sqlex) {
			System.out.println("SQLException: " + sqlex.getMessage());
		}
		return kaarten;
	}
}
