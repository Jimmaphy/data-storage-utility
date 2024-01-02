package nl.sourceassist.datastorageutility.files;

import nl.sourceassist.datastorageutility.datastructure.IdentifiableNode;
import nl.sourceassist.datastorageutility.datastructure.RootNode;

import java.nio.file.Paths;
import java.util.concurrent.StructureViolationException;

public class CSVFile implements File {

    private final char delimiter;
    private final boolean hasHeadings;
    private Paths filePath;

    public CSVFile(Paths filePath, boolean hasHeadings, char delimiter) {
        this.filePath = filePath;
        this.hasHeadings = hasHeadings;
        this.delimiter = delimiter;
    }

    @Override
    public RootNode readAllData() {
        RootNode dataStructure = new RootNode(2);

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
