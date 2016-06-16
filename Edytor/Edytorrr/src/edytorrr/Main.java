/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edytorrr;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Shinobu Meahera
 */
    public class Main extends Application {
   
    private VBox rootLayout;
    private Stage primaryStage;
    
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
        MainSceneController controller = loader.getController();
        controller.setMainApp(this);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
   
    public static void main(String[] args) {
        launch(args);
    }
    
}
