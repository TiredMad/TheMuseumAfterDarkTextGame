import java.util.ArrayList;
import java.util.EnumMap;

public class App {
    public static void main(String[] args) throws Exception {
        EnumMap<Room, Item> roomItems = new EnumMap<>(Room.class);
        EnumMap<Room, String> roomMessages = new EnumMap<>(Room.class);
        EnumMap<Item, String> itemMessages = new EnumMap<>(Item.class);
        ArrayList<Item> inventory = new ArrayList<>();
        Room playerLocation = Room.CAFE;

        // Example use
        // roomItems.put(Room.GIFTSHOP, Item.BATTERIES);
        // roomItems.get(Room.GIFTSHOP); // Returns Item.BATTERIES

        // Game loop: to be filled in with functions as they are completed
        // TODO: Starting dialog
        // while (!isGameComplete()) {
            // Command command = readAndParseCommand();

            // switch (command) {
            //     case Command.LOOK -> look();
            //     case Command.EXAMINE -> examine();
            //     case Command.INVENTORY -> inventory();
            //     case Command.PICKUP -> pickup();
            //     case Command.USE -> use();
            //     case Command.NORTH, Command.EAST, Command.SOUTH, Command.WEST -> move();
            // }
        // }
    }

    public static String ExamineItemOfChoice(Scanner scanner, ArrayList<Item> inventory,
            EnumMap<Item, String> itemMessages) {

        itemMessages.put(Item.BATTERIES, "BATTERIES. These are needed for the radio once you reach the guard room.");
        itemMessages.put(Item.KEYCARD, "KEYCARD. You'll need this to get access to the guard room.");

        if (inventory.isEmpty()) {
            return "Your pockets are empty.";
        }
        String currentItems = CheckWhatInInventory(inventory);
        System.out.println(currentItems);

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
}
