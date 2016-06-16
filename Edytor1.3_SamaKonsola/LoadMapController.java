/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.net.URL;
import static java.time.Clock.system;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Shinobu Meahera
 */
public class LoadMapController {
    private Main mainApp;
    private Stage newMapStage;
    
    @FXML
    private TextField nameTextField;
    
    @FXML
    private void acceptButton(){
        if(nameTextField.getText() != null){

            mainApp.openMap(nameTextField.getText());
            newMapStage.close();
            }
    }
    
    @FXML
    private void cancelButton(){
        newMapStage.close();
    }
    
    public void setStage(Stage Stage) {
        this.newMapStage = Stage;
    }
    public void setApp(Main main) {
        this.mainApp = main;
    }
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
