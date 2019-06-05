import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ProductOracleDAOImpl extends OracleBaseDAO implements ProductDAO {

	public ProductOracleDAOImpl() throws SQLException {
		super();
	}


	public boolean save(Product p) {
		try {
			PreparedStatement pstmnt = conn.prepareStatement("INSERT INTO product (productNummer, productNaam, beschrijving, prijs) VALUES (?, ?, ?, ?)");
			pstmnt.setInt(1, p.getProductNummer());
			pstmnt.setString(2, p.getProductNaam());
			pstmnt.setString(3, p.getBeschrijving());
			pstmnt.setFloat(4, p.getPrijs());

			pstmnt.executeUpdate();

			for (int kaartNummer : p.getOVChipkaartNummers()) {
				// Dit werkt makkelijker als productnummer en kaartnummer samen PK zijn in de database.
				pstmnt = conn.prepareStatement("INSERT INTO ov_chipkaart_product (productnummer, kaartnummer, reisproductstatus, lastupdate) VALUES (?, ?, 'onbekend', CURRENT_TIMESTAMP)");
				pstmnt.setInt(1, p.getProductNummer());
				pstmnt.setInt(2, kaartNummer);
				pstmnt.executeUpdate();
			}

			pstmnt.close();
		}
		catch (SQLException sqlex) {
			System.out.println("[" + this.getClass().getSimpleName() + " - SQLException while saving] " + sqlex.getMessage());

			return false;
		}

		return true;
	}

	public boolean update(Product p) {

		try {
				PreparedStatement pstmnt = conn.prepareStatement("UPDATE product SET productNaam = ?, beschrijving = ?, prijs = ? WHERE productNummer = ?");
				pstmnt.setString(1, p.getProductNaam());
				pstmnt.setString(2, p.getBeschrijving());
				pstmnt.setFloat(3, p.getPrijs());
				pstmnt.setInt(4, p.getProductNummer());
				pstmnt.executeUpdate();

				pstmnt = conn.prepareStatement("DELETE FROM ov_chipkaart_product WHERE productNummer = ?");
				pstmnt.setInt(1, p.getProductNummer());
				pstmnt.executeUpdate();

				for (int kaartNummer : p.getOVChipkaartNummers()) {
					pstmnt = conn.prepareStatement("INSERT INTO ov_chipkaart_product (productnummer, kaartnummer, reisproductstatus, lastupdate) VALUES (?, ?, 'onbekend', CURRENT_TIMESTAMP)");
					pstmnt.setInt(1, p.getProductNummer());
					pstmnt.setInt(2, kaartNummer);
					pstmnt.executeUpdate();
				}

				pstmnt.close();

		}
		catch (SQLException sqlex) {
			System.out.println("[" + this.getClass().getSimpleName() + " - SQLException while updating] " + sqlex.getMessage());
		}

		return false;
	}

	public boolean delete(Product p) {
		try {
			PreparedStatement pstmnt = conn.prepareStatement("DELETE FROM ov_chipkaart_product WHERE productNummer = ?");
			pstmnt.setInt(1, p.getProductNummer());
			pstmnt.executeUpdate();

			pstmnt = conn.prepareStatement("DELETE FROM product WHERE productNummer = ?");
			pstmnt.setInt(1, p.getProductNummer());
			pstmnt.executeUpdate();

			return true;
		} catch (SQLException sqlex) {
			System.out.println("[" + this.getClass().getSimpleName() + " - SQLException while deleting] " + sqlex.getMessage());
		}

		return false;
	}

	public ArrayList<Product> findByOVChipkaart(OVChipkaart k) {
		ArrayList<Product> producten = new ArrayList<Product>();

		try {
			String sSQL = 	"SELECT p.productNummer, p.productNaam, p.beschrijving, p.prijs " +
							"FROM ov_chipkaart_product ov_p, product p " +
							"WHERE ov_p.productNummer = p.productNummer AND ov_p.kaartnummer = ?";
			PreparedStatement pstmnt = conn.prepareStatement(sSQL);
			pstmnt.setInt(1, k.getKaartNummer());

			ResultSet vResult = pstmnt.executeQuery();
			while (vResult.next()) {
				Product product = new Product();
				product.setProductNummer(vResult.getInt("productNummer"));
				product.setProductNaam(vResult.getString("productNaam"));
				product.setBeschrijving(vResult.getString("beschrijving"));
				product.setPrijs(vResult.getFloat("prijs"));

				pstmnt = conn.prepareStatement("SELECT kaartNummer FROM ov_chipkaart_product WHERE productNummer = ?");
				pstmnt.setInt(1, product.getProductNummer());

				ResultSet vResult2 = pstmnt.executeQuery();
				while (vResult2.next()) {
					product.voegToeOVChipkaartNummer(vResult2.getInt("kaartNummer"));
				}

				producten.add(product);
			}

			vResult.close();
			pstmnt.close();
		}
		catch (SQLException sqlex) {
			System.out.println("[" + this.getClass().getSimpleName() + " - SQLException while finding by OVChipkaart] " + sqlex.getMessage());
		}

		return producten;
	}

}
