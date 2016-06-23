/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static javax.swing.Spring.height;
import static javax.swing.Spring.width;

/**
 *
 * @author Shinobu Meahera
 */
    public class Main extends Application {
   
    private VBox rootLayout;
    private Stage primaryStage;
    private MainSceneController mController;
    private WritableImage sprite;
    public Image src = new Image("https://github.com/ShinobuMeahera/JavaGame/blob/master/Gra/tileset3.png?raw=true");
    
    // mapa
    private int[][] map;
    private int tileSize;
    private int numRows;
    private int numCols;
	
    
    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        initRootLayout();
    }
     
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    private void initRootLayout(){
        try{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("MainScene.fxml"));
        rootLayout = (VBox) loader.load();

        Scene mScene = new Scene(rootLayout);
        primaryStage.setScene(mScene);
        primaryStage.setTitle("Map Editor 1.3");
        primaryStage.show();
        mController = loader.getController();
        mController.setMainApp(this);
        
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void newMap(){
        Parent newMap;
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("NewMap.fxml"));
     
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(getPrimaryStage());
            stage.setTitle("Creating New Map");
            stage.setScene(new Scene(loader.load()));
            
            NewMapController controller = loader.getController();
            controller.setStage(stage);
            stage.showAndWait();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public void loadMap(){
        Parent loadMap;
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("LoadMap.fxml"));

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(getPrimaryStage());
            stage.setTitle("Load Existing Map");
            stage.setScene(new Scene(loader.load()));
            
            LoadMapController controller = loader.getController();
            controller.setStage(stage);
            controller.setApp(this);
            
            stage.showAndWait();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public void openMap(String fileName) {
        try {
            
            FileReader fileReader =  new FileReader(fileName);

            BufferedReader br = new BufferedReader(fileReader);
			
            //piersza linia odpowiada za szerokosc mapy
            numCols = Integer.parseInt(br.readLine());
            //druga linia odpowiada za ilosc wierszy, czyli nasza wysokosc mapy
            numRows = Integer.parseInt(br.readLine());
            
            map = new int[numRows][numCols];
			
            String delims = "\\s+";
            for(int row = 0; row < numRows; row++) {
		String line = br.readLine();
		String[] tokens = line.split(delims);
		for(int col = 0; col < numCols; col++) {
                    map[row][col] = Integer.parseInt(tokens[col]);
     
		}
            }
            br.close();
            setCanvas(1);
			
            }
            catch(FileNotFoundException ex) {
                System.out.println("Unable to open file '");                
            }
            catch(IOException ex) {
                System.out.println("Error reading file '");                  
            }
            catch(NullPointerException e){
                System.out.println("NullPointerExe"); 
            }
	}
    public void setCanvas(int mode){ 
        if(mode == 0){ //NOWA MAPA
            
        }
        else
            if(mode == 1){ // STARA MAPA 
                
                
                int x;
                int y; 
                int k = 0;
                mController.setCanvasParam(numCols*30, numRows*30);
                System.out.println("zaladowane"); 
                    for(int j = 0; j<numRows; j++) 
                        for(int i = 0; i<numCols; i++){
                            y = (map[j][i]%30);
                            x = (map[j][i]/30);
                            System.out.println(x +"  "+ y + " " + k++);
                            mController.print((i*30), (j*30), src, x, y);
                            
                }  
                  
            }
    }
    public void editMap(int x, int y, int val){
        map[x][y] = val;
    }
  
    public static void main(String[] args) {
        launch(args);
    }
    
}
