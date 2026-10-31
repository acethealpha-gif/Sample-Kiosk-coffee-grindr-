package receipt;
import java.util.Scanner;
public class Receipt{
    
    static String[] allNames = {
            "Iced Americano", "Red Velvet Mocha", "African Black Coffee", "Kopi Luwak",
            "Apple Cinnamon Latte", "Ube Coconut Macchiato", "Pure Matcha", "Matcha Latte",
            "Hootie Frutti Sliced Cake", "Saffron Milk Sliced Cake", "Matcha Bun", "Green Grapes Macaron (3pcs)",
            "Mystery Christmas Treat"};
    
    static int[][] allPrices = {
            {75, 85, 95}, {75, 85, 95}, {75, 85, 95}, {85, 95, 100},
            {60, 70, 85}, {60, 70, 85}, {60, 70, 85}, {60, 70, 85},
            {70, 70, 70}, {70, 70, 70}, {60, 60, 60}, {90, 90, 90},
            {150, 150, 150}
        };

    public static void main(String[] args) {
        String[] coffeeNames = {
            "Iced Americano",
            "Red Velvet Mocha",
            "African Black Coffee",
            "Kopi Luwak"
        };
        int[][] coffeePrices = {
            {75, 85, 95},
            {75, 85, 95},
            {75, 85, 95},
            {85, 95, 100}
        };

        System.out.println("===== COFFEE GRINDR =====");
        System.out.println("COFFEE");

        for (int i = 0; i < coffeeNames.length; i++) {
            System.out.println((i + 1) + ". " + coffeeNames[i]
                + " | Jutes: " + coffeePrices[i][0]
                + " | Saks: " + coffeePrices[i][1]
                + " | Daks: " + coffeePrices[i][2]);
        }
        
        String[] coldNames ={
            "Apple Cinnamon Latte", 
            "Ube Coconut Macchiato",
            "Pure Matcha",
            "Matccha latte"};
         int[][] coldPrices={
             {60,70,85},
             {60,70,85},
             {60,70,85},
             {60,70,85}
         };
         System.out.println("COLD DRINKS");
         for (int i = 0; i < coldNames.length; i++) {
            System.out.println((i + 1) + ". " + coldNames[i]
                + " | Jutes: " + coldPrices[i][0]
                + " | Saks: " + coldPrices[i][1]
                + " | Daks: " + coldPrices[i][2]);
         }
         
         String[] pastryNames ={
             "Hootie Frutti Sliced Cakes",
             "Saffron Milk Sliced Cakes",
             "Matcha Bun",
             "Green Grapes Macaron (3pcs)"
         };
         int[] pastryPrices = {70, 70, 60, 90};
         
         System.out.println("PASTRIES");
         
         for (int i = 0; i < pastryNames.length; i++) {
            System.out.println((i + 1) + ". " + pastryNames[i]
                + " | Price: " + pastryPrices[i]);
         }
         
         System.out.println();
         System.out.println("HOLIDAY");
         System.out.println(" 1. Mystery Christmas Treat | Price : 150");
         
         
       //originallyy, dito nakalagay yung all names at all price na yan haa

        

        System.out.println();
        System.out.println("ORDER MENU");
        for (int i = 0; i < allNames.length; i++) {
            System.out.println((i + 1) + ". " + allNames[i]);
        }


       Scanner sc = new Scanner(System.in);
        int subtotal = 0;
        String again = "y";

        while (again.equals("y")) {
            System.out.print("Pick item (1-13): ");
            int item = sc.nextInt();

            int size = 1;
            if (item <= 8){
                System.out.print( "Size (1=Jutes, 2=Saks, 3=Daks):");
                size = sc.nextInt();
            }
                
           
            System.out.print("Quantity: ");
            int qty = sc.nextInt();

            int price = allPrices[item - 1][size - 1];
            int total = price * qty;
            subtotal = subtotal + total;

            System.out.println(allNames[item - 1] + " x" + qty + " = P" + total);
            System.out.println("Subtotal so far: P" + subtotal);

            System.out.print("Order more? (y/n): ");
            again = sc.next();
            System.out.println();
        }

        System.out.println("FINAL SUBTOTAL: P" + subtotal);
        
        System.out.print("Cash: P");
        int cash = sc.nextInt();
        int change = cash - subtotal;
        System.out.println("Change: P"+ change);
        
    }
}

