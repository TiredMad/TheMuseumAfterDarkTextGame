import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Scanner;
import java.util.Map;

public class App {

    public static Scanner stdin = new Scanner(System.in);

    public static boolean isGameComplete = false;

    public static void main(String[] args) throws Exception {
        EnumMap<Room, Item> roomItems = createRoomItems();
        EnumMap<Item, String> itemMessages = new EnumMap<>(Item.class);
        ArrayList<Item> inventory = new ArrayList<>();

        // current player location
        Room playerLocation = Room.CAFE;

        // previous player location
        Room prevPlayerLocation = null;

        // the world map
        final EnumMap<Room, EnumMap<Direction, Room>> MAP = initialiseMap();

        // room messages
        EnumMap<Room, String> roomMessages = initialise_room_messages();

        // Example use
        // roomItems.put(Room.GIFTSHOP, Item.BATTERIES);
        // roomItems.get(Room.GIFTSHOP); // Returns Item.BATTERIES

        // Game loop: to be filled in with functions as they are completed
        while (!isGameComplete) {
            // if the player has changed rooms
            // wipe console and print new room message
            if (prevPlayerLocation != playerLocation) {
                printFormattedRoomUI(playerLocation, roomMessages);
                prevPlayerLocation = playerLocation;
            }

            ParsedCommand command;
            try {
                command = readAndParseCommand();
            } catch (ParserException e) {
                System.out.printf("\n%s\n\n", e.getMessage());
                continue;
            }

            // FORMATTING DO NOT REMOVE!!
            System.out.printf("\n");

            switch (command.command()) {
                case Command.LOOK -> System.out.println(lookAroundRoom(playerLocation, roomItems));

                case Command.INVENTORY -> inventory(inventory);
                case Command.PICKUP -> System.out.println(PickUp(playerLocation, roomItems, inventory));
                case Command.EXAMINE ->
                    System.out.println(ExamineItemOfChoice(command.item(), inventory, itemMessages));
                case Command.USE -> System.out.println(useItem(command.item(), inventory, playerLocation));
                case Command.NORTH ->
                    playerLocation = move(Direction.NORTH, playerLocation, MAP, inventory, itemMessages);
                case Command.EAST ->
                    playerLocation = move(Direction.EAST, playerLocation, MAP, inventory, itemMessages);
                case Command.SOUTH ->
                    playerLocation = move(Direction.SOUTH, playerLocation, MAP, inventory, itemMessages);
                case Command.WEST ->
                    playerLocation = move(Direction.WEST, playerLocation, MAP, inventory, itemMessages);
                case Command.HELP -> help();
            }
            ;

            // FORMATTING DO NOT REMOVE!!
            System.out.printf("\n");
        }

        System.out.println(Files.readString(Path.of("src/RoomDescriptions/EndingMessage.txt")));
    }

    static void printFormattedRoomUI(Room room, EnumMap<Room, String> roomMessages) {
        // clear console
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // print room location and description
        System.out.printf("\n\n\n\n");
        printFormattedRoomTitle(room.toString());
        System.out.printf("\n");
        System.out.println(roomMessages.get(room));
        System.out.printf("\n");

        // print help hint
        System.out.println("- At any time, type \"help\" for options");
        System.out.printf("\n");
    }

    static void printFormattedRoomTitle(String roomTitle) {
        String topAndBottom = "";
        for (int i = 0; i < roomTitle.length() + 6; i++) {
            topAndBottom += '-';
        }
        System.out.printf("%s\n|  %s  |\n%s\n", topAndBottom, roomTitle, topAndBottom);
    }

    static void help() {
        System.out.printf("""
                - help: shows commands in game
                - look: find any useful items in the room
                - inventory: reminder of what you're holding
                - pickup: pickup a useful item in the room
                - examine <item>: describe an item you're holding
                - use <item>: do something with an item
                - north, east, south, west: move between rooms
                """);
    }

