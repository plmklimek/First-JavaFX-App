package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class FoodMenu {
    @FXML AnchorPane foodcontent;
    public void hoverbuttonm(MouseEvent event) {
        if (event.getPickResult().getIntersectedNode().getParent() != null) {
            if (event.getPickResult().getIntersectedNode().getParent().getId().equals("foodcontent")) {
                for (int i = 0; i < foodcontent.getChildren().size(); i++) {
                    if (foodcontent.getChildren().get(i) == event.getPickResult().getIntersectedNode()) {
                        foodcontent.getChildren().get(i).setStyle("-fx-background-color: #00E604;");
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
            if (event.getPickResult().getIntersectedNode().getId().equals("foodcontent")) {
                for (int i = 0; i < foodcontent.getChildren().size(); i++) {
                    foodcontent.getChildren().get(i).setStyle("-fx-background-color: #70D602 ;");
                }
            }
        }
    }
    public void PressButton(MouseEvent event) throws IOException {
        if(event.getPickResult().getIntersectedNode().getId() != null){
            String pick = event.getPickResult().getIntersectedNode().getId();
            foodcontent.getChildren().clear();
            if(pick.equals("displayfood")){
                AnchorPane displayfood = FXMLLoader.load(getClass().getResource("DisplayFood.fxml"));
                foodcontent.getChildren().setAll(displayfood);
            }
            else if(pick.equals("addfood")){
                AnchorPane addfood = FXMLLoader.load(getClass().getResource("AddFood.fxml"));
                foodcontent.requestFocus();
                foodcontent.getChildren().setAll(addfood);
            }
            else if(pick.equals("editfood")){
                AnchorPane editfood = FXMLLoader.load(getClass().getResource("UpdateFood.fxml"));
                foodcontent.getChildren().setAll(editfood);
            }
            else if(pick.equals("deletefood")){
                AnchorPane deletefood = FXMLLoader.load(getClass().getResource("DeleteFood.fxml"));
                foodcontent.getChildren().setAll(deletefood);
            }
        }
    }
}
