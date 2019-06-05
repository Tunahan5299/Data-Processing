import java.util.ArrayList;


public interface ProductDAO {
	boolean save(Product p);
	boolean update(Product p);
	boolean delete(Product p);
	ArrayList<Product> findByOVChipkaart(OVChipkaart k);
}
