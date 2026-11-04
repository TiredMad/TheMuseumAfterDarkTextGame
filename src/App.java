import java.util.ArrayList;
import java.util.EnumMap;

public class App {
    public static void main(String[] args) throws Exception {
        EnumMap<Room, Item> roomItems = new EnumMap<>(Room.class);
        EnumMap<Room, String> roomMessages = new EnumMap<>(Room.class);
        EnumMap<Item, String> itemMessages = new EnumMap<>(Item.class);
        ArrayList<Item> inventory = new ArrayList<>();
        Room playerLocation = Room.CAFE;

        // the world map
        final EnumMap<Room, EnumMap<Direction, Room>> MAP = initialiseMap();


        // Example use
        // roomItems.put(Room.GIFTSHOP, Item.BATTERIES);
        // roomItems.get(Room.GIFTSHOP); // Returns Item.BATTERIES
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
}
