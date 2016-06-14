/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edytorrr;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 *
 * @author Shinobu Meahera
 */
public class NewMapController {
    
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
    private void acceptButton(){
        if(getHeight() != 0 && getWidth() != 0 && getName() != null){
               // canvas.setHeight (getHeight());
                //canvas.setWidth (getWidth());
               
            }
    }
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
