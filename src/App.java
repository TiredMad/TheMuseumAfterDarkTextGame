import java.util.ArrayList;
import java.util.EnumMap;

public class App {
    public static void main(String[] args) throws Exception {


        EnumMap<Room, Item> roomItems = new EnumMap<>(Room.class);
        EnumMap<Room, String> roomMessages = new EnumMap<>(Room.class);
        EnumMap<Item, String> itemMessages = new EnumMap<>(Item.class);
        ArrayList<Item> inventory = new ArrayList<>();
        Room playerLocation = Room.MAINHALL;

        // Example use
        // roomItems.put(Room.GIFTSHOP, Item.BATTERIES);
        // roomItems.get(Room.GIFTSHOP); // Returns Item.BATTERIES
        EnumMap<Room, String> messages = initialise_room_messages();
        PrintRoomMessage(messages, playerLocation);

        
       
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
}
