import java.util.ArrayList;
import java.util.EnumMap;

public class App {
    public static void main(String[] args) throws Exception {
        EnumMap<Room, Item> roomItems = new EnumMap<>(Room.class);
        EnumMap<Room, String> roomMessages = new EnumMap<>(Room.class);
        EnumMap<Item, String> itemMessages = new EnumMap<>(Item.class);
        ArrayList<Item> inventory = new ArrayList<>();
        Room playerLocation = Room.CAFE;

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

}
