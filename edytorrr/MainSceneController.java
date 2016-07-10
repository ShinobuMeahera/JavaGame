/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaGame.edytorrr;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InterfaceAddress;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.input.KeyCode.Z;
import static javafx.scene.input.KeyCode.X;

public class MainSceneController implements Initializable {



    private NewMapController newMapCon;
    private Main mainApp;
    private GraphicsContext gc;
    private GraphicsContext gc2;
    public  Image src2 ;

    private int id;
    int x;
    int lastX;
    int y;
    int lastY;
    int i = 0;
    int j = 0;
    int mapScrollHValue = 0;
    int mapScrollVValue = 0;
    int [] mouseLastPos = {0,0};
    private boolean zKey = false;
    private boolean xKey = false;
    private int tileSize = 30;
    int iMax;


    @FXML
    private MenuItem saveMenuItem;

    @FXML
    private AnchorPane controlBar;

    @FXML
    private MenuItem saveAsMenuItem;

    @FXML
    private Canvas canvas;

    @FXML
    private Canvas canvas2;

    @FXML
    private Button leftButton;

    @FXML
    private ToggleButton loopButton;

    @FXML
    private Label leftLabel;

    @FXML
    private Label loopLabel;

    @FXML
    private Label scaleLabel;

    @FXML
    private TextField hSize;

    @FXML
    private TextField vSize;

    @FXML
    private ScrollPane scroll;

    @FXML
    private ScrollPane mainScroll;

    @FXML
    private Slider loopControl;

    @FXML
    private Slider scaleSlider;

    public void hudEnable(){
        hSize.setText(Integer.toString(mainApp.numCols));
        vSize.setText(Integer.toString(mainApp.numRows));
        saveMenuItem.setDisable(false);
        saveAsMenuItem.setDisable(false);
        controlBar.setDisable(false);
        src2 = mainApp.src;
        refreshLeft();
    }

    @FXML
    public void newMapFunction(){
        mainApp.newMap();
    }

    @FXML
    public void loadMapFunction(){
        mainApp.loadMap();

    }

    @FXML
    public void saveMapFunction(){
        mainApp.saveMap(mainApp.mapa.getPath());
    }

    @FXML
    public void saveAsMapFunction(){
        mainApp.saveAsMap();
    }

    @FXML
    public void closeFunction(){
        System.exit(1);
    }

    @FXML
    public void preferFunction(){
        mainApp.Prefer();
    }

    @FXML
    public void scaleView(){
        tileSize= (int)((30*scaleSlider.getValue())/100);
        mainApp.tileSize = tileSize;
        mainApp.setCanvas(1);
        scaleSlider.setValue((tileSize/30.0)*100);
        System.out.println(tileSize);
        scaleLabel.setText(Integer.toString(tileSize));
        System.out.println((scaleSlider.getValue())/100);
    }

    @FXML
    private void reloadButton(){
        mainApp.openMap();
    }

    @FXML
    public void print(int x,int y,Image img,int row, int col){
        gc.drawImage(img, col*30, row*30, 30, 30, x, y, tileSize, tileSize);

    }



    @FXML
    private void sizeChange(){
        mainApp.changeSize(Integer.parseInt(hSize.getText().toString()), Integer.parseInt(vSize.getText().toString()));
        hSize.setText(Integer.toString(mainApp.numCols));
        vSize.setText(Integer.toString(mainApp.numRows));
        mainApp.setCanvas(1);
        System.out.println("Zmieniona rozmiar");
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
        gc2.strokeRect(x*30,y*30,30,30);

        leftLabel.setText("Tile : " + id);

        System.out.println("hval: " + val +" hval/30: " + val/30.0);
        System.out.println("x: " + x +" y: " + y);
        System.out.println("x: " +(int)(scroll.getHvalue()*30)  +" y: " +scroll.getVvalue() );
    }

    @FXML
    private void scrollBar(ScrollEvent event){
        System.out.println("Scroll");
        scroll.setHvalue(((int)(scroll.getHvalue()*30))/30);

    }

