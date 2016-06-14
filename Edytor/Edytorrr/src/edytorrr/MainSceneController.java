/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edytorrr;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class MainSceneController implements Initializable {
    
    private NewMapController newMapCon;
    private LoadMapController loadMapCon;
    @FXML
    private Label label;
    
    @FXML
    private Canvas canvas;
    
    @FXML
    private Edytorrr mainApp;
    
    @FXML
    public void newMapFunction(){
        Parent newMap;
        try{
            newMap = FXMLLoader.load(getClass().getResource("NewMap.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Creating New Map");
            stage.setScene(new Scene(newMap));
            stage.showAndWait();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    public void loadMapFunction(){
        Parent loadMap;
        try{
            loadMap = FXMLLoader.load(getClass().getResource("LoadMap.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Load Existing Map");
            stage.setScene(new Scene(loadMap));
            stage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void setMainApp(Edytorrr mainApp) {
        this.mainApp = mainApp;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
