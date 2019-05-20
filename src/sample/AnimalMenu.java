package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AnimalMenu {
    @FXML AnchorPane animalcontent;
    public void hoverbuttonm(MouseEvent event) {
        if (event.getPickResult().getIntersectedNode().getParent() != null) {
            if (event.getPickResult().getIntersectedNode().getParent().getId().equals("animalcontent")) {
                for (int i = 0; i < animalcontent.getChildren().size(); i++) {
                    if (animalcontent.getChildren().get(i) == event.getPickResult().getIntersectedNode()) {
                        animalcontent.getChildren().get(i).setStyle("-fx-background-color: #00E604;");
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
            if (event.getPickResult().getIntersectedNode().getId().equals("animalcontent")) {
                for (int i = 0; i < animalcontent.getChildren().size(); i++) {
                    animalcontent.getChildren().get(i).setStyle("-fx-background-color: #70D602 ;");
                }
            }
        }
    }
    public void PressButton(MouseEvent event) throws IOException {
        if(event.getPickResult().getIntersectedNode().getId() != null){
            String pick = event.getPickResult().getIntersectedNode().getId();
            animalcontent.getChildren().clear();
            if(pick.equals("displayanimal")){
                AnchorPane displayanimal = FXMLLoader.load(getClass().getResource("DisplayAnimal.fxml"));
                animalcontent.getChildren().setAll(displayanimal);
            }
            else if(pick.equals("addanimal")){
                AnchorPane addanimal = FXMLLoader.load(getClass().getResource("AddAnimal.fxml"));
                animalcontent.requestFocus();
                animalcontent.getChildren().setAll(addanimal);
            }
            else if(pick.equals("editanimal")){
                AnchorPane updateanimal = FXMLLoader.load(getClass().getResource("UpdateAnimal.fxml"));
                animalcontent.getChildren().setAll(updateanimal);
            }
            else if(pick.equals("deleteanimal")){
                AnchorPane deleteanimal = FXMLLoader.load(getClass().getResource("DeleteAnimal.fxml"));
                animalcontent.getChildren().setAll(deleteanimal);
            }
        }
    }
}
