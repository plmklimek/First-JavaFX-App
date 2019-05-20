package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class DisplayFood {
    @FXML TableView tabledisplayfood;
    @FXML ChoiceBox fooddisplayfiltr1;
    @FXML ChoiceBox fooddisplayfiltr2;
    @FXML ChoiceBox fooddisplayfiltr3;
    @FXML TextField fooddisplaytext1;
    @FXML TextField fooddisplaytext2;
    @FXML Button fooddisplaysend;
    @FXML AnchorPane displayfoodcontent;
    public void initialize() throws SQLException {
        TableFood();
    }
    public void backtomenu(javafx.scene.input.MouseEvent event) throws IOException {
        displayfoodcontent.getChildren().clear();
        AnchorPane foodmenu = FXMLLoader.load(getClass().getResource("foodmenu.fxml"));
        displayfoodcontent.getChildren().setAll(foodmenu);
    }
    /**
     Funkcja tworzaca strukture tabeli w oknie wyswietlania tabeli pokarmu
     */

    public void TableFood() throws SQLException {

        Connection con = null;
        con = Connect.getConnection();
        TableColumn idFood = new TableColumn("Id");
        idFood.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn nameFood = new TableColumn("Nazwa");
        nameFood.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn iloscFood = new TableColumn("Ilosc");
        iloscFood.setCellValueFactory(new PropertyValueFactory<>("ilosc"));
        nameFood.setMinWidth(350);
        iloscFood.setMinWidth(350);

        tabledisplayfood.getColumns().addAll(idFood, nameFood, iloscFood);

        Food food = null;
        PreparedStatement prsm = con.prepareStatement("select * from Pokarm");
        ResultSet rsfood = prsm.executeQuery();
        while (rsfood.next()) {
            food = new Food(rsfood.getInt(1), rsfood.getString(2), rsfood.getInt(3));
            tabledisplayfood.getItems().add(food);
        }
        InsertFood(0,0, 0,"", "");
        FoodFiltr();
    }
    /**
     Funkcja ustawiajace kontrolki do filtrowania tabeli jedzenia
     */
    public void FoodFiltr(){
        fooddisplayfiltr1.getItems().add("-");
        fooddisplayfiltr1.setValue("-");
        fooddisplayfiltr1.getItems().add("nazwa");
        fooddisplayfiltr1.getItems().add("ilosc wieksza od");
        fooddisplayfiltr1.getItems().add("ilosc mniejsza od");

        fooddisplayfiltr2.getItems().add("-");
        fooddisplayfiltr2.setValue("-");
        fooddisplayfiltr2.getItems().add("lub");
        fooddisplayfiltr2.getItems().add("i");

        fooddisplayfiltr3.getItems().add("-");
        fooddisplayfiltr3.setValue("-");
        fooddisplayfiltr3.getItems().add("nazwa");
        fooddisplayfiltr3.getItems().add("ilosc wieksza od");
        fooddisplayfiltr3.getItems().add("ilosc mniejsza od");

    }
    /**
     Funkcja obslugujaca nacisniecie przycisku filtrowania danych  z tabeli
     */
    public void pressFoodFiltrButton(ActionEvent event) throws SQLException, ClassNotFoundException, ParseException {
        InsertFood(fooddisplayfiltr1.getSelectionModel().getSelectedIndex(),fooddisplayfiltr2.getSelectionModel().getSelectedIndex(),fooddisplayfiltr3.getSelectionModel().getSelectedIndex(),fooddisplaytext1.getText(), fooddisplaytext2.getText());

    }
    /**

     Funkcja dodajaca filtrowane dane do tabelis
     */
    public void InsertFood(int act1 , int act2, int act3, String t1 , String t2) throws SQLException{
        tabledisplayfood.getItems().clear();
        Connection con = null;
        con = Connect.getConnection();
        Food food = null;
        ResultSet rsfood;
        String query = "";
        if((act1 == 0 || t1.isEmpty())) {
            query = "select * from Pokarm";
        }
        else if(act1 == 1){
            query = "select * from Pokarm WHERE nazwa LIKE ?";
            t1 = "%" + t1 + "%";
        }
        else if(act1 == 2){
            query = "select * from Pokarm WHERE ilosc >= ?";
        }
        else if(act1 == 3){
            query = "select * from Pokarm WHERE ilosc <= ?";
        }
        if(act1 != 0 && act2 != 0 && act3 != 0 && !t2.isEmpty()){
            if(act2 == 1){
                query += " OR ";
            }
            else{
                query += " AND ";
            }
            if(act3 == 1){
                query += "nazwa LIKE ?";
                t2 = "%" + t2 + "%";
            }
            else if(act3 == 2){
                query += "ilosc >= ?";
            }
            else if(act3 == 3){
                query += "ilosc <= ?";
            }
        }
        PreparedStatement prsm = con.prepareStatement(query);
        if(act1 != 0 && !t1.isEmpty()){
            if(act1 == 2 || act1 == 3){
                prsm.setInt(1,  Integer.parseInt(t1));
            }
            else {
                prsm.setString(1, t1);
            }
            if((act2 != 0) && (act3 != 0) && !t2.isEmpty()) {
                if(act3 == 2 || act3 == 3){
                    prsm.setInt(2,  Integer.parseInt(t2));
                }
                else {
                    prsm.setString(2, t2);
                }
            }
        }
        rsfood = prsm.executeQuery();
        int t = 0;
        while (rsfood.next()) {
            food = new Food(rsfood.getInt(1), rsfood.getString(2),rsfood.getInt(3));
            tabledisplayfood.getItems().add(food);
        }
    }
}
