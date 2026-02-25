package org.example;

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
    }
}
