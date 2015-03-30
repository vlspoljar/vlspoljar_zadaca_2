package foi.uzdiz.vlspoljar;

import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        Zapisi zapisi = Zapisi.getInstance();

        zapisi.ucitavanjeDatoteke(args[0], args[1]);
        zapisi.provjeraDatoteke();

        String izbor = "";
        do {
            System.out.println("\n==========IZBORNIK==========");
            System.out.println("1) pregled stanja");
            System.out.println("2) unos dodatne akcije");
            System.out.println("3) brisanje postojece akcije");
            System.out.println("4) ucitavanje dodatne datoteke");
            System.out.println("5) izlaz iz programa");
            System.out.println("Vas izbor: ");
            Scanner scan = new Scanner(System.in);
            izbor = scan.nextLine();
            switch (izbor) {
                case "1":
                    System.out.println("\n===Pregled stanja===");
                    System.out.println("Unesite sifru korisnika/grupe/objekta: ");
                    String sifra = scan.nextLine();
                    System.out.println("Unesite tip akcije: ");
                    int tip = scan.nextInt();
                    System.out.println("Unesite vrstu akcije: ");
                    int vrsta = scan.nextInt();
                    zapisi.pregledStanja(sifra, tip, vrsta);
                    break;
                case "2":
                    System.out.println("\n====Unos akcije====");
                    System.out.println("Unesite sifru objekta: ");
                    String sifraNoveAkcije = scan.nextLine();
                    System.out.println("Unesite sifru korisnika/grupe: ");
                    String sifraNovogRoditelja = scan.nextLine();
                    System.out.println("Unesite tip akcije: ");
                    String tipNoveAkcije = scan.nextLine();
                    System.out.println("Unesite vrstu akcije: ");
                    String vrstaNoveAkcije = scan.nextLine();
                    zapisi.unosDodatneAkcije(sifraNoveAkcije, sifraNovogRoditelja, tipNoveAkcije, vrstaNoveAkcije);
                    break;
                case "3":
                    System.out.println("\n==Brisanje akcije==");
                    System.out.println("Unesite sifru korisnika/grupe: ");
                    String sifraBrisanje = scan.nextLine();
                    System.out.println("Unesite identifikator akcije: ");
                    int idBrisanja = scan.nextInt();
                    zapisi.brisanjePostojeceAkcije(sifraBrisanje, idBrisanja);
                    break;
                case "4":
                    System.out.println("\n===Unos datoteke===");
                    System.out.println("Unesite naziv dodatne datoteke: ");
                    String dodatnaDatoteka = scan.nextLine();
                    zapisi.ucitavanjeDatoteke(args[0], dodatnaDatoteka);
                    zapisi.ucitavanjeDodatneDatoteke();
                    break;
                case "5":
                    System.out.println("\nIzlaz iz programa!");
                    break;
            }
        } while (!izbor.equals("5"));

    }

}
