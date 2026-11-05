import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Scanner;

public class App {

    public static Scanner stdin = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        EnumMap<Room, Item> roomItems = new EnumMap<>(Room.class);
        EnumMap<Room, String> roomMessages = new EnumMap<>(Room.class);
        EnumMap<Item, String> itemMessages = new EnumMap<>(Item.class);
        ArrayList<Item> inventory = new ArrayList<>();
        Room playerLocation = Room.CAFE;

        // the world map
        final EnumMap<Room, EnumMap<Direction, Room>> MAP = initialiseMap();

        // room messages
        EnumMap<Room, String> messages = initialise_room_messages();

        // Example use
        // roomItems.put(Room.GIFTSHOP, Item.BATTERIES);
        // roomItems.get(Room.GIFTSHOP); // Returns Item.BATTERIES

        // Game loop: to be filled in with functions as they are completed
        // TODO: Starting dialog
        while (!isGameComplete()) {
            // print current location
            System.out.println(messages.get(playerLocation));

            ParsedCommand command = readAndParseCommand();

            switch (command.command()) {
                case Command.LOOK -> System.out.println(lookAroundRoom(playerLocation));
                //case Command.EXAMINE -> examine();
                //case Command.INVENTORY -> inventory();
                //case Command.PICKUP -> pickup();
                //case Command.USE -> use();
                case Command.NORTH -> playerLocation = move(Direction.NORTH, playerLocation, MAP);
                case Command.EAST -> playerLocation = move(Direction.EAST, playerLocation, MAP);
                case Command.SOUTH -> playerLocation = move(Direction.SOUTH, playerLocation, MAP);
                case Command.WEST -> playerLocation = move(Direction.WEST, playerLocation, MAP);
            };

        }   

