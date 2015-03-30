package foi.uzdiz.vlspoljar;

public class Korisnik implements IComponent{ 
    private int tip;
    private String sifra;
    private String naziv;
    private int vrsta;

    public Korisnik(int tip, String sifra, String naziv, int vrsta) {
        this.tip = tip;
        this.sifra = sifra;
        this.naziv = naziv;
        this.vrsta = vrsta;
    }

    @Override
    public int getTip() {
        return tip;
    }

    @Override
    public String getSifra() {
        return sifra;
    }

    @Override
    public String getNaziv() {
        return naziv;
    }

    @Override
    public int getVrsta() {
        return vrsta;
    }

    @Override
    public void print() {
        System.out.println("-------Korisnik------");
        System.out.println("Sifra: " + getSifra());
        System.out.println("Naziv: " + getNaziv());
        System.out.println("---------------------");
    }
    
}
