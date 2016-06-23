/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.input.ScrollEvent;

public class MainSceneController implements Initializable {
    
    
    
    private NewMapController newMapCon;
    private LoadMapController loadMapCon;
    private Main mainApp;
    private GraphicsContext gc;
    private GraphicsContext gc2;
    public Image src2 = new Image("https://github.com/ShinobuMeahera/JavaGame/blob/master/Gra/tileset3.png?raw=true");
    private int id;
    @FXML
    private Canvas canvas;
    
    @FXML
    private Canvas canvas2;
    
    @FXML
    private Button leftButton;
    
    @FXML
    private Label leftLabel;
    
    @FXML
    private ScrollPane scroll;

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
        canvas.setLayoutX(0);
        canvas.setLayoutY(0);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        gc.setFill(Color.BLACK);      
        gc.fillRect(0,0,4000,4000);
        System.out.println(canvas.getHeight() + " " + canvas.getWidth()); 
        System.out.println(canvas.getLayoutX()+ " " + canvas.getLayoutY()); 
    }
    @FXML
    public void colCan() {
    // gc.fillRect(0,0,4000,4000);
     //leftLabel.setText("Button Action\n");
    }
    @FXML
    public void print(int x,int y,Image img,int row, int col){
        gc.drawImage(img, col*30, row*30, 30, 30, x, y, 30, 30);
    }
    @FXML	
    private void refreshButton(){
        
    }
    @FXML
    private void leftCanvasMouse(MouseEvent event) {
        int x;
        int y;
        int val = 0;
        val = (int)(scroll.getHvalue()*22);
        scroll.setHvalue(val/22.0);
        x = (int)(event.getSceneX()-20)/30 + val;
        y = (int)(event.getSceneY()-108)/30;
        id = y*30+x;
        refreshLeft();
        gc2.setStroke(Color.RED);
        gc2.strokeRect(x*29,y*29,30,30);
        
        leftLabel.setText("Tile : " + id);
      
        System.out.println("hval: " + val +" hval/30: " + val/30.0);
        System.out.println("x: " + x +" y: " + y);
        System.out.println("x: " +(int)(scroll.getHvalue()*30)  +" y: " +scroll.getVvalue() );
     //leftLabel.setText("Button Action\n");
    }
    
    @FXML
    private void scrollBar(ScrollEvent event){
    	System.out.println("Scroll");
    	scroll.setHvalue(((int)(scroll.getHvalue()*30))/30);
    	
    }
     @FXML
    private void canvasMouse(MouseEvent event) {
        int x;
        int y;

        x = (int)(event.getSceneX()-486)/30;
        y = (int)(event.getSceneY()-235)/30;
        mainApp.editMap(y, x, id);
        mainApp.setCanvas(1);
        
        System.out.println("x: " + x +" y: " + y);
        //System.out.println("x: " +scroll.getHvalue()  +" y: " +scroll.getVvalue() );
    }
    private void refreshLeft(){
    	gc2 = canvas2.getGraphicsContext2D();
    	gc2.setFill(Color.MAGENTA);      
        gc2.fillRect(0,0,1000,1000);
        gc2.setStroke(Color.WHITE);
        gc2.setLineWidth(2);
        for(int j = 0; j<30; j++) 
            for(int i = 0; i<8; i++){
                gc2.drawImage(src2, j*30, i*30, 30, 30, j*30, i*30, 30, 30);
                gc2.strokeLine(29+30*j, 0, 29+30*j, 240);
                
            }
        for(int i = 0; i<8;i++)
        	gc2.strokeLine(0,29+30*i,900, 29+30*i);
        
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        scroll.setOnScrollFinished(this::scrollBar);
        gc = canvas.getGraphicsContext2D(); 
        refreshLeft();
       
    }
    
}
