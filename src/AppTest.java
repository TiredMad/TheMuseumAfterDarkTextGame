import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Scanner;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

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
        EnumMap<Room, Item> roomItems = App.createRoomItems();

        assertEquals("you some batteries. they might be useful", App.lookAroundRoom(Room.GIFTSHOP, roomItems));
        assertEquals("you see a keycard. it might be useful", App.lookAroundRoom(Room.STAFFROOM, roomItems));
        assertEquals("you don't see any useful items", App.lookAroundRoom(Room.MAINHALL, roomItems));
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
        ArrayList<Item> inventory = new ArrayList<>();
        EnumMap<Item, String> itemMessages = new EnumMap<>(Item.class);

        assertEquals(Room.MAINHALL, App.move(Direction.NORTH, Room.CAFE, map, inventory, itemMessages));
        assertEquals(Room.MAINHALL, App.move(Direction.EAST, Room.DINOSAURGALLERY, map, inventory, itemMessages));
        assertEquals(Room.CAFE, App.move(Direction.SOUTH, Room.MAINHALL, map, inventory, itemMessages));
        assertEquals(Room.MAINHALL, App.move(Direction.WEST, Room.GIFTSHOP, map, inventory, itemMessages));
        assertEquals(Room.UPPERLANDING, App.move(Direction.EAST, Room.STAFFROOM, map, inventory, itemMessages));
        inventory.add(Item.KEYCARD);
        assertEquals(Room.GUARDROOM, App.move(Direction.NORTH, Room.UPPERLANDING, map, inventory, itemMessages));
        assertEquals(Room.UPPERLANDING, App.move(Direction.SOUTH, Room.GUARDROOM, map, inventory, itemMessages));
    }

    // Testing invalid moves, the player should stay where they are
    @Test
    void testMoveInvalid() throws Exception {
        EnumMap<Room, EnumMap<Direction, Room>> map = App.initialiseMap();
        ArrayList<Item> inventory = new ArrayList<>();
        EnumMap<Item, String> itemMessages = new EnumMap<>(Item.class);

        assertEquals(Room.CAFE, App.move(Direction.SOUTH, Room.CAFE, map, inventory, itemMessages));
        assertEquals(Room.DINOSAURGALLERY, App.move(Direction.WEST, Room.DINOSAURGALLERY, map, inventory, itemMessages));
        assertEquals(Room.GIFTSHOP, App.move(Direction.EAST, Room.GIFTSHOP, map, inventory, itemMessages));
        assertEquals(Room.STAFFROOM, App.move(Direction.NORTH, Room.STAFFROOM, map, inventory, itemMessages));
        assertEquals(Room.UPPERLANDING, App.move(Direction.EAST, Room.UPPERLANDING, map, inventory, itemMessages));
        assertEquals(Room.GUARDROOM, App.move(Direction.NORTH, Room.GUARDROOM, map, inventory, itemMessages));
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
    void PickUpPositive() throws Exception {

        EnumMap<Room, Item> roomItems = App.InitialiseItemEnumMap();
        Room playerLocation = Room.CAFE;
        ArrayList<Item> inventory = new ArrayList<>();

        App.PickUp(playerLocation, roomItems, inventory);

        assertEquals(true, !roomItems.containsKey(Room.CAFE));

        roomItems = App.InitialiseItemEnumMap();
        playerLocation = Room.DINOSAURGALLERY;
        inventory = new ArrayList<>();

        App.PickUp(playerLocation, roomItems, inventory);

        assertEquals(true, !roomItems.containsKey(Room.DINOSAURGALLERY));

        roomItems = App.InitialiseItemEnumMap();
        playerLocation = Room.GIFTSHOP;
        inventory = new ArrayList<>();

        App.PickUp(playerLocation, roomItems, inventory);

        assertEquals(true, !roomItems.containsKey(Room.GIFTSHOP));

        roomItems = App.InitialiseItemEnumMap();
        playerLocation = Room.STAFFROOM;
        inventory = new ArrayList<>();

        App.PickUp(playerLocation, roomItems, inventory);

        assertEquals(true, !roomItems.containsKey(Room.STAFFROOM));

        roomItems = App.InitialiseItemEnumMap();
        playerLocation = Room.MAINHALL;
        inventory = new ArrayList<>();

        App.PickUp(playerLocation, roomItems, inventory);

        assertEquals(false, roomItems.containsKey(Room.MAINHALL));

       

    }

    @Test
    void PickUpNegative() throws Exception{
        EnumMap <Room, Item> roomItems = App.InitialiseItemEnumMap();
       
        ArrayList<Item> inventory = new ArrayList<>();

        assertEquals("You cannot see any useful items in this room.", App.PickUp(Room.MAINHALL, roomItems, inventory)); 
        assertEquals("You cannot see any useful items in this room.", App.PickUp(Room.UPPERLANDING, roomItems, inventory));
        assertEquals("You cannot see any useful items in this room.", App.PickUp(Room.GUARDROOM, roomItems, inventory));

        
    }

    

        // testing examine shows the right message for items the player has
    @Test
    void testExamineItemValid() throws Exception {
        EnumMap<Item, String> messages = new EnumMap<>(Item.class);
        ArrayList<Item> inventory = new ArrayList<>();
        inventory.add(Item.BATTERIES);
        inventory.add(Item.KEYCARD);

        assertEquals("BATTERIES. These are needed for the radio once you reach the guard room.", App.ExamineItemOfChoice(Item.BATTERIES, inventory, messages));
        assertEquals("KEYCARD. You'll need this to get access to the guard room.", App.ExamineItemOfChoice(Item.KEYCARD, inventory, messages));
    }

    // testing examine when the pockets are empty or the item isn't there
    @Test
    void testExamineItemInvalid() throws Exception {
        EnumMap<Item, String> messages = new EnumMap<>(Item.class);
        ArrayList<Item> inventory = new ArrayList<>();

        assertEquals("Your pockets are empty.", App.ExamineItemOfChoice(Item.BATTERIES, inventory, messages));

        // player has batteries but examines the keycard
        inventory.add(Item.BATTERIES);
        assertEquals("You don't have that item.", App.ExamineItemOfChoice(Item.KEYCARD, inventory, messages));
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



