
import java.util.EnumMap;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;





public class AppTest {
    @Test 
    void initialiseRoomsHappyPath() throws Exception
    {
        int size = App.initialise_room_messages().size();
        assertEquals(7, size);
    }

   @Test 
   void tryToPrintMapAfterClear(){
    EnumMap<Room, String> testMap = null;
    
    assertThrows(Exception.class, ()-> {App.PrintRoomMessage(testMap, Room.CAFE);});

   }
}