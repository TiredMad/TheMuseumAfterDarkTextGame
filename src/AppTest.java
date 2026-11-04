
import java.util.EnumMap;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;





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
   
    @Test
    void validateLookAroundRoom() {
        assertEquals("you some batteries. they might be useful", App.lookAroundRoom(Room.GIFTSHOP));
        assertEquals("you see a keycard. it might be useful", App.lookAroundRoom(Room.STAFFROOM));
        assertEquals("you don't see any useful items", App.lookAroundRoom(Room.MAINHALL));
        assertThrows(NullPointerException.class, () -> App.lookAroundRoom(null));
    }

    //tests for initialising the object EnumMap and adding an item to your inventory

    @Test
    void AddItemToInventoryHappyPath() throws Exception {
        EnumMap<Room,Item> roomItems = App.InitialiseItemEnumMap();
        ArrayList<Item> inventory = new ArrayList<>();
        assertEquals(false, inventory.contains(Item.BATTERIES));
        App.PutItemInInventoryAndRemoveFromRoom(roomItems, Room.GIFTSHOP, inventory );

       assertEquals(true, inventory.contains(Item.BATTERIES));
       assertEquals(false, roomItems.containsValue(Item.BATTERIES));

       assertEquals(false, inventory.contains(Item.COIN));
        App.PutItemInInventoryAndRemoveFromRoom(roomItems, Room.CAFE, inventory );

       assertEquals(true, inventory.contains(Item.COIN));
       assertEquals(false, roomItems.containsValue(Item.COIN));

       assertEquals(false, inventory.contains(Item.GUIDEBOOK));
        App.PutItemInInventoryAndRemoveFromRoom(roomItems, Room.DINOSAURGALLERY, inventory );

       assertEquals(true, inventory.contains(Item.GUIDEBOOK));
       assertEquals(false, roomItems.containsValue(Item.GUIDEBOOK));

       assertEquals(false, inventory.contains(Item.KEYCARD));
        App.PutItemInInventoryAndRemoveFromRoom(roomItems, Room.STAFFROOM, inventory );

       assertEquals(true, inventory.contains(Item.KEYCARD));
       assertEquals(false, roomItems.containsValue(Item.KEYCARD));




    }
}
