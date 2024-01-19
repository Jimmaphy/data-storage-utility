package nl.sourceassist.datastorageutility.files;

import nl.sourceassist.datastorageutility.datastructure.CompositeNode;
import nl.sourceassist.datastorageutility.datastructure.LeafNode;
import nl.sourceassist.datastorageutility.datastructure.RootNode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.concurrent.StructureViolationException;
import java.util.stream.IntStream;

public class CSVFile implements File {

    private final char delimiter;
    private final boolean hasHeadings;
    private Path filePath;

    public CSVFile(String filePath, boolean hasHeadings, char delimiter) {
        this.filePath = Paths.get(filePath);
        this.hasHeadings = hasHeadings;
        this.delimiter = delimiter;
    }

    @Override
    public RootNode readAllData() {
        RootNode dataStructure = new RootNode(2);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            String[] headings = this.hasHeadings
                ? reader.readLine().split(String.valueOf(delimiter))
                : IntStream.rangeClosed(1, 10)
                    .mapToObj(String::valueOf)
                    .toArray(String[]::new);

            String line = reader.readLine();
            int rowNumber = 0;
            while (line != null) {
                String[] values = line.split(String.valueOf(delimiter));

                CompositeNode rowNode = new CompositeNode(String.valueOf(rowNumber));
                for (int i = 0; i < values.length; i++) {
                    rowNode.addChild(new LeafNode(headings[i], values[i]));
                }

                dataStructure.addChild(rowNode);
                line = reader.readLine();
                rowNumber++;
            }
        }

        catch (IOException e) {
            System.out.println(e.toString());
        }

        return dataStructure;
    }

    @Override
    public boolean writeAllData(RootNode data) {
        if (data.getDepth() != 2) {
            throw new StructureViolationException();
        }

        return false;
    }

    @Override
    public boolean saveFileAs(Paths newFilePath) {
        return false;
    }

    @Override
    public String getFileName() {
        return this.filePath.getFileName().toString();
    }
}