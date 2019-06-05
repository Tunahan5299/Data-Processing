import java.util.ArrayList;


public interface OVChipkaartDAO {
	OVChipkaart save(OVChipkaart k);
	OVChipkaart update(OVChipkaart r);
	boolean delete(OVChipkaart r);
	ArrayList<OVChipkaart> findAll();
	ArrayList<OVChipkaart> findByReiziger(Reiziger r);

}
