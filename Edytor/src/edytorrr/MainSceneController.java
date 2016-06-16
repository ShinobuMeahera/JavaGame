/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edytorrr;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class MainSceneController implements Initializable {
    
    private NewMapController newMapCon;
    private LoadMapController loadMapCon;
    private Main mainApp;
    
    @FXML
    private Canvas canvas;
    
    @FXML
    public void newMapFunction(){
       mainApp.newMap();
    }
    @FXML
    public void loadMapFunction(){
       mainApp.loadMap();
    }
    
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    
    public void setCanvasParam(int x, int y){
        canvas.setWidth(x);
        canvas.setHeight(y);
        System.out.println(canvas.getHeight() + " " + canvas.getWidth()); 
        System.out.println(canvas.getLayoutX()+ " " + canvas.getLayoutY()); 
    }
    @FXML
    public void colCan(){
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        gc.setFill(Color.RED);      
        gc.fillRect(0,0,4000,4000);
        System.out.println("NullPointer ii"); 

    }
    @FXML
    public void print(int x,int y,double col){
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        gc.setFill(Color.color(1,0.1,0.1 + col));      
        gc.fillRect(x,y,30,30);
        System.out.println("NullPointer ii"); 

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