    // asks user for input and converts to command enum
    // throws exception for invalid input
    public static ParsedCommand readAndParseCommand() throws ParserException {
        final Map<String, String> ALIASES = Map.of(
                "h", "help",
                "l", "look",
                "i", "inventory",
                "p", "pickup",
                "x", "examine",
                "u", "use",
                "n", "north",
                "e", "east",
                "s", "south",
                "w", "west");

        // take input from user
        System.out.printf("Enter command: ");
        String input = stdin.nextLine();

        String[] words = input.trim().split("\\s+");

        Command command;
        try {
            String word = ALIASES.getOrDefault(words[0], words[0]);
            command = Command.valueOf(word.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ParserException("Invalid command");
        }

        if (command != Command.USE && command != Command.EXAMINE) {
            return new ParsedCommand(command, null);
        }

        if (words.length < 2) {
            throw new ParserException("This command requires an item");
        }

        try {
            Item item = Item.valueOf(words[1].toUpperCase());
            return new ParsedCommand(command, item);
        } catch (IllegalArgumentException e) {
            throw new ParserException("Unknown item");
        }
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

    public static String ExamineItemOfChoice(Item item, ArrayList<Item> inventory,
            EnumMap<Item, String> itemMessages) {

        itemMessages.put(Item.BATTERIES, "BATTERIES. These are needed for the radio once you reach the guard room.");
        itemMessages.put(Item.KEYCARD, "KEYCARD. You'll need this to get access to the guard room.");
        itemMessages.put(Item.COIN, "COIN. A pound coin");
        itemMessages.put(Item.GUIDEBOOK,
                "GUIDEBOOK. The Notlaw Museum Guidebook, 12th Edition. Its copyright date appears to be in the future.");
        if (inventory.isEmpty()) {
            return "Your pockets are empty.";
        }

        if (inventory.contains(item)) {
            return itemMessages.get(item);
        }

        return "You don't have that item.";
    }

    static String lookAroundRoom(Room playerLocation, EnumMap<Room, Item> roomItems) {
        Item item = roomItems.get(playerLocation);

        return switch (item) {
            case Item.COIN -> "you see a coin. it might be useful";
            case Item.GUIDEBOOK -> "you see a guidebook. it might be useful";
            case Item.BATTERIES -> "you some batteries. they might be useful";
            case Item.KEYCARD -> "you see a keycard. it might be useful";
            case null -> "you don't see any useful items";
        };
    }

    static EnumMap<Room, Item> createRoomItems() {
        EnumMap<Room, Item> roomItems = new EnumMap<>(Room.class);
        roomItems.put(Room.CAFE, Item.COIN);
        roomItems.put(Room.GIFTSHOP, Item.BATTERIES);
        roomItems.put(Room.DINOSAURGALLERY, Item.GUIDEBOOK);
        roomItems.put(Room.STAFFROOM, Item.KEYCARD);

        return roomItems;
    }

    // Making the array of room messages, will make them more descriptive later -
    // mad :P
    public static EnumMap<Room, String> initialise_room_messages() throws Exception {
        EnumMap<Room, String> roomMessages = new EnumMap<>(Room.class);
        roomMessages.put(Room.CAFE, Files.readString(Path.of("src/RoomDescriptions/CafeDescription.txt")));
        roomMessages.put(Room.MAINHALL, Files.readString(Path.of("src/RoomDescriptions/MainHallDescription.txt")));
        roomMessages.put(Room.GIFTSHOP, Files.readString(Path.of("src/RoomDescriptions/GiftShopDescription.txt")));
        roomMessages.put(Room.DINOSAURGALLERY,
                Files.readString(Path.of("src/RoomDescriptions/DinosaurGalleryDescription.txt")));
        roomMessages.put(Room.UPPERLANDING,
                Files.readString(Path.of("src/RoomDescriptions/UpperLandingDescription.txt")));
        roomMessages.put(Room.STAFFROOM, Files.readString(Path.of("src/RoomDescriptions/StaffRoomDescription.txt")));
        roomMessages.put(Room.GUARDROOM, Files.readString(Path.of("src/RoomDescriptions/GuardRoomDescription.txt")));

        return roomMessages;
    }

    public static EnumMap<Room, Item> InitialiseItemEnumMap() throws Exception {
        EnumMap<Room, Item> roomItems = new EnumMap<>(Room.class);
        roomItems.put(Room.CAFE, Item.COIN);
        roomItems.put(Room.GIFTSHOP, Item.BATTERIES);
        roomItems.put(Room.DINOSAURGALLERY, Item.GUIDEBOOK);
        roomItems.put(Room.STAFFROOM, Item.KEYCARD);

        return roomItems;
    }

    // returns the item added to inventory
    public static Item PutItemInInventoryAndRemoveFromRoom(EnumMap<Room, Item> roomItems, Room playerLocation,
            ArrayList<Item> inventory) {
        Item itemToAdd = roomItems.get(playerLocation);
        inventory.add(itemToAdd);
        roomItems.remove(playerLocation);
        return itemToAdd;
    }

    // throwing error for if no item is in a room
    public static String PickUp(Room playerLocation, EnumMap<Room, Item> roomItems, ArrayList<Item> inventory) {
        if (roomItems.containsKey(playerLocation)) {
            Item itemAdded = PutItemInInventoryAndRemoveFromRoom(roomItems, playerLocation, inventory);
            return itemAdded + " was added to your inventory!";
        } else {
            return "You cannot see any useful items in this room.";
        }
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

        // GUARD ROOM
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
            EnumMap<Room, EnumMap<Direction, Room>> map,
            ArrayList<Item> inventory,
            EnumMap<Item, String> itemMessages) {
        Room nextRoom = getNextRoom(directionToMove, playerCurrentRoom, map);

        // Only allow access to the guard room if we have the keycard.
        if (nextRoom == Room.GUARDROOM && !OpenLockedDoorWithKey(inventory, itemMessages, nextRoom)) {
            return playerCurrentRoom;
        }

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
            EnumMap<Room, EnumMap<Direction, Room>> map) {
        return map.get(room).get(directionHeading);
    }

    public static String useItem(Item item, ArrayList<Item> userInventory, Room playerLocation) {

        // item function to be implemented in a future version :)
        if (item == Item.COIN && userInventory.contains(Item.COIN) ||
                item == Item.GUIDEBOOK && userInventory.contains(Item.GUIDEBOOK)) {
            return "This is DLC content!";
        }

        // we currently only track two items
        // the keycard will automatically open a door so we dont need to handle this
        if (item == Item.KEYCARD && userInventory.contains(Item.KEYCARD)) {
            return "Looks like the key card has opened the guard room";
            // the batteries must be used in the guardroom to complete the game.
        }

        if (item == Item.BATTERIES && userInventory.contains(Item.BATTERIES)) {
            // check player is in the
            if (playerLocation == Room.GUARDROOM) {
                // this wins the game
                isGameComplete = true;
                return "You put the batteries in the radio.";
            } else {
                return "The batteries can be used in the radio! Head to the guard room!";
            }
        }

        return "You do not have that item!";
    }

    // checks whether player has keycard to guarddoor
    public static boolean OpenLockedDoorWithKey(ArrayList<Item> inventory,
            EnumMap<Item, String> itemMessages, Room playerLocation) {

        if (playerLocation == Room.GUARDROOM && inventory.contains(Item.BATTERIES)
                && (inventory.contains(Item.KEYCARD))) {
            return true;
        }

        else if (playerLocation == Room.GUARDROOM && !inventory.contains(Item.KEYCARD)) {
            System.out.print("Access denied you need a KEYCARD!");
            return false;
        }

        else if (playerLocation == Room.GUARDROOM && inventory.contains(Item.KEYCARD)
                && !inventory.contains(Item.BATTERIES)) {
            System.out.print("Access granted \n Hint (Source of power needed for radio)");
        }
        return true;

    }

}
