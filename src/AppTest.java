import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.EnumMap;

public class AppTest {
    
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

}