    private int xyPos(MouseEvent event){
        mapScrollHValue = (int)(mainScroll.getHvalue()*(mainApp.numCols-32/(scaleSlider.getValue()/100)));
        mapScrollVValue = (int)(mainScroll.getVvalue()*(mainApp.numRows-23/(scaleSlider.getValue()/100)));
        mainScroll.setHvalue(mapScrollHValue/(double)(mainApp.numCols-32/(scaleSlider.getValue()/100)));
        mainScroll.setVvalue(mapScrollVValue/(double)(mainApp.numRows-23/(scaleSlider.getValue()/100)));

        lastX = x;
        lastY = y;

        x = (int)(event.getSceneX()-302)/ tileSize + mapScrollHValue;
        y = (int)(event.getSceneY()-27)/ tileSize + mapScrollVValue;
        System.out.println("x : " + (event.getSceneX()-302) + "y : " + (event.getSceneY()-27));

        if(lastX !=  x)
            return 1;
        else
        if(lastY != y)
            return 2;
        else
            return 0;


    }
    @FXML
    private void mouse(MouseEvent event){

        xyPos(event);

        mainApp.setCanvas(1);
        gc.setStroke(Color.MAGENTA);
        gc.strokeRect(x*tileSize, y*tileSize, tileSize, tileSize);
        gc.setGlobalAlpha(0.7);
        print(x * tileSize, y * tileSize, src2 ,id/30, id%30);
        gc.setGlobalAlpha(1);
        canvas.setOnMousePressed(this::canvasMouse);

        if(zKey == false){
            i = 0;
            j = 0;
        }

        if(xKey == false){
            mouseLastPos[0] = 0;
            mouseLastPos[1] = 0;
        }

    }

    @FXML
    private void mainKeyPress(KeyEvent event){
        if(event.getCode() == Z){
            zKey = true;
            System.out.println("Z key Pressed");
        }

        if(event.getCode() == X){
            xKey = true;
            System.out.println("X key Pressed");
        } 


    }

    @FXML
    private void mainKeyRel(KeyEvent event){
        if(event.getCode() == Z){
            zKey = false;
            System.out.println("Z key Released");
        }

        if(event.getCode() == X){
            xKey = false;
            System.out.println("X key Released");
        }

    }

    @FXML
    private void loopEnable(){
        if(loopButton.isSelected())
            loopControl.setDisable(false);
        else
            loopControl.setDisable(true);

        iMax = (int) loopControl.getValue();
        loopLabel.setText(Integer.toString(iMax));
    }

    @FXML
    private void canvasMouse(MouseEvent event) {
    int xyTemp;


        if( (xyTemp = xyPos(event)) == 1) {
            if (zKey == true)
                if(loopButton.isSelected() == true && (i >= (iMax-1)) )
                    i = 0;
                else
                    i += 1;
            else
                i = 0;
        }else
        if(xyTemp == 2) {
            if (zKey == true)
                if(loopButton.isSelected() == true && (j >= (iMax-1)) )
                    j = 0;
                else
                    j += 1;
            else
                j = 0;
        }

        if(event.isPrimaryButtonDown()) {

            mainApp.editMap(y, x, (id + i)+(tileSize*j));
        }
        else
        if(event.isSecondaryButtonDown()) {

            mainApp.editMap(y, x, 0);
        }
        if (xKey == true) {
            if(mouseLastPos[0] == 0 && mouseLastPos[1] == 0) {
                mouseLastPos[0] = x;
                mouseLastPos[1] = y;
            }
            else{
                for(int  iy = 0; iy <= Math.abs(mouseLastPos[1] - y); iy++) {
                    for (int ix = 0; ix <= Math.abs(mouseLastPos[0] - x); ix++) {
                        mainApp.editMap(mouseLastPos[1]- iy, mouseLastPos[0] + ix, (id + i));
                        System.out.print("Petla X  :" + ix + "  d :" + Math.abs(mouseLastPos[0] - x));
                    }
                }
            }
        }
        else{
            mouseLastPos[0] = 0;
            mouseLastPos[1] = 0;
        }
        xyPos(event);
        mainApp.setCanvas(1);
        System.out.println("x: " + x +" y: " + y);
        System.out.println("x: " + x +" y: " + y + " " + i);
        System.out.println("Z  " + zKey + " x " + xKey);

    }
    public void refreshLeft(){
        gc2 = canvas2.getGraphicsContext2D();
        gc2.setFill(Color.MAGENTA);
        gc2.fillRect(0,0,1000,1000);

        for(int j = 0; j<30; j++)
            for(int i = 0; i<6; i++){
                gc2.drawImage(src2, j*30, i*30, 30, 30, j*30, i*30, 30, 30);
                gc2.strokeLine(30+30*j, 0, 30+30*j, 240);

            }
        gc2.setStroke(Color.WHITE);
        for(int j = 0; j<30; j++)
            for(int i = 0; i<6;i++) {
                gc2.strokeLine(30 + 30 * j, 0, 30 + 30 * j, 240);
                gc2.strokeLine(0, 30 + 30 * i, 900, 30 + 30 * i);
            }


    }

    public void setCanvasParam(int x, int y){
        canvas.setWidth(x);
        canvas.setHeight(y);
        canvas.setLayoutX(0);
        canvas.setLayoutY(0);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
        System.out.println(canvas.getHeight() + " " + canvas.getWidth());

    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        scroll.setOnScrollFinished(this::scrollBar);
        gc = canvas.getGraphicsContext2D();
        refreshLeft();

    }

}