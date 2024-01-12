package nl.sourceassist.datastorageutility.files;

import nl.sourceassist.datastorageutility.datastructure.IdentifiableNode;
import nl.sourceassist.datastorageutility.datastructure.RootNode;

import java.nio.file.Paths;

/**
 * An interface representing a file.
 * This interface can be implemented to different formats
 */
public interface File {

    /**
     * Read all the data of the file and return it as composite data structure.
     *
     * @return the data of the file as a composite data structure.
     */
    RootNode readAllData();

    /**
     * Write all the data provided to the given file.
     *
     * @param data the data to be saved to the file.
     * @return true if the data was saved successfully, false otherwise.
     * @see RootNode the start of the data structure requested.
     */
    boolean writeAllData(RootNode data);

    /**
     * Save the file on a different location.
     *
     * @param path the new path of the file.
     * @return true if the file was saved successfully, false otherwise.
     * @see Paths Java's cross.
     */
    boolean saveFileAs(Paths path);
}

