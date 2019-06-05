import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Product {
    private int productNummer;
    private String productNaam;
    private String beschrijving;
    private float prijs;

    private List<Integer> OVChipkaartNummers;

    public Product() {
        this.OVChipkaartNummers = new ArrayList<Integer>();
    }

    public Product(int productNummer, String productNaam, String beschrijving, float prijs) {
        this();

        this.productNummer = productNummer;
        this.productNaam = productNaam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public int getProductNummer() {
        return productNummer;
    }

    public void setProductNummer(int productNummer) {
        this.productNummer = productNummer;
    }

    public String getProductNaam() {
        return productNaam;
    }

    public void setProductNaam(String productNaam) {
        this.productNaam = productNaam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public float getPrijs() {
        return prijs;
    }

    public void setPrijs(float prijs) {
        this.prijs = prijs;
    }

    public List<Integer> getOVChipkaartNummers() {
        return OVChipkaartNummers;
    }

    public boolean voegToeOVChipkaartNummer(Integer OVChipkaartNummer) {
        this.OVChipkaartNummers.add(OVChipkaartNummer);

        return true;
    }

    public boolean verwijderOVChipkaartNummer(Integer OVChipkaartNummer) {
        this.OVChipkaartNummers.remove(OVChipkaartNummer);

        return true;
    }

    public String toString() {
        return "[" + this.getClass().getSimpleName() + "] {" + productNummer + ", " + productNaam + " ("
                + beschrijving + "), € " + prijs + ", staat op " + OVChipkaartNummers.size() + " kaarten}";
    }
}
