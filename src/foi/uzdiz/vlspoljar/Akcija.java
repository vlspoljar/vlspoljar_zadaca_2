package foi.uzdiz.vlspoljar;

public class Akcija {
    private int idAkcije;
    private String sifraObjekta;
    private String sifraRoditelja;
    private int tipAkcije;
    private int vrstaAkcije;

    public Akcija(int idAkcije, String sifraObjekta, String sifraRoditelja, int tipAkcije, int vrstaAkcije) {
        this.idAkcije = idAkcije;
        this.sifraObjekta = sifraObjekta;
        this.sifraRoditelja = sifraRoditelja;
        this.tipAkcije = tipAkcije;
        this.vrstaAkcije = vrstaAkcije;
    }

    public int getIdAkcije() {
        return idAkcije;
    }

    public void setIdAkcije(int idAkcije) {
        this.idAkcije = idAkcije;
    }

    public String getSifraObjekta() {
        return sifraObjekta;
    }

    public void setSifraObjekta(String sifraObjekta) {
        this.sifraObjekta = sifraObjekta;
    }

    public String getSifraRoditelja() {
        return sifraRoditelja;
    }

    public void setSifraRoditelja(String sifraRoditelja) {
        this.sifraRoditelja = sifraRoditelja;
    }

    public int getTipAkcije() {
        return tipAkcije;
    }

    public void setTipAkcije(int tipAkcije) {
        this.tipAkcije = tipAkcije;
    }

    public int getVrstaAkcije() {
        return vrstaAkcije;
    }

    public void setVrstaAkcije(int vrstaAkcije) {
        this.vrstaAkcije = vrstaAkcije;
    }
    
    
}