        //PrintRoomMessage(messages, playerLocation);
    }

    // TODO
    static boolean isGameComplete() {
        return false;
    }

    // asks user for input and converts to command enum
    // throws exception for invalid input
    public static ParsedCommand readAndParseCommand() throws Exception {
        // take input from user
        System.out.printf("Enter command: ");
        String input = stdin.nextLine();

        String[] words = input.split(" ");

        // convert input to command enum
        Command command = switch (words[0].toLowerCase()) {
            case "look" -> Command.LOOK;
            case "examine" -> Command.EXAMINE;
            case "inventory" -> Command.INVENTORY;
            case "pickup" -> Command.PICKUP;
            case "use" -> Command.USE;
            case "north" -> Command.NORTH;
            case "east" -> Command.EAST;
            case "south" -> Command.SOUTH;
            case "west" -> Command.WEST;
            default -> throw new Exception("Invalid Command");
        };

        switch (command) {
            case Command.LOOK, Command.INVENTORY, Command.NORTH, Command.EAST, Command.SOUTH, Command.WEST -> {
                return new ParsedCommand(command, null);
            }
            default -> {
            }
        }

        if (words.length < 2) {
            throw new Exception("Invalid Command");
        }

        Item item = switch (words[1].toLowerCase()) {
            case "batteries" -> Item.BATTERIES;
            case "keycard" -> Item.KEYCARD;
            case "coin" -> Item.COIN;
            case "guidebook" -> Item.GUIDEBOOK;
            default -> null;
        };

        return new ParsedCommand(command, item);
    }

    static void inventory(ArrayList<Item> inventory) {
        if (inventory.size() == 0) {
            System.out.println("You have nothing in your inventory.");
            return;
        }

        String itemPlural = inventory.size() == 1 ? "item" : "items";
        System.out.println("You have " + inventory.size() + " " + itemPlural + " in your inventory:");
        for (Item item : inventory) {
            switch (item) {
                case Item.BATTERIES -> System.out.println("- Batteries");
                case Item.KEYCARD -> System.out.println("- Keycard");
                case Item.COIN -> System.out.println("- Coin");
                case Item.GUIDEBOOK -> System.out.println("- Guidebook");
            }
        }
    }

    public static String ExamineItemOfChoice(Scanner scanner, ArrayList<Item> inventory,
            EnumMap<Item, String> itemMessages) {

        itemMessages.put(Item.BATTERIES, "BATTERIES. These are needed for the radio once you reach the guard room.");
        itemMessages.put(Item.KEYCARD, "KEYCARD. You'll need this to get access to the guard room.");

        if (inventory.isEmpty()) {
            return "Your pockets are empty.";
        }
        //String currentItems = CheckWhatInInventory(inventory);
        //System.out.println(currentItems);

        System.out.println("Which item do you want to examine? ");
        String choice = scanner.nextLine().trim().toUpperCase();

        for (Item currentItem : inventory) {
            if (currentItem.name().equals(choice)) {
                return itemMessages.get(currentItem);
            }
        }
        return "You don't have that.";
    }

    static String lookAroundRoom(Room playerLocation) {
        // as a player, I would like to type "look" to 'scan' a room for useful
        // items so that I know if an item is present within the room I am in.
        return switch (playerLocation) {
            case Room.CAFE -> "you see a coin. it might be useful";
            case Room.DINOSAURGALLERY -> "you see a guidebook. it might be useful";
            case Room.GIFTSHOP -> "you some batteries. they might be useful";
            case Room.STAFFROOM -> "you see a keycard. it might be useful";
            default -> "you don't see any useful items";
        };
    }
    
    // Making the array of room messages, will make them more descriptive later -
    // mad :P
    public static EnumMap<Room, String> initialise_room_messages() throws Exception {
        EnumMap<Room, String> roomMessages = new EnumMap<>(Room.class);
        roomMessages.put(Room.CAFE, "in the cafe");
        roomMessages.put(Room.MAINHALL, "in the main hall");
        roomMessages.put(Room.GIFTSHOP, "in the gift shop");
        roomMessages.put(Room.DINOSAURGALLERY, "in the dinosaur gallery");
        roomMessages.put(Room.UPPERLANDING, "in the upper landing");
        roomMessages.put(Room.STAFFROOM, "in the staff room");
        roomMessages.put(Room.GUARDROOM, "in the guard room");

        return roomMessages;
    }

    public static void PrintRoomMessage(EnumMap<Room, String> messages, Room room) throws Exception {
        if(messages.get(room)== null){
            throw new Exception("no message available");
        }
        
        System.out.printf("%s\n", messages.get(room));
    }

    public static EnumMap<Room, Item> InitialiseItemEnumMap() throws Exception
    {
        EnumMap<Room, Item> roomItems = new EnumMap<>(Room.class);
        roomItems.put(Room.CAFE, Item.COIN);
        roomItems.put(Room.GIFTSHOP, Item.BATTERIES);
        roomItems.put(Room.DINOSAURGALLERY, Item.GUIDEBOOK);
        roomItems.put(Room.STAFFROOM, Item.KEYCARD);

        return roomItems;
    }

    public static void PutItemInInventoryAndRemoveFromRoom(EnumMap<Room, Item> roomItems, Room playerLocation, ArrayList<Item> inventory ){
        inventory.add(roomItems.get(playerLocation));

        System.out.println(roomItems.get(playerLocation) + " was added to your inventory");
        roomItems.remove(playerLocation);
        
        

        
    }

    // Initialises the world map
    // Used to identify which directions are available from each room
    // and the room they lead to
    public static EnumMap<Room, EnumMap<Direction, Room>> initialiseMap() {
        // CAFE
        EnumMap<Direction, Room> roomsAvailableFromCafe = new EnumMap<>(Direction.class);
        roomsAvailableFromCafe.put(Direction.NORTH, Room.MAINHALL);

        // MAIN HALL
        EnumMap<Direction, Room> roomsAvailableFromMainHall = new EnumMap<>(Direction.class);
        roomsAvailableFromMainHall.put(Direction.NORTH, Room.UPPERLANDING);
        roomsAvailableFromMainHall.put(Direction.EAST, Room.GIFTSHOP);
        roomsAvailableFromMainHall.put(Direction.SOUTH, Room.CAFE);
        roomsAvailableFromMainHall.put(Direction.WEST, Room.DINOSAURGALLERY);

        // DINO GALLERY
        EnumMap<Direction, Room> roomsAvailableFromDinosaurGallery = new EnumMap<>(Direction.class);
        roomsAvailableFromDinosaurGallery.put(Direction.EAST, Room.MAINHALL);

        // GIFT SHOP
        EnumMap<Direction, Room> roomsAvailableFromGiftShop = new EnumMap<>(Direction.class);
        roomsAvailableFromGiftShop.put(Direction.WEST, Room.MAINHALL);

        // STAFF ROOM
        EnumMap<Direction, Room> roomsAvailableFromStaffRoom = new EnumMap<>(Direction.class);
        roomsAvailableFromStaffRoom.put(Direction.EAST, Room.UPPERLANDING);

        // UPPER LANDING
        EnumMap<Direction, Room> roomsAvailableFromUpperLanding = new EnumMap<>(Direction.class);
        roomsAvailableFromUpperLanding.put(Direction.NORTH, Room.GUARDROOM);
        roomsAvailableFromUpperLanding.put(Direction.SOUTH, Room.MAINHALL);
        roomsAvailableFromUpperLanding.put(Direction.WEST, Room.STAFFROOM);

        //GUARD ROOM
        EnumMap<Direction, Room> roomsAvailableFromGuardRoom = new EnumMap<>(Direction.class);
        roomsAvailableFromGuardRoom.put(Direction.SOUTH, Room.UPPERLANDING);

        // Fill the map with the specific room enum maps
        EnumMap<Room, EnumMap<Direction, Room>> roomsAvailableFromRooms = new EnumMap<>(new EnumMap<>(Room.class));
        roomsAvailableFromRooms.put(Room.CAFE, roomsAvailableFromCafe);
        roomsAvailableFromRooms.put(Room.MAINHALL, roomsAvailableFromMainHall);
        roomsAvailableFromRooms.put(Room.DINOSAURGALLERY, roomsAvailableFromDinosaurGallery);
        roomsAvailableFromRooms.put(Room.GIFTSHOP, roomsAvailableFromGiftShop);
        roomsAvailableFromRooms.put(Room.STAFFROOM, roomsAvailableFromStaffRoom);
        roomsAvailableFromRooms.put(Room.UPPERLANDING, roomsAvailableFromUpperLanding);
        roomsAvailableFromRooms.put(Room.GUARDROOM, roomsAvailableFromGuardRoom);

        return roomsAvailableFromRooms;
    }

    // updates the player to the new room
    // or tells the user they cant go that way
    public static Room move(
        Direction directionToMove, 
        Room playerCurrentRoom,
        EnumMap<Room, EnumMap<Direction, Room>> map
    ) {
        Room nextRoom = getNextRoom(directionToMove, playerCurrentRoom, map);
        if (nextRoom != null) {
            return nextRoom;
        } else {
            System.out.println("There is no door in that direction!!");
            return playerCurrentRoom;
        }
    }

    // Returns the next room from a given room and a direction to go
    // returns null if there is no room to go to
    public static Room getNextRoom(
        Direction directionHeading, 
        Room room,
        EnumMap<Room, EnumMap<Direction, Room>> map
    ) {
        return map.get(room).get(directionHeading);
    }
}
