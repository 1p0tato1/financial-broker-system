package org.example;

import org.example.enums.Devise;
import org.example.enums.TypeOrdre;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitaire pour générer des données de test.
 */
public class MockDataGenerator {

    /**
     * Crée et retourne une liste de clients avec des données pré-remplies.
     * @return Une liste de clients pour les tests.
     */
    public static List<Client> createMockClients() {
        List<Client> clients = new ArrayList<>();

        Adresse adresse1 = new Adresse("12", "Rue de la Bourse", "Bat A", "75002", "Paris");
        Client client1 = new Client(1, "Dupont", "Marie", adresse1);
        CTO cto1 = new CTO("CTO-001", 10000.0);
        client1.ajouterEnveloppe(cto1);
        Action apple = new Action("AAPL", "Apple Inc.", 150.0, "USA");
        Ordre ordreAchatApple = new Ordre(apple, LocalDateTime.now(), TypeOrdre.ACHAT, 10, Devise.USD, 150.0);
        cto1.passerUnOrdre(ordreAchatApple);

        // Ordre de vente qui modifie la position
        Ordre ordreVenteApple = new Ordre(apple, LocalDateTime.now(), TypeOrdre.VENTE, 5, Devise.USD, 160.0);
        cto1.passerUnOrdre(ordreVenteApple);

        clients.add(client1);


        Adresse adresse2 = new Adresse("45", "Avenue de la Grande Armée", "", "75016", "Paris");
        Client client2 = new Client(2, "Martin", "Paul", adresse2);
        CTO cto2 = new CTO("CTO-002", 50000.0);
        client2.ajouterEnveloppe(cto2);
        Action lvmh = new Action("LVMH", "LVMH Moët Hennessy", 800.0, "France");
        Ordre ordreAchatLVMH = new Ordre(lvmh, LocalDateTime.now(), TypeOrdre.ACHAT, 20, Devise.EUR, 800.0);
        cto2.passerUnOrdre(ordreAchatLVMH);
        clients.add(client2);

        return clients;
    }
}
