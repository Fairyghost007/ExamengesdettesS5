package com.example.View;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {
    private AnchorPane detteView;

    public ViewFactory() {}

    public AnchorPane getDetteView() {
        if (detteView == null) {
            try{
                detteView = (AnchorPane)FXMLLoader.load(getClass().getResource("/Fxml/Client/DetteClient.fxml"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return detteView;
    }

    public void showConnexion(){
        FXMLLoader loader =new FXMLLoader(getClass().getResource("/Fxml/Connexion.fxml"));
        Scene scene =null;
        try{
            scene =new Scene(loader.load());

        }catch(Exception e){
            e.printStackTrace();
        }
        Stage stage =new Stage();
        stage.setScene(scene);
        stage.setTitle("Ges Dette");
        stage.show();
         
    }
}
