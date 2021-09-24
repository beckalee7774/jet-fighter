package persistence;

import model.Jet;
import model.JetFighterGame;
import model.Gun;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import org.json.*;

// represents a json writer that write data to a Json file
public class JsonWriter {
    private PrintWriter writer;
    private String destination;
    private static final int TAB = 4;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }


    // MODIFIES: this
    // EFFECTS: writes JSON representation of JetFighterGame to file
    public void writeFile(JetFighterGame game) {
        JSONObject json = game.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

}
