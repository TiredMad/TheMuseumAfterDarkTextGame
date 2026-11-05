import java.util.EnumMap;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Scanner;

public class AppTest {
    @Test
    void initialiseRoomsHappyPath() throws Exception {
        int size = App.initialise_room_messages().size();
        assertEquals(7, size);
    }

    @Test
    void tryToPrintMapAfterClear() {
        EnumMap<Room, String> testMap = null;

        assertThrows(Exception.class, () -> {
            App.PrintRoomMessage(testMap, Room.CAFE);
        });
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

    // testing every room where there is a possibility the direction given can
    // return null
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

    // tests for initialising the object EnumMap and adding an item to your
    // inventory

    @Test
    void AddItemToInventoryHappyPath() throws Exception {
        EnumMap<Room, Item> roomItems = App.InitialiseItemEnumMap();
        ArrayList<Item> inventory = new ArrayList<>();
        assertEquals(false, inventory.contains(Item.BATTERIES));
        App.PutItemInInventoryAndRemoveFromRoom(roomItems, Room.GIFTSHOP, inventory);

        assertEquals(true, inventory.contains(Item.BATTERIES));
        assertEquals(false, roomItems.containsValue(Item.BATTERIES));

        assertEquals(false, inventory.contains(Item.COIN));
        App.PutItemInInventoryAndRemoveFromRoom(roomItems, Room.CAFE, inventory);

        assertEquals(true, inventory.contains(Item.COIN));
        assertEquals(false, roomItems.containsValue(Item.COIN));

        assertEquals(false, inventory.contains(Item.GUIDEBOOK));
        App.PutItemInInventoryAndRemoveFromRoom(roomItems, Room.DINOSAURGALLERY, inventory);

        assertEquals(true, inventory.contains(Item.GUIDEBOOK));
        assertEquals(false, roomItems.containsValue(Item.GUIDEBOOK));

        assertEquals(false, inventory.contains(Item.KEYCARD));
        App.PutItemInInventoryAndRemoveFromRoom(roomItems, Room.STAFFROOM, inventory);

        assertEquals(true, inventory.contains(Item.KEYCARD));
        assertEquals(false, roomItems.containsValue(Item.KEYCARD));

    }

    @Test
    void IfInventoryIsEmptyMessageShown() {
        Scanner scanner = new Scanner("");
        ArrayList<Item> inventory = new ArrayList<>();
        EnumMap<Item, String> messages = new EnumMap<>(Item.class);

        String result = App.ExamineItemOfChoice(scanner, inventory, messages);

        assertEquals("Your pockets are empty.", result);

    }

    @Test
    void ExamineItemsInInventory() {
        Scanner scanner = new Scanner("BATTERIES");
        ArrayList<Item> inventory = new ArrayList<>();
        inventory.add(Item.BATTERIES);
        EnumMap<Item, String> messages = new EnumMap<>(Item.class);

        String result = App.ExamineItemOfChoice(scanner, inventory, messages);

        assertEquals("BATTERIES. These are needed for the radio once you reach the guard room.", result);

    }

    @Test
    void testUseItem() throws Exception {
        // test can use batteries in guard room
        ArrayList<Item> inventory = new ArrayList<>();
        inventory.add(Item.BATTERIES);
        assertEquals(
                "You put the batteries in the radio.",
                App.useItem(Item.BATTERIES, inventory, Room.GUARDROOM));

        // test cannot use batteries unless in guard room
        assertEquals(
                "The batteries can be used in the radio! Head to the guard room!",
                App.useItem(Item.BATTERIES, inventory, Room.MAINHALL));

        // get using key card
        inventory.add(Item.KEYCARD);
        assertEquals(
                "Looks like the key card has opened the guard room",
                App.useItem(Item.KEYCARD, inventory, Room.MAINHALL));

        // test using an item not in inventory
        inventory = new ArrayList<>();
        assertEquals(
                "You do not have that item!",
                App.useItem(Item.KEYCARD, inventory, Room.MAINHALL));
        assertEquals(
                "You do not have that item!",
                App.useItem(Item.BATTERIES, inventory, Room.MAINHALL));
    }

}
    void ExamineItemsYouDontHave() {
        Scanner scanner = new Scanner("BATTERIES");
        ArrayList<Item> inventory = new ArrayList<>();
        inventory.add(Item.KEYCARD);
        EnumMap<Item, String> messages = new EnumMap<>(Item.class);

        String result = App.ExamineItemOfChoice(scanner, inventory, messages);

        assertEquals("You don't have that.", result);
        
    }

    @Test
    void testOpenDoorWithBothItems() {
        EnumMap<Item, String> messages = new EnumMap<>(Item.class);
        ArrayList<Item> inventory = new ArrayList<>();
        inventory.add(Item.KEYCARD);
        inventory.add(Item.BATTERIES);

        assertTrue(App.OpenLockedDoorWithKey(inventory, messages, Room.GUARDROOM));
    }

    // door opens with just the keycard in the guard room
    @Test
    void testOpenDoorWithKeycardOnly() {
        EnumMap<Item, String> messages = new EnumMap<>(Item.class);
        ArrayList<Item> inventory = new ArrayList<>();
        inventory.add(Item.KEYCARD);

        assertTrue(App.OpenLockedDoorWithKey(inventory, messages, Room.GUARDROOM));
    }

    // door stays shut without the keycard
    @Test
    void testOpenDoorWithoutKeycard() {
        EnumMap<Item, String> messages = new EnumMap<>(Item.class);
        ArrayList<Item> inventory = new ArrayList<>();
        inventory.add(Item.BATTERIES);

        assertFalse(App.OpenLockedDoorWithKey(inventory, messages, Room.GUARDROOM));
    }

    
    }


