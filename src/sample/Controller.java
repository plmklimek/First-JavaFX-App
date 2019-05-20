package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Controller {
    @FXML
    AnchorPane navbar;
    @FXML
    AnchorPane animalcontent;
    @FXML
    AnchorPane usercontent;
    @FXML
    AnchorPane foodcontent;
    @FXML
    AnchorPane feedcontent;
    @FXML
    AnchorPane paviloncontent;
    @FXML
    AnchorPane catwalkscontent;
    @FXML
    AnchorPane fullcontent;

    /**
     * Funkcja obslugujaca zmiane koloru przy najechaniu  na przycisk menu podstawowego
     */
    public void hoverbutton(MouseEvent event) {
        for (int i = 0; i < navbar.getChildren().size(); i++) {
            if (navbar.getChildren().get(i) == event.getPickResult().getIntersectedNode().getParent()) {
                navbar.getChildren().get(i).setStyle("-fx-background-color: #00E604;");
            }
        }
    }

    /**
     * Funkcja obslugujaca zmiane koloru przy najechaniu  na przycisk menu odpowiedniej funckjonalnosci
     */
    public void hoverbuttone(MouseEvent event) {
        for (int i = 0; i < navbar.getChildren().size(); i++) {
            navbar.getChildren().get(i).setStyle("-fx-background-color:  #7BFF24 ;");
        }
    }

    public void hoverbuttonm(MouseEvent event) {
        if (event.getPickResult().getIntersectedNode().getParent() != null) {
            if (event.getPickResult().getIntersectedNode().getParent().getParent().getId().equals("usercontent")) {
                for (int i = 0; i < usercontent.getChildren().size(); i++) {
                    if (usercontent.getChildren().get(i) == event.getPickResult().getIntersectedNode().getParent()) {
                        usercontent.getChildren().get(i).setStyle("-fx-background-color: #00E604;");
                    }
                }
            } else if (event.getPickResult().getIntersectedNode().getParent().getParent().getId().equals("animalcontent")) {
                for (int i = 0; i < animalcontent.getChildren().size(); i++) {
                    if (animalcontent.getChildren().get(i) == event.getPickResult().getIntersectedNode().getParent()) {
                        animalcontent.getChildren().get(i).setStyle("-fx-background-color: #00E604;");
                    }
                }
            } else if (event.getPickResult().getIntersectedNode().getParent().getParent().getId().equals("foodcontent")) {
                for (int i = 0; i < foodcontent.getChildren().size(); i++) {
                    if (foodcontent.getChildren().get(i) == event.getPickResult().getIntersectedNode().getParent()) {
                        foodcontent.getChildren().get(i).setStyle("-fx-background-color: #00E604;");
                    }
                }
            } else if (event.getPickResult().getIntersectedNode().getParent().getParent().getId().equals("feedcontent")) {
                for (int i = 0; i < feedcontent.getChildren().size(); i++) {
                    if (feedcontent.getChildren().get(i) == event.getPickResult().getIntersectedNode().getParent()) {
                        feedcontent.getChildren().get(i).setStyle("-fx-background-color: #00E604;");
                    }
                }
            } else if (event.getPickResult().getIntersectedNode().getParent().getParent().getId().equals("paviloncontent")) {
                for (int i = 0; i < paviloncontent.getChildren().size(); i++) {
                    if (paviloncontent.getChildren().get(i) == event.getPickResult().getIntersectedNode().getParent()) {
                        paviloncontent.getChildren().get(i).setStyle("-fx-background-color: #00E604;");
                    }
                }
            } else if (event.getPickResult().getIntersectedNode().getParent().getParent().getId().equals("catwalkscontent")) {
                for (int i = 0; i < catwalkscontent.getChildren().size(); i++) {
                    if (catwalkscontent.getChildren().get(i) == event.getPickResult().getIntersectedNode().getParent()) {
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
            if (event.getPickResult().getIntersectedNode().getId().equals("usercontent")) {
                for (int i = 0; i < usercontent.getChildren().size(); i++) {
                    usercontent.getChildren().get(i).setStyle("-fx-background-color: #70D602 ;");
                }
            } else if (event.getPickResult().getIntersectedNode().getId().equals("animalcontent")) {
                for (int i = 0; i < animalcontent.getChildren().size(); i++) {
                    animalcontent.getChildren().get(i).setStyle("-fx-background-color: #70D602 ;");
                }
            } else if (event.getPickResult().getIntersectedNode().getId().equals("foodcontent")) {
                for (int i = 0; i < foodcontent.getChildren().size(); i++) {
                    foodcontent.getChildren().get(i).setStyle("-fx-background-color: #70D602 ;");
                }
                ;
            } else if (event.getPickResult().getIntersectedNode().getId().equals("feedcontent")) {
                for (int i = 0; i < feedcontent.getChildren().size(); i++) {
                    feedcontent.getChildren().get(i).setStyle("-fx-background-color: #70D602 ;");
                }
            } else if (event.getPickResult().getIntersectedNode().getId().equals("paviloncontent")) {
                for (int i = 0; i < paviloncontent.getChildren().size(); i++) {
                    paviloncontent.getChildren().get(i).setStyle("-fx-background-color: #70D602 ;");
                }
            } else if (event.getPickResult().getIntersectedNode().getId().equals("catwalkscontent")) {
                for (int i = 0; i < catwalkscontent.getChildren().size(); i++) {
                    catwalkscontent.getChildren().get(i).setStyle("-fx-background-color: #70D602 ;");
                }
            }
        }
    }

    public void pressButton(MouseEvent event) throws IOException {
        fullcontent.getChildren().clear();
        String pick = event.getPickResult().getIntersectedNode().getParent().getId();
        if (pick.equals("user_btn")) {
            AnchorPane usr = FXMLLoader.load(getClass().getResource("usermenu.fxml"));
            fullcontent.getChildren().setAll(usr);
        } else if (pick.equals("home_btn")) {
            AnchorPane home = FXMLLoader.load(getClass().getResource("home.fxml"));
            fullcontent.getChildren().setAll(home);
        } else if (pick.equals("animal_btn")) {
            AnchorPane animal = FXMLLoader.load(getClass().getResource("animalmenu.fxml"));
            fullcontent.getChildren().setAll(animal);
        } else if (pick.equals("food_btn")) {
            AnchorPane food = FXMLLoader.load(getClass().getResource("foodmenu.fxml"));
            fullcontent.getChildren().setAll(food);
        } else if (pick.equals("feed_btn")) {
            AnchorPane feed = FXMLLoader.load(getClass().getResource("feedmenu.fxml"));
            fullcontent.getChildren().setAll(feed);

        } else if (pick.equals("catwalks_btn")) {
            AnchorPane catwalks = FXMLLoader.load(getClass().getResource("catwalksmenu.fxml"));
            fullcontent.getChildren().setAll(catwalks);
        } else if (event.getPickResult().getIntersectedNode().getParent().getId().equals("pavilon_btn")) {
            AnchorPane pavilon = FXMLLoader.load(getClass().getResource("pavilonmenu.fxml"));
            fullcontent.getChildren().setAll(pavilon);
        }
    }}

