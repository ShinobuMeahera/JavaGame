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
    public Image src;
    public String tileMapName = "tileset3.png";
    

    private int[][] map = new int[0][0];
    public int tileSize = 30;
    public int numRows;
    public int numCols;
    public File mapa;
	
    
    @Override
    public void start(Stage stage) throws Exception {
        try{
            src = new Image(new FileInputStream(tileMapName));
        }
        catch(Exception e){
            System.out.print("Brak src2");
        }
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
            controller.setApp(this);
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
        if(mapa == null) {
            return;
        }
        System.out.println("Wybrano plik do zalodowania");
        System.out.println(mapa.getPath());

        openMap();

    }
    public void createNewMap(int x, int y, String name){
        mapa = new File(name);
        numRows = 0;
        numCols = 0;
        map = new int[0][0];
        changeSize(x,y);
        setCanvas(1);

        mController.hudEnable();

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
            mController.hudEnable();
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
    public void Prefer(){
        Parent prefer;
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("Prefer.fxml"));

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(getPrimaryStage());
            stage.setTitle("Preferences");
            stage.setScene(new Scene(loader.load()));

            PreferController controller = loader.getController();
            controller.setApp(this);
            controller.run();
            stage.showAndWait();

    }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public void changeTileMap(File newtileMap){
        tileMapName = newtileMap.getPath();
        try{
            src = new Image(new FileInputStream(tileMapName));
        }
        catch(Exception e){
            System.out.print("Brak src2");
        }
        mController.src2 = src;
        mController.refreshLeft();
        setCanvas(1);

    }
    public void setCanvas(int mode){ 
        if(mode == 0){ //NOWA MAPA
            
        }
        else
            if(mode == 1){ // STARA MAPA 
                
                
                int x;
                int y;

                mController.setCanvasParam(numCols*tileSize, numRows*tileSize);

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

    public void saveAsMap(){
        File saveFile;
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Save as ...");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("map", "*.map"),
                new FileChooser.ExtensionFilter("Map", "*.Map")
        );
        saveFile =  fileChooser.showSaveDialog(primaryStage);

        if(saveFile == null){
            return; //wychodzi z funkcji jesli niewybrano pliku i anulowano
        }
        saveMap(saveFile.getPath());

        mapa = saveFile;

        System.out.println("Zapisano plik");

    }

    public void changeSize(int x, int y){
        int [][] mapTemp = map;
        int rowDiff = 0;
        int colDiff = 0;

        map = new int[y][x];
        if(numRows > y)
            rowDiff = numRows - y;
        if(numCols > x)
            colDiff = numCols - x;

        for(int row = 0; row < numRows - rowDiff; row++) {
            for (int col = 0; col < numCols - colDiff; col++) {
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
