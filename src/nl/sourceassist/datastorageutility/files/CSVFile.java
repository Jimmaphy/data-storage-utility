package nl.sourceassist.datastorageutility.files;

import nl.sourceassist.datastorageutility.datastructure.IdentifiableNode;
import nl.sourceassist.datastorageutility.datastructure.RootNode;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.StructureViolationException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CSVFile implements File {

    private final char delimiter;
    private final boolean hasHeadings;
    private Paths filePath;

    public CSVFile(Paths filePath, boolean hasHeadings, char delimiter) {
        this.filePath = filePath;
        this.hasHeadings = hasHeadings;
        this.delimiter = delimiter;
    }

        /**
     * Reads all data from the CSV file and returns it as a RootNode.
     * Each line in the file is represented as an IdentifiableNode, with the first column as the identifier and the remaining columns as children.
     * If the file has headings, the first line is skipped.
     *
     * @return a RootNode representing all data in the file
     * @throws IOException if an I/O error occurs
     */
    @Override
    public RootNode readAllData() {
        RootNode dataStructure = new RootNode(2);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            // Skip the first line if it contains headings
            if (hasHeadings) {
                reader.readLine();
            }

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(String.valueOf(delimiter));

                IdentifiableNode node = new IdentifiableNode(values[0]); // Assuming the first column is an identifier

                for (int i = 1; i < values.length; i++) {
                    node.addChild(values[i]);
                }
                dataStructure.addChild(node);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle or log the exception as needed
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
}