package exampleWriteIO;

import java.io.FileWriter;
import java.io.IOException;

public class FileIO {
    public static void main(String[] args) {
        try {
            // Writing to a file
            FileWriter writer = new FileWriter("src/main/java/example09/example.txt");

            // Write a string to the file
            writer.write("Hello!!!");

            //close writer
            writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
}
