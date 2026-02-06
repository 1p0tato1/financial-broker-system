package org.example;

import org.example.enums.Devise;
import org.example.enums.TypeOrdre;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        // Création Client et Adresse
        Adresse adresse = new Adresse("12", "Rue de la Bourse", "Bat A", "75002", "Paris");
        Client client = new Client(1, "Dupont", "Marie", adresse);

        System.out.println("Client : " + client.getPrenom() + " " + client.getNom());

        // Ouverture d'un CTO avec 10 000€
        CTO cto = new CTO("CTO-001", 10000.0);
        client.ajouterEnveloppe(cto);
        System.out.println("Compte CTO ouvert. Solde : " + cto.getSoldeEspeces());

        // Création de l'Actif (4 arguments seulement : Ticker, Nom, Prix, Pays)
        Action apple = new Action("AAPL", "Apple Inc.", 150.0, "USA");

        // Passage d'un ordre d'ACHAT (10 actions)
        System.out.println("\n--- ACHAT DE 10 APPLE ---");
        Ordre ordreAchat = new Ordre(apple, LocalDateTime.now(), TypeOrdre.ACHAT, 10, Devise.USD, 150.0);
        cto.passerUnOrdre(ordreAchat);

        // Vérification après achat
        System.out.println("Nouveau solde : " + cto.getSoldeEspeces()); // Doit être 8500.0

        // Passage d'un ordre de VENTE (5 actions à prix plus élevé)
        System.out.println("\n--- VENTE DE 5 APPLE (Plus-value) ---");
        Ordre ordreVente = new Ordre(apple, LocalDateTime.now(), TypeOrdre.VENTE, 5, Devise.USD, 160.0);
        cto.passerUnOrdre(ordreVente);

        // Bilan Final
        System.out.println("\n--- BILAN ---");
        System.out.println("Solde final : " + cto.getSoldeEspeces());
        System.out.println("Valeur du portefeuille : " + cto.getValeurTotaleBrute());

        System.out.println("Fiscalité (CTO 30%) : " + cto.calculerFiscalite());
    }
}