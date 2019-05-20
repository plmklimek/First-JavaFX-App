package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CatWalksMenu {
    @FXML
    AnchorPane catwalkscontent;
    public void hoverbuttonm(MouseEvent event) {
        if (event.getPickResult().getIntersectedNode().getParent() != null) {
            if (event.getPickResult().getIntersectedNode().getParent().getId().equals("catwalkscontent")) {
                for (int i = 0; i < catwalkscontent.getChildren().size(); i++) {
                    if (catwalkscontent.getChildren().get(i) == event.getPickResult().getIntersectedNode()) {
                        catwalkscontent.getChildren().get(i).setStyle("-fx-background-color: #00E604;");
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
            if (event.getPickResult().getIntersectedNode().getId().equals("catwalkscontent")) {
                for (int i = 0; i < catwalkscontent.getChildren().size(); i++) {
                    catwalkscontent.getChildren().get(i).setStyle("-fx-background-color: #70D602 ;");
                }
            }
        }
    }
    public void PressButton(MouseEvent event) throws IOException {
        if(event.getPickResult().getIntersectedNode().getId() != null){
            String pick = event.getPickResult().getIntersectedNode().getId();
            catwalkscontent.getChildren().clear();
            if(pick.equals("displaycatwalks")){
                AnchorPane displaycatwalks = FXMLLoader.load(getClass().getResource("DisplayCatWalks.fxml"));
                catwalkscontent.getChildren().setAll(displaycatwalks);
            }
            else if(pick.equals("addcatwalks")){
                AnchorPane addcatwalks = FXMLLoader.load(getClass().getResource("AddCatWalks.fxml"));
                catwalkscontent.requestFocus();
                catwalkscontent.getChildren().setAll(addcatwalks);
            }
            else if(pick.equals("editcatwalks")){
                AnchorPane editcatwalks = FXMLLoader.load(getClass().getResource("UpdateCatWalks.fxml"));
                catwalkscontent.getChildren().setAll(editcatwalks);
            }
            else if(pick.equals("deletecatwalks")){
                AnchorPane deletecatwalks = FXMLLoader.load(getClass().getResource("DeleteCatWalks.fxml"));
                catwalkscontent.getChildren().setAll(deletecatwalks);
            }
        }
    }
}
