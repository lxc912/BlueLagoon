package comp1110.ass2;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class MoveStringTest {
    @Test
    void testIsMoveStringWellFormed() {
        // 7 Test cases
        String validMoveString1 = "S 1,2";
        String validMoveString2 = "T 0,4";

        String invalidMoveString3 = "S 1,2,3";
        String invalidMoveString4 = "A 3,4";
        String invalidMoveString5 = "T 3";
        String invalidMoveString6 = "T 3.4";
        String invalidMoveString7 = "S 3,4,";

        // Assert that valid MoveString return True
        Assertions.assertTrue(BlueLagoon.isMoveStringWellFormed(validMoveString1));
        Assertions.assertTrue(BlueLagoon.isMoveStringWellFormed(validMoveString2));

        // Assert that invalid MoveString return False
        Assertions.assertFalse(BlueLagoon.isMoveStringWellFormed(invalidMoveString3));
        Assertions.assertFalse(BlueLagoon.isMoveStringWellFormed(invalidMoveString4));
        Assertions.assertFalse(BlueLagoon.isMoveStringWellFormed(invalidMoveString5));
        Assertions.assertFalse(BlueLagoon.isMoveStringWellFormed(invalidMoveString6));
        Assertions.assertFalse(BlueLagoon.isMoveStringWellFormed(invalidMoveString7));
    }
}