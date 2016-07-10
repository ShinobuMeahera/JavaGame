/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaGame.edytorrr;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Shinobu Meahera
 */
public class NewMapController {
    
    private Main mainApp;
    private Stage newMapStage;
    
    @FXML
    private TextField widthTextField;
    
    @FXML
    private TextField heigthTextField;
    
    @FXML
    private TextField nameTextField;
    
    public String getName(){
        return nameTextField.getText();
    }
    public int getWidth(){
        return Integer.parseInt(widthTextField.getText());
    }
    public int getHeight(){
        try{
            return Integer.parseInt(heigthTextField.getText());
        }
        catch(NumberFormatException e){
            return 0;
        }
    }
    
    @FXML
    private void acceptButton(){
        if(getHeight() != 0 && getWidth() != 0 && getName() != null){
               // canvas.setHeight ();
                //canvas.setWidth ();
            System.out.println("x : " + getWidth() + "y : " + getWidth());
            System.out.println("Name : " + getName());
            mainApp.createNewMap(getWidth(), getHeight(), getName());
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

}
