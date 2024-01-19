package nl.sourceassist.datastorageutility;

import nl.sourceassist.datastorageutility.commander.Commander;
import nl.sourceassist.datastorageutility.datastructure.RootNode;
import nl.sourceassist.datastorageutility.files.File;
import nl.sourceassist.datastorageutility.files.FileFactory;

import java.io.IOException;

public class DataStorageUtility {
    public static void main(String[] args) throws IOException {
        boolean isDone = false;
        Commander commander = new Commander();

        while (!isDone) {
            isDone = commander.execute();
        }
    }
}

//File file = FileFactory.OpenFileFactory("INSERT-PATH");
//RootNode graph = file.readAllData();
//System.out.println("Hello");
