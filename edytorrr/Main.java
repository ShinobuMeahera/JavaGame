/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaGame.edytorrr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;


public class Main extends Application {
   
    private VBox rootLayout;
    private Stage primaryStage;
    private MainSceneController mController;
    public Image src = new Image("https://github.com/ShinobuMeahera/JavaGame/blob/master/Gra/tileset3.png?raw=true");
    
    // mapa
    private int[][] map;
    protected int tileSize = 30;
    public int numRows;
    public int numCols;
    public File mapa;
	
    
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
        primaryStage.setTitle("Map Editor 2.0");
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

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Load Map");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("map", "*.map"),
                new FileChooser.ExtensionFilter("Map", "*.Map")
        );
        mapa =  fileChooser.showOpenDialog(primaryStage);
        System.out.println("Wybrano plik");
        openMap();

    }
    public void openMap() {
        try {
            int numColsWork = 0;
            int numRowsWork = 0;
            FileReader fileReader =  new FileReader(mapa);

            BufferedReader br = new BufferedReader(fileReader);
			
            //piersza linia odpowiada za szerokosc mapy
            numColsWork = Integer.parseInt(br.readLine());
            //druga linia odpowiada za ilosc wierszy, czyli nasza wysokosc mapy
            numRowsWork = Integer.parseInt(br.readLine());
            numCols = numColsWork ;
            numRows = numRowsWork ;
            System.out.println(numCols + " " +  numRows);
            map = new int[numRowsWork][numColsWork];
			
            String delims = "\\s+";
            for(int row = 0; row < numRowsWork; row++) {
		    String line = br.readLine();
		    String[] tokens = line.split(delims);
		    for(int col = 0; col < numColsWork; col++) {
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
                mController.setCanvasParam(numCols*tileSize, numRows*tileSize);
                //System.out.println("zaladowane");
                    for(int j = 0; j<numRows; j++) 
                        for(int i = 0; i<numCols; i++){
                            y = (map[j][i]%30);
                            x = (map[j][i]/30);
                            mController.print((i*tileSize), (j*tileSize), src, x, y);
                            
                }  
                  
            }
    }
    public void editMap(int x, int y, int val){
        map[x][y] = val;
    }
    public void saveMap(String fileName){

        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fileWriter);

            bw.write(Integer.toString(numCols));
            bw.newLine();
            bw.write(Integer.toString(numRows));
            bw.newLine();
            for(int row = 0; row < numRows; row++) {
                for(int col = 0; col < numCols; col++) {
                    bw.write(Integer.toString(map[row][col]));
                    bw.write("  ");
                }
                bw.newLine();
            }

            bw.close();
        }
        catch(IOException ex) {
            System.out.println("Error reading file '");
        }
        catch(NullPointerException e){
            System.out.println("NullPointerExe");
        }

    }
    public void changeSize(int x, int y){
        int [][] mapTemp = map;

        map = new int[y][x];

        for(int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                map[row][col] = mapTemp[row][col];
            }
        }
        for(int row = numRows; row < y; row++) {
            for (int col = numCols; col < x; col++) {
                map[row][col] = 0;
            }
        }
        numCols = x;
        numRows = y;

    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
