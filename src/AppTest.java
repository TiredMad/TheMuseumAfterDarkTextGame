import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Scanner;

public class AppTest {

@Test
void IfInventoryIsEmptyMessageShown() {
        Scanner scanner = new Scanner("");
        ArrayList<Item> inventory = new ArrayList<>();
        EnumMap<Item, String> messages = new EnumMap<>(Item.class);

        String result = App.ExamineItemOfChoice(scanner, inventory, messages);

        assertEquals("Your pockets are empty.", result);


}

@Test 
void ExamineItemsInInventory(){
        Scanner scanner = new Scanner("BATTERIES");
        ArrayList<Item> inventory = new ArrayList<>();
        inventory.add(Item.BATTERIES);
        EnumMap<Item, String> messages = new EnumMap<>(Item.class);

        String result = App.ExamineItemOfChoice(scanner, inventory, messages);

        assertEquals("BATTERIES. These are needed for the radio once you reach the guard room.", result);

}

@Test
void ExamineItemsYouDontHave(){
       Scanner scanner = new Scanner("BATTERIES");
       ArrayList<Item> inventory = new ArrayList<>();
       inventory.add(Item.KEYCARD);
       EnumMap<Item, String> messages = new EnumMap<>(Item.class);

       String result = App.ExamineItemOfChoice(scanner, inventory, messages);

       assertEquals("You don't have that item.", result);


}

}