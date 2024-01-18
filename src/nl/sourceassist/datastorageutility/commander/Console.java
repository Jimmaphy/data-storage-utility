package nl.sourceassist.datastorageutility.commander;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console {
    public String readLine() {
        try{
            String line = (new BufferedReader(
                    new InputStreamReader(System.in))
            ).readLine();

            return line;
        }
        catch(IOException ex) {
            return "Gibberish input detected";
        }
    }

    public void writeInstruction(String instruction) {
        System.out.println(instruction);
        System.out.println();
    }

    public void writeCommand(String command) {
        System.out.println(command);
    }
}