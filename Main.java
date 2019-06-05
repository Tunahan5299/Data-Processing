import java.sql.*;
import java.util.*;

public class Main {

	public static void main(String[] args) throws SQLException {
		ReizigerOracleDAOImpl rdao = new ReizigerOracleDAOImpl();
		OVChipkaartOracleDAOImpl ovdao = new OVChipkaartOracleDAOImpl();
		ProductOracleDAOImpl pdao = new ProductOracleDAOImpl();

		int nummer = 11223;
		OVChipkaart test = new OVChipkaart();
		test.setKaartNummer(nummer);
		ArrayList<Product> producten = pdao.findByOVChipkaart(test);
		System.out.println("---- Producten op kaart " + nummer + ":");
		for (Product p : producten) {
			System.out.println(p);
		}
		System.out.println();


		printAllOVChipkaarten(ovdao);
		printAllReizigers(rdao);


		System.out.println("---- Voeg 1 reiziger, 2 ov-chipkaarten en 2 producten toe:");
		Reiziger tunahan = new Reiziger(52, "TU", " ", "Guler",  java.sql.Date.valueOf("1999-11-15"));
		OVChipkaart tunahanovkaart = new OVChipkaart(12345, java.sql.Date.valueOf("2023-01-21"), 2, (float) 99.99, tunahan);
		OVChipkaart tunahanovkaart1 = new OVChipkaart(54321, java.sql.Date.valueOf("2024-01-22"), 1, (float) 87.43, tunahan);
		Product dagkaartbaby = new Product(100, "Dagkaart baby", "Met je kind op reis", (float) 4.99);
		Product dagkaartbejaard = new Product(101, "Dagkaart bejaard", "Met je opa of oma op reis", (float) 5.99);

		tunahanovkaart.voegToeProduct(dagkaartbejaard);
		tunahanovkaart.voegToeProduct(dagkaartbaby);
		tunahanovkaart1.voegToeProduct(dagkaartbaby);

		dagkaartbejaard.voegToeOVChipkaartNummer(tunahanovkaart.getKaartNummer());
		dagkaartbaby.voegToeOVChipkaartNummer(tunahanovkaart.getKaartNummer());
		dagkaartbaby.voegToeOVChipkaartNummer(tunahanovkaart1.getKaartNummer());

		tunahan.voegToeOvchipkaart(tunahanovkaart);
		tunahan.voegToeOvchipkaart(tunahanovkaart1);

		rdao.save(tunahan);
		pdao.save(dagkaartbaby);
		pdao.save(dagkaartbejaard);
		printAllReizigers(rdao);

		System.out.println("---- Aanpassen van de naam van de reiziger, het saldo van een ov-chipkaart, de beschrijving van het product en de associatie van de twee:");
		tunahanovkaart.setSaldo((float) 88.75);
		tunahanovkaart.verwijderProduct(dagkaartbaby);
		dagkaartbaby.verwijderOVChipkaartNummer(tunahanovkaart.getKaartNummer());
		dagkaartbaby.setBeschrijving("Alleen babys onder de 1 jaar");
		pdao.update(dagkaartbaby);
		ovdao.update(tunahanovkaart);
		tunahan.setAchternaam("Güler");
		rdao.update(tunahan);

		String gbdatum = "1999-11-15";
		ArrayList<Reiziger> rList = rdao.findByGBdatum(java.sql.Date.valueOf(gbdatum));
		System.out.println("---- Reizigers met geboortedatum " + gbdatum + ": ");
		for (Reiziger r : rList) {
			System.out.println(r);
		}
		System.out.println();

		rdao.delete(tunahan);
		pdao.delete(dagkaartbaby);
		pdao.delete(dagkaartbejaard);
		ovdao.delete(tunahanovkaart1);
		printAllReizigers(rdao);

		rdao.closeConnection();
	}

	public static void printAllOVChipkaarten(OVChipkaartDAO ovdao) throws SQLException {
		System.out.println("---- Alle OV-Chipkaarten in de database:");

		List<OVChipkaart> rList = ovdao.findAll();

		for (OVChipkaart k : rList) {
			System.out.println(k);
		}
		System.out.println();
	}

	public static void printAllReizigers(ReizigerDAO rDAO) throws SQLException {
		System.out.println("---- Alle reizigers in de database:");

		List<Reiziger> rList = rDAO.findAll();

		for (Reiziger r : rList) {
			System.out.println(r);
		}
		System.out.println();
	}
}
