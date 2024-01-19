package nl.sourceassist.datastorageutility.files;

import java.nio.file.Files;
import java.nio.file.Paths;

public class FileFactory {
    public static File OpenFileFactory(String filePath) {
        int lastPeriodIndex = filePath.lastIndexOf(".");

        if (lastPeriodIndex > -1 && Files.exists(Paths.get(filePath))) {
            String extension = filePath.substring(lastPeriodIndex + 1);
            return switch (extension) {
                case "csv" -> new CSVFile(filePath, true, ';');
                case "json" -> new JSONFile(filePath);
                default -> throw new IllegalArgumentException("Invalid file extension");
            };
        }

        throw new IllegalArgumentException("Invalid path or file name");
    }
}