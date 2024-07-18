/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import utils.LinkedList;

/**
 *
 * @author quinton
 */
public class Common {

    public static void writeObjectsToFile(LinkedList<?> list, String filename) throws FileNotFoundException, IOException {
        File file = new File(filename);
        ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(file));
        ooStream.writeObject(list);
        ooStream.close();
    }

    public static void retrieveObjectsFromFile(LinkedList<?> list, String filename) throws FileNotFoundException, IOException, ClassNotFoundException {
        File file = new File("documents/" + filename);
        if (file.exists() && !file.isDirectory()) {
            ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file));
            list = (LinkedList<?>) (oiStream.readObject());
            oiStream.close();

        } else {
            file.createNewFile();
        }

    }
}
