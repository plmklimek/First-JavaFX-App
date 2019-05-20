package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class FeedMenu {
    @FXML
    AnchorPane feedcontent;
    public void hoverbuttonm(MouseEvent event) {
        if (event.getPickResult().getIntersectedNode().getParent() != null) {
            if (event.getPickResult().getIntersectedNode().getParent().getId().equals("feedcontent")) {
                for (int i = 0; i < feedcontent.getChildren().size(); i++) {
                    if (feedcontent.getChildren().get(i) == event.getPickResult().getIntersectedNode()) {
                        feedcontent.getChildren().get(i).setStyle("-fx-background-color: #00E604;");
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
            if (event.getPickResult().getIntersectedNode().getId().equals("feedcontent")) {
                for (int i = 0; i < feedcontent.getChildren().size(); i++) {
                    feedcontent.getChildren().get(i).setStyle("-fx-background-color: #70D602 ;");
                }
            }
        }
    }
    public void PressButton(MouseEvent event) throws IOException {
        if(event.getPickResult().getIntersectedNode().getId() != null){
            String pick = event.getPickResult().getIntersectedNode().getId();
            feedcontent.getChildren().clear();
            if(pick.equals("displayfeed")){
                AnchorPane displayfeed = FXMLLoader.load(getClass().getResource("DisplayFeed.fxml"));
                feedcontent.getChildren().setAll(displayfeed);
            }
            else if(pick.equals("addfeed")){
                AnchorPane addfeed = FXMLLoader.load(getClass().getResource("AddFeedContent.fxml"));
                feedcontent.requestFocus();
                feedcontent.getChildren().setAll(addfeed);
            }
        }
    }
}
