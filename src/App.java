import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Scanner;

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
            System.out.println("Your pockets are empty.");
            return "Your pockets are empty.";
        }
        String currentItems = AddToInventory(inventory);
        System.out.println(currentItems);

        System.out.println("Which item do you want to examine? ");
        String choice = scanner.nextLine().trim().toUpperCase();

        for (Item currentItem : inventory) {
            if (currentItem.name().equals(choice)) {
                return itemMessages.get(currentItem);
            }
        }
        return "You don't have that item.";
    }

}
