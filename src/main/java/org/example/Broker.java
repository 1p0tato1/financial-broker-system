package org.example;

import org.example.enums.Devise;
import org.example.enums.TypeOrdre;
import org.example.exceptions.ActifNotFoundException;
import org.example.exceptions.TransactionFractionneeInvalideException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Broker {

    void main() {
        List<Client> clients = MockDataGenerator.createMockClients();

        System.out.println("--- Affichage des données du Broker ---");

        for (Client client : clients) {
            System.out.println("\n=========================================");
            System.out.println("Client: " + client.getPrenom() + " " + client.getNom() + " (ID: " + client.getId() + ")");
            System.out.println("Adresse: " + client.getAdresses().iterator().next());
            System.out.println("-----------------------------------------");

            if (client.getEnveloppes().isEmpty()) {
                System.out.println("Ce client n'a aucune enveloppe.");
            } else {
                System.out.println("Enveloppes du client:");
                for (Enveloppe enveloppe : client.getEnveloppes()) {
                    System.out.println("\n  --- Enveloppe N°" + enveloppe.getNumero() + " (" + enveloppe.getClass().getSimpleName() + ") ---");
                    System.out.println("  Solde espèces: " + String.format("%.2f", enveloppe.getSoldeEspeces()) + " EUR");

                    // Affichage des Positions
                    if (enveloppe.getPortefeuille().isEmpty()) {
                        System.out.println("  --> Positions: Aucune position dans cette enveloppe.");
                    } else {
                        System.out.println("  --> Positions:");
                        for (Map.Entry<String, Position> entry : enveloppe.getPortefeuille().entrySet()) {
                            Position pos = entry.getValue();
                            System.out.println("    - " + pos.getActif().getNom() + " (" + pos.getActif().getTicker() + "): " + pos.getQuantite() + " unités");
                        }
                    }

                    // Affichage des Ordres (basé sur la liste 'ordreEnCours')
                    if (enveloppe.getOrdreEnCours().isEmpty()) {
                        System.out.println("  --> Historique d'ordres: Aucun ordre.");
                    } else {
                        System.out.println("  --> Historique d'ordres:");
                        for (Ordre ordre : enveloppe.getOrdreEnCours()) {
                            System.out.println("    - " + ordre.getType() + " " + ordre.getQuantite() + " " + ordre.getActif().getTicker() + " @ " + ordre.getPrixMinMax() + " " + ordre.getDevise());
                        }
                    }
                }
            }
            System.out.println("=========================================");
        }

        // Test de l'exception ActifNotFoundException
        System.out.println("\n--- Test de ActifNotFoundException ---");
        Enveloppe firstEnveloppe = clients.getFirst().getEnveloppes().get(0);
        try {
            System.out.println("Recherche d'un actif existant (AAPL)...");
            Actif actif = firstEnveloppe.rechercherActif("AAPL");
            System.out.println("Actif trouvé: " + actif.getNom());

            System.out.println("\nRecherche d'un actif inexistant (GOOGL)...");
            firstEnveloppe.rechercherActif("GOOGL");
        } catch (ActifNotFoundException e) {
            System.err.println("Erreur attendue: " + e.getMessage());
        }

        // Test de l'exception TransactionFractionneeInvalideException
        System.out.println("\n--- Test de TransactionFractionneeInvalideException ---");
        try {
            System.out.println("Tentative de passage d'un ordre avec une quantité fractionnée (1.5 unités)...");

            // On crée un actif et un ordre temporaires pour le test
            Actif actifTest = new Action("TEST", "Entreprise Test", 100.0, "France");
            Ordre ordreFractionne = new Ordre(actifTest, LocalDateTime.now(), TypeOrdre.ACHAT, 1.5, Devise.EUR, 100.0);

            // L'appel de cette méthode devrait déclencher l'exception
            firstEnveloppe.passerUnOrdre(ordreFractionne);

            System.out.println("Succès (Cette ligne ne devrait pas s'afficher si l'exception fonctionne bien).");
        } catch (TransactionFractionneeInvalideException e) {
            System.err.println("Erreur attendue: " + e.getMessage());
        }
    }
}
