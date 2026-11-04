<<<<<<< HEAD
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Scanner;

public class AppTest {

@Test
void IfInventoryIsEmptyMessageShown() {
        Scanner scanner = new Scanner("");
        ArrayList<Item> inventory = new ArrayList<>();
        EnumMap<Item, String> messages = new EnumMap<>(Item.class);

        String result = App.ExamineItemOfChoice(scanner, inventory, messages);

        assertEquals("Your pockets are empty.", result);


}

@Test 
void ExamineItemsInInventory(){
        Scanner scanner = new Scanner("BATTERIES");
        ArrayList<Item> inventory = new ArrayList<>();
        inventory.add(Item.BATTERIES);
        EnumMap<Item, String> messages = new EnumMap<>(Item.class);

        String result = App.ExamineItemOfChoice(scanner, inventory, messages);

        assertEquals("BATTERIES. These are needed for the radio once you reach the guard room.", result);

}

@Test
void ExamineItemsYouDontHave(){
       Scanner scanner = new Scanner("BATTERIES");
       ArrayList<Item> inventory = new ArrayList<>();
       inventory.add(Item.KEYCARD);
       EnumMap<Item, String> messages = new EnumMap<>(Item.class);

       String result = App.ExamineItemOfChoice(scanner, inventory, messages);

       assertEquals("You don't have that item.", result);


}

}
=======

import java.util.EnumMap;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.EnumMap;

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
    
    // test the correct room is given when the direction is valid
    // covering every direction at least once
    // every current location at least once
    // every return location at least once
    @Test 
    void testGetNextRoomValid() throws Exception {
        EnumMap<Room, EnumMap<Direction, Room>> map = App.initialiseMap();

        assertEquals(Room.MAINHALL, App.getNextRoom(Direction.NORTH, Room.CAFE, map));
        assertEquals(Room.GIFTSHOP, App.getNextRoom(Direction.EAST, Room.MAINHALL, map));
        assertEquals(Room.MAINHALL, App.getNextRoom(Direction.SOUTH, Room.UPPERLANDING, map));
        assertEquals(Room.MAINHALL, App.getNextRoom(Direction.WEST, Room.GIFTSHOP, map));
        assertEquals(Room.MAINHALL, App.getNextRoom(Direction.EAST, Room.DINOSAURGALLERY, map));
        assertEquals(Room.UPPERLANDING, App.getNextRoom(Direction.EAST, Room.STAFFROOM, map));
        assertEquals(Room.UPPERLANDING, App.getNextRoom(Direction.SOUTH, Room.GUARDROOM, map));

        assertEquals(Room.CAFE, App.getNextRoom(Direction.SOUTH, Room.MAINHALL, map));
        assertEquals(Room.DINOSAURGALLERY, App.getNextRoom(Direction.WEST, Room.MAINHALL, map));
        assertEquals(Room.STAFFROOM, App.getNextRoom(Direction.WEST, Room.UPPERLANDING, map));
        assertEquals(Room.GUARDROOM, App.getNextRoom(Direction.NORTH, Room.UPPERLANDING, map));
    }

    // testing every room where there is a possibility the direction given can return null
    @Test 
    void testGetNextRoomInvalid() throws Exception {
        EnumMap<Room, EnumMap<Direction, Room>> map = App.initialiseMap();

        assertEquals(null, App.getNextRoom(Direction.EAST, Room.CAFE, map));
        assertEquals(null, App.getNextRoom(Direction.SOUTH, Room.DINOSAURGALLERY, map));
        assertEquals(null, App.getNextRoom(Direction.NORTH, Room.GIFTSHOP, map));
        assertEquals(null, App.getNextRoom(Direction.WEST, Room.STAFFROOM, map));
        assertEquals(null, App.getNextRoom(Direction.EAST, Room.UPPERLANDING, map));
        assertEquals(null, App.getNextRoom(Direction.NORTH, Room.GUARDROOM, map));
    }

    // testing it corret room is returned when a move is valid
    @Test 
    void testMoveValid() throws Exception {
        EnumMap<Room, EnumMap<Direction, Room>> map = App.initialiseMap();

        assertEquals(Room.MAINHALL, App.move(Direction.NORTH, Room.CAFE, map));
        assertEquals(Room.MAINHALL, App.move(Direction.EAST, Room.DINOSAURGALLERY, map));
        assertEquals(Room.CAFE, App.move(Direction.SOUTH, Room.MAINHALL, map));
        assertEquals(Room.MAINHALL, App.move(Direction.WEST, Room.GIFTSHOP, map));
        assertEquals(Room.UPPERLANDING, App.move(Direction.EAST, Room.STAFFROOM, map));
        assertEquals(Room.GUARDROOM, App.move(Direction.NORTH, Room.UPPERLANDING, map));
        assertEquals(Room.UPPERLANDING, App.move(Direction.SOUTH, Room.GUARDROOM, map));
    }

    // Testing invalid moves, the player should stay where they are
    @Test
    void testMoveInvalid() throws Exception {
        EnumMap<Room, EnumMap<Direction, Room>> map = App.initialiseMap();

        assertEquals(Room.CAFE, App.move(Direction.SOUTH, Room.CAFE, map));
        assertEquals(Room.DINOSAURGALLERY, App.move(Direction.WEST, Room.DINOSAURGALLERY, map));
        assertEquals(Room.GIFTSHOP, App.move(Direction.EAST, Room.GIFTSHOP, map));
        assertEquals(Room.STAFFROOM, App.move(Direction.NORTH, Room.STAFFROOM, map));
        assertEquals(Room.UPPERLANDING, App.move(Direction.EAST, Room.UPPERLANDING, map));
        assertEquals(Room.GUARDROOM, App.move(Direction.NORTH, Room.GUARDROOM, map));  
    }
}
>>>>>>> main
