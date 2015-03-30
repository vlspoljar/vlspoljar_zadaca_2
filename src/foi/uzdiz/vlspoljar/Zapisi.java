package foi.uzdiz.vlspoljar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Zapisi {

    private static Zapisi uniqueInstance;
    public List<String> listaZapisa = new ArrayList<String>();
    public List<Korisnik> listaKorisnika = new ArrayList<Korisnik>();
    public List<Grupa> listaGrupa = new ArrayList<Grupa>();
    public List<Objekt> listaObjekata = new ArrayList<Objekt>();
    public List<Akcija> listaAkcija = new ArrayList<Akcija>();
    public List<String> listaDefiniranja = new ArrayList<String>();
    public List<String> listaStruktura = new ArrayList<String>();
    public List<String> listaAkcije = new ArrayList<String>();
    public Korisnik korisnik;
    public Grupa grupa;
    public Objekt objekt;
    public Akcija akcija;
    public int duljinaZapisa = 0;
    public int brojacAkcija = 1;

    private Zapisi() {
    }

    public static Zapisi getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Zapisi();
        }
        return uniqueInstance;
    }

    public List<String> getListaZapisa() {
        return listaZapisa;
    }

    public void setListaZapisa(ArrayList<String> listaZapisa) {
        this.listaZapisa = listaZapisa;
    }

    public List<Akcija> getListaAkcija() {
        return listaAkcija;
    }

    public void setListaAkcija(List<Akcija> listaAkcija) {
        this.listaAkcija = listaAkcija;
    }

    public void ucitavanjeDatoteke(String putanjaPrograma, String putanjaDatoteke) {
        String path = "";
        if (putanjaDatoteke.startsWith(".\\")) {
            path = putanjaDatoteke;
        } else {
            path = putanjaPrograma + "\\" + putanjaDatoteke;
        }
        
        try (BufferedReader b = new BufferedReader(new FileReader(new File(path)))) {
            StringBuilder sb = new StringBuilder();
            String line = b.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = b.readLine();
            }
            String everything = sb.toString();
            String[] e = everything.split("\\r?\\n");
            for (String string : e) {
                listaZapisa.add(string);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void provjeraDatoteke() {
        duljinaZapisa = listaZapisa.size();
        for (int i = 0; i < listaZapisa.size(); i++) {
            provjeraZapisa(listaZapisa.get(i));
        }
    }

    public void ucitavanjeDodatneDatoteke() {
        for (int i = duljinaZapisa; i < listaZapisa.size(); i++) {
            provjeraZapisa(listaZapisa.get(i));
        }
        duljinaZapisa = listaZapisa.size();
    }

    public void unosDodatneAkcije(String sNA, String sNR, String tNA, String vNA) {
        String novaAkcija = "2\t" + sNA + "\t" + sNR + "\t" + tNA + "\t" + vNA;
        listaZapisa.add(novaAkcija);
        provjeraZapisa(listaZapisa.get(duljinaZapisa));
        duljinaZapisa++;

    }

    public void brisanjePostojeceAkcije(String sifra, int id) {
        boolean obrisano = false;
        for (Akcija a : listaAkcija) {
            if (a.getIdAkcije() == id) {
                if (a.getSifraRoditelja().equals(sifra) && a.getVrstaAkcije() == 0) {//OSOBNA KORISNIK
                    listaAkcija.remove(a);
                    obrisano = true;
                    System.out.println("Akcija " + a.getIdAkcije() + " obrisana!");
                    break;
                }
                for (Grupa g : listaGrupa) {
                    if (g.getSifra().equals(g.getSifra()) && (a.getVrstaAkcije() == 0 || a.getVrstaAkcije() == 1)) {//OSOBNA/GRUPNA GRUPA
                        for (IComponent ic : g.listaAdmina) {
                            if (ic.getSifra().equals(sifra)) {
                                listaAkcija.remove(a);
                                obrisano = true;
                                System.out.println("Admin: Akcija " + a.getIdAkcije() + " obrisana!");
                                break;
                            }
                        }
                        break;
                    }
                }
                if (a.getVrstaAkcije() == 2) {//JAVNA
                    for (Grupa g : listaGrupa) {
                        if (g.getSifra().equals(ishodisnaGrupa().getSifra())) {
                            for (IComponent ic : g.listaDjece) {
                                if (ic.getSifra().equals(sifra)) {
                                    listaAkcija.remove(a);
                                    obrisano = true;
                                    System.out.println("Ishodisni admin: Akcija " + a.getIdAkcije() + " obrisana!");
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    break;
                }
                break;
            }
        }
        if (!obrisano) {
            System.out.println("Akcija ne postoji ili nemate ovlasti za njezino brisanje!");
        }
    }

    public void pregledStanja(String sifra, int tip, int vrsta) {
        for (Grupa g : listaGrupa) {
            if (g.getSifra().equals(ishodisnaGrupa().getSifra())) {
                g.print();
            }
        }
    }

    public Grupa ishodisnaGrupa() {
        for (Grupa g : listaGrupa) {
            if (!g.isRoditelj()) {
                return g;
            }
        }
        return null;
    }

    public void provjeraZapisa(String s) {
        String regex = "^(((0)\\t([0-9]{5})\\t([A-Za-z0-9\\s]{1,20})\\t([0-2]))|((1)\\t([0-9]{5})\\t([0-9]{5})\\t([0-1]))|((2)\\t([0-9]{5})\\t([0-9]{5})\\t([0-2])\\t([0-2])))$";
        Matcher m = Pattern.compile(regex).matcher(s);
        if (m.matches()) {
            //DEFINIRANJE
            if (m.group(2) != null) {
                if (listaDefiniranja.isEmpty()) {
                    listaDefiniranja.add(m.group(4));
                    if (m.group(6).equals("0")) {
                        korisnik = new Korisnik(Integer.parseInt(m.group(3)), m.group(4), m.group(5), Integer.parseInt(m.group(6)));
                        listaKorisnika.add(korisnik);
                    }
                    if (m.group(6).equals("1")) {
                        grupa = new Grupa(Integer.parseInt(m.group(3)), m.group(4), m.group(5), Integer.parseInt(m.group(6)), false);
                        listaGrupa.add(grupa);
                    }
                    if (m.group(6).equals("2")) {
                        objekt = new Objekt(Integer.parseInt(m.group(3)), m.group(4), m.group(5), Integer.parseInt(m.group(6)));
                        listaObjekata.add(objekt);
                    }
                } else if (!listaDefiniranja.isEmpty() && !listaDefiniranja.contains(m.group(4))) {
                    listaDefiniranja.add(m.group(4));
                    if (m.group(6).equals("0")) {
                        korisnik = new Korisnik(Integer.parseInt(m.group(3)), m.group(4), m.group(5), Integer.parseInt(m.group(6)));
                        listaKorisnika.add(korisnik);
                    }
                    if (m.group(6).equals("1")) {
                        grupa = new Grupa(Integer.parseInt(m.group(3)), m.group(4), m.group(5), Integer.parseInt(m.group(6)), false);
                        listaGrupa.add(grupa);
                    }
                    if (m.group(6).equals("2")) {
                        objekt = new Objekt(Integer.parseInt(m.group(3)), m.group(4), m.group(5), Integer.parseInt(m.group(6)));
                        listaObjekata.add(objekt);
                    }
                } else {
                    System.out.println("Dupla sifra definiranja: " + s);
                }
            }
            //STRUKTURA
            if (m.group(7) != null) {
                if (listaStruktura.isEmpty()) {
                    listaStruktura.add(s);
                    for (Grupa gRoditelj : listaGrupa) {
                        if (gRoditelj.getSifra().equals(m.group(10))) {
                            for (Grupa gDijete : listaGrupa) {
                                if (gDijete.getSifra().equals(m.group(9))) {
                                    gRoditelj.add(gDijete);
                                    gDijete.setRoditelj(true);
                                    if (m.group(11).equals("1")) {
                                        gRoditelj.listaAdmina.add(gDijete);
                                    }
                                }
                            }
                            for (Korisnik kDijete : listaKorisnika) {
                                if (kDijete.getSifra().equals(m.group(9))) {
                                    gRoditelj.add(kDijete);
                                    if (m.group(11).equals("1")) {
                                        gRoditelj.listaAdmina.add(kDijete);
                                    }
                                }
                            }
                        }
                    }
                } else if (!listaStruktura.isEmpty() && !listaStruktura.contains(s)) {
                    listaStruktura.add(s);
                    for (Grupa gRoditelj : listaGrupa) {
                        if (gRoditelj.getSifra().equals(m.group(10))) {
                            for (Grupa gDijete : listaGrupa) {
                                if (gDijete.getSifra().equals(m.group(9))) {
                                    gRoditelj.add(gDijete);
                                    gDijete.setRoditelj(true);
                                    if (m.group(11).equals("1")) {
                                        gRoditelj.listaAdmina.add(gDijete);
                                    }
                                }
                            }
                            for (Korisnik kDijete : listaKorisnika) {
                                if (kDijete.getSifra().equals(m.group(9))) {
                                    gRoditelj.add(kDijete);
                                    if (m.group(11).equals("1")) {
                                        gRoditelj.listaAdmina.add(kDijete);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    System.out.println("Dupla struktura: " + s);
                }
            }
            //AKCIJE
            if (m.group(12) != null) {
                if (listaAkcije.isEmpty()) {
                    listaAkcije.add(s);
                    for (Objekt ob : listaObjekata) {
                        if (ob.getSifra().equals(m.group(14))) {
                            for (Grupa g : listaGrupa) {
                                if (g.getSifra().equals(m.group(15))) {
                                    akcija = new Akcija(brojacAkcija, m.group(14), m.group(15), Integer.parseInt(m.group(16)), Integer.parseInt(m.group(17)));
                                    listaAkcija.add(akcija);
                                    brojacAkcija++;
                                }
                            }
                            for (Korisnik k : listaKorisnika) {
                                if (k.getSifra().equals(m.group(15))) {
                                    akcija = new Akcija(brojacAkcija, m.group(14), m.group(15), Integer.parseInt(m.group(16)), Integer.parseInt(m.group(17)));
                                    listaAkcija.add(akcija);
                                    brojacAkcija++;
                                }
                            }
                        }
                    }
                } else if (!listaAkcije.isEmpty() && !listaAkcije.contains(s)) {
                    listaAkcije.add(s);
                    for (Objekt ob : listaObjekata) {
                        if (ob.getSifra().equals(m.group(14))) {
                            for (Grupa g : listaGrupa) {
                                if (g.getSifra().equals(m.group(15))) {
                                    akcija = new Akcija(brojacAkcija, m.group(14), m.group(15), Integer.parseInt(m.group(16)), Integer.parseInt(m.group(17)));
                                    listaAkcija.add(akcija);
                                    brojacAkcija++;
                                }
                            }
                            for (Korisnik k : listaKorisnika) {
                                if (k.getSifra().equals(m.group(15))) {
                                    akcija = new Akcija(brojacAkcija, m.group(14), m.group(15), Integer.parseInt(m.group(16)), Integer.parseInt(m.group(17)));
                                    listaAkcija.add(akcija);
                                    brojacAkcija++;
                                }
                            }
                        }
                    }
                } else {
                    System.out.println("Dupla akcija: " + s);
                }
            }
        } else {
            System.out.println("Kriva sintaksa zapisa: " + s);
        }
    }

}
