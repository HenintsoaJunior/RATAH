package files;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import dessin.Plan;

public class File{
    public static void save(Plan plan) {
        try {
            FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Henintsoa\\Documents\\github\\RATAH\\CIVILISATIONGAME\\src\\fichier\\file.txt");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(plan);
            objectOut.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  static Plan load() {
        try {
            FileInputStream fileIn = new FileInputStream("C:\\Users\\Henintsoa\\Documents\\github\\RATAH\\CIVILISATIONGAME\\src\\fichier\\file.txt");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Plan p= (Plan)objectIn.readObject();
            objectIn.close();
            fileIn.close();
            return p;
        } catch (Exception e) {
            return null;
        }
    }
    public void clearFileContent() throws Exception {
        Path filePath = Paths.get("save.dat");
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.TRUNCATE_EXISTING)) {}
    }
}