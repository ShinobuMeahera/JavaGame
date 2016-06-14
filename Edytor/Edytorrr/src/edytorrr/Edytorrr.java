/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edytorrr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Shinobu Meahera
 */
public class Edytorrr extends Application {
    
    private Stage primaryStage;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        this.primaryStage = stage;
        Scene scene = new Scene(root);
        this.primaryStage.setTitle("Map Editor 1.3");
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }
     
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
