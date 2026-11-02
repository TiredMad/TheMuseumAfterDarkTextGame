public class App {
    public static void main(String[] args) throws Exception {
        String[] messages = initialise_room_messages();
        PrintRoomMessage(messages, 1);
    }
    //Making the array of room messages, will make them more descriptive later - mad :P 
    public static String[] initialise_room_messages(){
        String[] room_messages = new String[7];
        room_messages[0] = "in the cafe";
        room_messages[1] = "in the main hall";
        room_messages[2] = "in the gift shop (battery location)";
        room_messages[3] = "in the dino gallery(guidebook location";
        room_messages[4] = "in the upper landing";
        room_messages[5] = "in the staff room (keycard location)";
        room_messages[6] = "in the guard room (goal)";

       return  room_messages;
    }

    public static void PrintRoomMessage(String[] messages, int index){
        System.out.printf("%s\n", messages[index]);
    }
}

