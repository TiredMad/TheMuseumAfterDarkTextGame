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
}
