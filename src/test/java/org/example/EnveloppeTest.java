package org.example;

import org.example.enums.Devise;
import org.example.enums.TypeOrdre;
import org.example.exceptions.ActifNotFoundException;
import org.example.exceptions.TransactionFractionneeInvalideException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class EnveloppeTest {

    @Test
    void testRechercherActif_casNominal() throws ActifNotFoundException, TransactionFractionneeInvalideException {
        // 1. Créer une enveloppe
        Enveloppe cto = new CTO("CTO-TEST-001", 5000.0);

        // 2. Ajouter un actif
        Action microsoft = new Action("MSFT", "Microsoft Corp.", 400.0, "USA");
        Ordre achatMicrosoft = new Ordre(microsoft, LocalDateTime.now(), TypeOrdre.ACHAT, 10, Devise.USD, 400.0);
        cto.passerUnOrdre(achatMicrosoft);

        // 3. Rechercher l’actif
        Actif actifTrouve = cto.rechercherActif("MSFT");

        // 4. Vérifier que l’actif retourné est correct
        assertNotNull(actifTrouve, "L'actif ne devrait pas être null.");
        assertEquals("MSFT", actifTrouve.getTicker(), "Le ticker de l'actif trouvé est incorrect.");
        assertEquals("Microsoft Corp.", actifTrouve.getNom(), "Le nom de l'actif trouvé est incorrect.");
    }

    @Test
    void testRechercherActif_casErreur() {
        // 1. Créer une enveloppe
        Enveloppe cto = new CTO("CTO-TEST-002", 1000.0);

        // 2. Ajouter un actif (différent de celui recherché)
        Action apple = new Action("AAPL", "Apple Inc.", 150.0, "USA");
        Ordre achatApple = new Ordre(apple, LocalDateTime.now(), TypeOrdre.ACHAT, 5, Devise.USD, 150.0);
        try {
            cto.passerUnOrdre(achatApple);
        } catch (TransactionFractionneeInvalideException e) {
            fail("Une exception de transaction fractionnée ne devrait pas être levée ici.");
        }

        // 3. Rechercher un actif non existant et vérifier que l'exception est levée
        assertThrows(ActifNotFoundException.class, () -> cto.rechercherActif("GOOGL"), "Une exception ActifNotFoundException aurait dû être levée.");
    }
}
