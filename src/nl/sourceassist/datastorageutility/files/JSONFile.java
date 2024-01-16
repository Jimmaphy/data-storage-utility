package nl.sourceassist.datastorageutility.files;

import nl.sourceassist.datastorageutility.datastructure.CompositeNode;
import nl.sourceassist.datastorageutility.datastructure.LeafNode;
import nl.sourceassist.datastorageutility.datastructure.RootNode;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.StructureViolationException;

// TODO Moet er voor zorgen dat de javax.json geimporteerd word daarna verder.

public class JSONFile implements File {

    private Path filePath;

    public JSONFile(String filePath) {
        this.filePath = Path.of(filePath);
    }

    @Override
    public RootNode readAllData() {
        RootNode dataStructure = new RootNode();

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            StringBuilder jsonContent = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            try (JsonReader jsonReader = Json.createReader(Files.newInputStream(filePath))) {
                JsonArray jsonArray = jsonReader.readArray();
                int rowNumber = 0;

                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject jsonObject = jsonArray.getJsonObject(i);

                    CompositeNode rowNode = new CompositeNode(String.valueOf(rowNumber)); // Assuming the first column is an identifier

                    for (String key : jsonObject.keySet()) {
                        rowNode.addChild(new LeafNode(key, jsonObject.getString(key)));
                    }

                    dataStructure.addChild(rowNode);
                    rowNumber++;
                }
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }

        return dataStructure;
    }

    @Override
    public boolean writeAllData(RootNode data) {
        if (data.getDepth() != 2) {
            throw new StructureViolationException();
        }

        // TODO
        // Implement the logic to write data to a JSON file using java.io
        // You need to convert the RootNode structure into a JSON format and write it to the file

        return false;
    }

    @Override
    public boolean saveFileAs(Paths path) {
      // TODO
      throw new UnsupportedOperationException("Unimplemented method 'saveFileAs'");
    }
}
