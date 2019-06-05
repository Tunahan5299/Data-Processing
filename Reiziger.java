import java.sql.Date;
import java.util.ArrayList;


public class Reiziger {
	private int reizigerID; 
	private String voorletters;
	private String tussenvoegsel;
	private String achternaam;
	private Date geboortedatum;

	private ArrayList<OVChipkaart> ovchipkaarten;

	public Reiziger() {
		this.ovchipkaarten = new ArrayList<OVChipkaart>();
	}

	public Reiziger(int reizigerID, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
		super();
		this.reizigerID = reizigerID;
		this.voorletters = voorletters;
		this.tussenvoegsel = tussenvoegsel;
		this.achternaam = achternaam;
		this.geboortedatum = geboortedatum;
		this.ovchipkaarten = new ArrayList<OVChipkaart>();
	}

	public int getReizigerID() {
		return reizigerID;
	}

	public void setReizigerID(int reizigerID) {
		this.reizigerID = reizigerID;
	}

	public String getVoorletters() {
		return voorletters;
	}

	public void setVoorletters(String voorletters) {
		this.voorletters = voorletters;
	}

	public String getTussenvoegsel() {
		return tussenvoegsel;
	}

	public void setTussenvoegsel(String tussenvoegsel) {
		this.tussenvoegsel = tussenvoegsel;
	}

	public String getAchternaam() {
		return achternaam;
	}

	public void setAchternaam(String achternaam) {
		this.achternaam = achternaam;
	}

	public Date getGeboortedatum() {
		return geboortedatum;
	}

	public void setGeboortedatum(Date geboortedatum) {
		this.geboortedatum = geboortedatum;
	}

	public ArrayList<OVChipkaart> getOvchipkaarten() {
		return ovchipkaarten;
	}

	public boolean voegToeOvchipkaart(OVChipkaart k) {
		return this.ovchipkaarten.add(k);
	}

	public boolean verwijderOVChipkaart(OVChipkaart k) {
		return this.ovchipkaarten.remove(k);
	}

	public String getNaam() {
		return voorletters + ". " + (tussenvoegsel == null ? "" : tussenvoegsel + " ") + achternaam;
	}

	public String toString() {
		String sReiziger = "[" + this.getClass().getSimpleName() + "] {" + reizigerID + ", " + getNaam() + ", geb. " + geboortedatum + "}";

		for (OVChipkaart k : ovchipkaarten) {
			sReiziger += "\n  " + k.toString();
		}

		return sReiziger;
	}
}
