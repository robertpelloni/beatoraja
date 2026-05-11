package bms.player.beatoraja.launcher;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Placeholder controller for the In-Game Theme/Skin WYSIWYG Editor.
 * Maps backend `SkinObject` properties (`dstX`, `dstY`, `scale`) 
 * directly to a frontend GUI for visual editing.
 * 
 * @author Jules
 */
public class ThemeEditorView implements Initializable {

    @FXML
    private ComboBox<String> skinSelector;
    @FXML
    private ListView<String> activeObjectsList;
    
    @FXML
    private TextField dstXField;
    @FXML
    private TextField dstYField;
    @FXML
    private TextField scaleXField;
    @FXML
    private TextField scaleYField;
    
    @FXML
    private VBox previewCanvas; // Future integration point for LibGDX Lwjgl3AWTCanvas

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialization logic for populating activeObjectsList 
        // with parsed JSON/Lua skin objects.
    }
    
    @FXML
    public void onSaveSkin() {
        // Serializes modified dstX, dstY back to the selected skin.json
    }
}
