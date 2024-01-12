package nl.sourceassist.datastorageutility;

import nl.sourceassist.datastorageutility.datastructure.RootNode;
import nl.sourceassist.datastorageutility.files.File;
import nl.sourceassist.datastorageutility.files.FileFactory;

public class DataStorageUtility {
    public static void main(String[] args) {
        File file = FileFactory.OpenFileFactory("INSERT-PATH");
        RootNode graph = file.readAllData();
        System.out.println("Hello");
    }
}
