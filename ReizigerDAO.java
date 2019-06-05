import java.sql.Date;
import java.sql.SQLException;
import java.util.*;

public interface ReizigerDAO {
	Reiziger save(Reiziger r) throws SQLException;
	ArrayList<Reiziger> findAll() throws SQLException;
	ArrayList<Reiziger> findByGBdatum(Date date) throws SQLException;
	Reiziger update(Reiziger r) throws SQLException;
	boolean delete(Reiziger r) throws SQLException;
}
