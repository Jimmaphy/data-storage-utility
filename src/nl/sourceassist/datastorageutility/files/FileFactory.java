package nl.sourceassist.datastorageutility.files;

public class FileFactory {
    public static File OpenFileFactory(String filePath) {
        int lastPeriodIndex = filePath.lastIndexOf(".");

        if (lastPeriodIndex > -1) {
            String extension = filePath.substring(lastPeriodIndex + 1);
            return switch (extension) {
                case "csv" -> new CSVFile(filePath, true, ';');
                case "json" -> null;
                default -> throw new IllegalArgumentException("Invalid file extension");
            };
        }

        throw new IllegalArgumentException("Invalid file name");
    }
}