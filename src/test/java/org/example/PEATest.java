package org.example;

import org.example.enums.Devise;
import org.example.enums.TypeOrdre;
import org.example.exceptions.TransactionFractionneeInvalideException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PEATest {

    @Test
    void testAchatFractionneInterdit() {
        // 1. Créer un PEA
        PEA pea = new PEA("PEA-TEST-001", 10000.0);

        // 2. Tenter un achat de 1.5 de support quelconque
        Action actionTest = new Action("TEST", "Test Action", 100.0, "France");
        Ordre ordreFractionne = new Ordre(actionTest, LocalDateTime.now(), TypeOrdre.ACHAT, 1.5, Devise.EUR, 100.0);

        // 3. Vérifier la levée de TransactionFractionneeInvalideException
        assertThrows(TransactionFractionneeInvalideException.class, () -> {
            pea.passerUnOrdre(ordreFractionne);
        }, "Une exception TransactionFractionneeInvalideException aurait dû être levée.");
    }
}
