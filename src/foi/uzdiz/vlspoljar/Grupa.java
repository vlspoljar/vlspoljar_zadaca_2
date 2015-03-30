package foi.uzdiz.vlspoljar;

import java.util.ArrayList;
import java.util.List;

public class Grupa implements IComponent{
    private int tip;
    private String sifra;
    private String naziv;
    private int vrsta;
    private boolean roditelj;
    public List<IComponent> listaDjece = new ArrayList<IComponent>();
    public List<IComponent> listaAdmina = new ArrayList<IComponent>();

    public Grupa(int tip, String sifra, String naziv, int vrsta, boolean roditelj) {
        this.tip = tip;
        this.sifra = sifra;
        this.naziv = naziv;
        this.vrsta = vrsta;
        this.roditelj = roditelj;
    }

    public void add(IComponent component) {
        listaDjece.add(component);
    }
    
    public IComponent getChild(int i) {
        return listaDjece.get(i);
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

    public List<IComponent> getListaAdmina() {
        return listaAdmina;
    }

    public void setListaAdmina(List<IComponent> listaAdmina) {
        this.listaAdmina = listaAdmina;
    }

    public boolean isRoditelj() {
        return roditelj;
    }

    public void setRoditelj(boolean roditelj) {
        this.roditelj = roditelj;
    }

    @Override
    public void print() {
        System.out.println("--------Grupa--------");
        System.out.println("Sifra: " + getSifra());
        System.out.println("Naziv: " + getNaziv());
        System.out.println("---------------------");
        
        for (IComponent ic : listaDjece) {
            ic.print();
        }
    }
    
    public void remove(IComponent component) {
        listaDjece.remove(component);
    }
    
}
