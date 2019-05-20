package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class PavilonMenu {
    @FXML
    AnchorPane paviloncontent;
    public void hoverbuttonm(MouseEvent event) {
        if (event.getPickResult().getIntersectedNode().getParent() != null) {
            if (event.getPickResult().getIntersectedNode().getParent().getId().equals("paviloncontent")) {
                for (int i = 0; i < paviloncontent.getChildren().size(); i++) {
                    if (paviloncontent.getChildren().get(i) == event.getPickResult().getIntersectedNode()) {
                        paviloncontent.getChildren().get(i).setStyle("-fx-background-color: #00E604;");
                    }
                }
            }
        }
    }

    /**
     * Funkcja przechowujaca zmiane koloru przycisku przy najechaniu
     */
    public void hoverbuttonme(MouseEvent event) {
        if (event.getPickResult().getIntersectedNode().getId() != null) {
            if (event.getPickResult().getIntersectedNode().getId().equals("paviloncontent")) {
                for (int i = 0; i < paviloncontent.getChildren().size(); i++) {
                    paviloncontent.getChildren().get(i).setStyle("-fx-background-color: #70D602 ;");
                }
            }
        }
    }
    public void PressButton(MouseEvent event) throws IOException {
        if(event.getPickResult().getIntersectedNode().getId() != null){
            String pick = event.getPickResult().getIntersectedNode().getId();
            paviloncontent.getChildren().clear();
            if(pick.equals("displaypavilon")){
                AnchorPane displaypavilon = FXMLLoader.load(getClass().getResource("DisplayPavilon.fxml"));
                paviloncontent.getChildren().setAll(displaypavilon);
            }
            else if(pick.equals("addpavilon")){
                AnchorPane addpavilon = FXMLLoader.load(getClass().getResource("AddPavilon.fxml"));
                paviloncontent.requestFocus();
                paviloncontent.getChildren().setAll(addpavilon);
            }
            else if(pick.equals("editpavilon")){
                AnchorPane updatepavilon = FXMLLoader.load(getClass().getResource("UpdatePavilon.fxml"));
                paviloncontent.getChildren().setAll(updatepavilon);
            }
            else if(pick.equals("deletepavilon")){
                AnchorPane deletepavilon = FXMLLoader.load(getClass().getResource("DeletePavilon.fxml"));
                paviloncontent.getChildren().setAll(deletepavilon);
            }
        }
    }
}
