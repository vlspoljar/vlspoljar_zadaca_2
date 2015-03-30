package foi.uzdiz.vlspoljar;

public class Objekt {
    private int tip;
    private String sifra;
    private String naziv;
    private int vrsta;

    public Objekt(int tip, String sifra, String naziv, int vrsta) {
        this.tip = tip;
        this.sifra = sifra;
        this.naziv = naziv;
        this.vrsta = vrsta;
    }

    public int getTip() {
        return tip;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getVrsta() {
        return vrsta;
    }

    public void setVrsta(int vrsta) {
        this.vrsta = vrsta;
    }
    
}
