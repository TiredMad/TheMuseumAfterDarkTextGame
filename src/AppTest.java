import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    @Test
    void validateLookAroundRoom() {
        assertEquals("you some batteries. they might be useful", App.lookAroundRoom(Room.GIFTSHOP));
        assertEquals("you see a keycard. it might be useful", App.lookAroundRoom(Room.STAFFROOM));
        assertEquals("you don't see any useful items", App.lookAroundRoom(Room.MAINHALL));
        assertThrows(NullPointerException.class, () -> App.lookAroundRoom(null));
    }
}
