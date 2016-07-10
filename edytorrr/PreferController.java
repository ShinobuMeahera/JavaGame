package JavaGame.edytorrr;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.File;


public class PreferController {

    private Main mainApp;
    private String tileMapName;

    @FXML
    private Label tileMapLoaded;

    @FXML
    public void changeTileMap() {

        Parent tileMap;
        File tempMap;

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Change Tile Map");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg")
        );
        tempMap = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        if (tempMap == null) {
            return;
        }
        System.out.println("Wybrano plik do zalodowania");
        System.out.println(tempMap.getPath());
        changeTileMap(tempMap);

    }
    private void changeTileMap(File newTileMap){
        mainApp.changeTileMap(newTileMap);
        tileMapName = newTileMap.getName();
        tileMapLoaded.setText(tileMapName);
    }

    public void setApp(Main main) {
        mainApp = main;
    }

    public void run() {
        tileMapName = mainApp.tileMapName;
        tileMapLoaded.setText(tileMapName);
    }

}
