package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Usermenu {
@FXML
    AnchorPane usercontent;
    public void hoverbuttonm(MouseEvent event) {
        if (event.getPickResult().getIntersectedNode().getParent() != null) {
            if (event.getPickResult().getIntersectedNode().getParent().getId().equals("usercontent")) {
                for (int i = 0; i < usercontent.getChildren().size(); i++) {
                    if (usercontent.getChildren().get(i) == event.getPickResult().getIntersectedNode()) {
                        usercontent.getChildren().get(i).setStyle("-fx-background-color: #00E604;");
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
            if (event.getPickResult().getIntersectedNode().getId().equals("usercontent")) {
                for (int i = 0; i < usercontent.getChildren().size(); i++) {
                    usercontent.getChildren().get(i).setStyle("-fx-background-color: #70D602 ;");
                }
            }
        }
    }
    public void PressButton(MouseEvent event) throws IOException {
        if(event.getPickResult().getIntersectedNode().getId() != null){
            String pick = event.getPickResult().getIntersectedNode().getId();
            usercontent.getChildren().clear();
            if(pick.equals("displayuser")){
                AnchorPane displayuser = FXMLLoader.load(getClass().getResource("DisplayUser.fxml"));
                usercontent.getChildren().setAll(displayuser);
            }
            else if(pick.equals("adduser")){
                AnchorPane adduser = FXMLLoader.load(getClass().getResource("AddUser.fxml"));
                usercontent.requestFocus();
                usercontent.getChildren().setAll(adduser);
            }
            else if(pick.equals("updateuser")){
                AnchorPane updateuser = FXMLLoader.load(getClass().getResource("UpdateUser.fxml"));
                usercontent.getChildren().setAll(updateuser);
            }
            else if(pick.equals("deleteuser")){
                AnchorPane deleteuser = FXMLLoader.load(getClass().getResource("DeleteUser.fxml"));
                usercontent.getChildren().setAll(deleteuser);
            }
        }
    }
}
