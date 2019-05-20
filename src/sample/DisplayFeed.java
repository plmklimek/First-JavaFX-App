package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;

public class DisplayFeed {
    @FXML TableView tabledisplayfeed;
    @FXML ChoiceBox feeddisplayfood2;
    @FXML ChoiceBox feeddisplayfood1;
    @FXML ChoiceBox feeddisplayfiltr1;
    @FXML ChoiceBox feeddisplayfiltr2;
    @FXML ChoiceBox feeddisplayfiltr3;
    @FXML ChoiceBox feeddisplayanimal2;
    @FXML ChoiceBox feeddisplayanimal1;
    @FXML ChoiceBox feeddisplayuser1;
    @FXML ChoiceBox feeddisplayuser2;
    @FXML TextField feeddisplaytext1;
    @FXML TextField feeddisplaytext2;
    @FXML DatePicker feeddisplaydate1;
    @FXML DatePicker feeddisplaydate2;
    @FXML AnchorPane displayfeedcontent;
    public void initialize() throws SQLException {
        TableFeed();
    }
    public void backtomenu(javafx.scene.input.MouseEvent event) throws IOException {
        displayfeedcontent.getChildren().clear();
        AnchorPane feedmenu = FXMLLoader.load(getClass().getResource("feedmenu.fxml"));
        displayfeedcontent.getChildren().setAll(feedmenu);
    }
    /**
     Funkcja tworzaca strukture tabeli w oknie wyswietlania tabeli karmienia
     */
    public void TableFeed() throws SQLException {
        feeddisplayfood1.setVisible(false);
        feeddisplayanimal1.setVisible(false);
        feeddisplayuser1.setVisible(false);
        feeddisplaytext1.setVisible(true);
        feeddisplaydate1.setVisible(false);
        feeddisplayfood2.setVisible(false);
        feeddisplayanimal2.setVisible(false);
        feeddisplayuser2.setVisible(false);
        feeddisplaytext2.setVisible(true);
        feeddisplaydate2.setVisible(false);
        Connection con = null;
        con = Connect.getConnection();
        TableColumn idFeed = new TableColumn("Id");
        idFeed.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn datefeed = new TableColumn("Data");
        datefeed.setCellValueFactory(new PropertyValueFactory<>("date"));
        TableColumn iloscFeed = new TableColumn("Ilosc");
        iloscFeed.setCellValueFactory(new PropertyValueFactory<>("ilosc"));
        TableColumn zwierzeta_id = new TableColumn("Zwierze");
        zwierzeta_id.setCellValueFactory(new PropertyValueFactory<>("zwierzeta_id"));
        TableColumn pracownicy_id = new TableColumn("Pracownik");
        pracownicy_id.setCellValueFactory(new PropertyValueFactory<>("pracownicy_id"));
        TableColumn pokarm_id = new TableColumn("Pokarm");
        pokarm_id.setCellValueFactory(new PropertyValueFactory<>("pokarm_id"));
        pokarm_id.setMinWidth(390);

        tabledisplayfeed.getColumns().addAll(idFeed, datefeed, iloscFeed, zwierzeta_id, pracownicy_id, pokarm_id);
        Feed feed = null;
        PreparedStatement prsm = con.prepareStatement("SELECT A.ID,A.Data,A.ILOSC,B.Nazwa,C.IMIE || ' ' || C.Nazwisko personalia,D.Nazwa pokarm \n" +
                "FROM Karmienie A JOIN Zwierzeta B ON A.ZWIERZETA_ID = B.ID  \n" +
                "JOIN Pracownicy C ON  A.Pracownicy_ID = C.ID JOIN Pokarm D ON A.POKARM_ID = D.ID");
        ResultSet rsfeed = prsm.executeQuery();
        while (rsfeed.next()) {
            feed = new Feed(rsfeed.getInt(1), rsfeed.getDate(2), rsfeed.getInt(3),rsfeed.getString(4),rsfeed.getString(5), rsfeed.getString(6));
            tabledisplayfeed.getItems().add(feed);

        }
        InsertFeed(0,0, 0,"", "");
        FeedFiltr();
    }
    /**
     Funkcja ustawiajace kontrolki do filtrowania tabeli karmienia
     */
    public void FeedFiltr() throws SQLException {
        feeddisplayfiltr1.getItems().add("-");
        feeddisplayfiltr1.setValue("-");
        feeddisplayfiltr1.getItems().add("pokarm");
        feeddisplayfiltr1.getItems().add("zwierze");
        feeddisplayfiltr1.getItems().add("opiekun");
        feeddisplayfiltr1.getItems().add("ilosc pokarmu wieksze od");
        feeddisplayfiltr1.getItems().add("ilosc pokarmu mniejsze od");
        feeddisplayfiltr1.getItems().add("karmienie po");
        feeddisplayfiltr1.getItems().add("karmienie przed");

        feeddisplayfiltr2.getItems().add("-");
        feeddisplayfiltr2.setValue("-");
        feeddisplayfiltr2.getItems().add("lub");
        feeddisplayfiltr2.getItems().add("i");

        feeddisplayfiltr3.getItems().add("-");
        feeddisplayfiltr3.setValue("-");
        feeddisplayfiltr3.getItems().add("pokarm");
        feeddisplayfiltr3.getItems().add("zwierze");
        feeddisplayfiltr3.getItems().add("opiekun");
        feeddisplayfiltr3.getItems().add("ilosc pokarmu wieksze od");
        feeddisplayfiltr3.getItems().add("ilosc pokarmu mniejsze od");
        feeddisplayfiltr3.getItems().add("karmienie po");
        feeddisplayfiltr3.getItems().add("karmienie przed");
        Connection con = null;
        con = Connect.getConnection();
        PreparedStatement psrmu = con.prepareStatement("SELECT id,imie,nazwisko FROM Pracownicy");
        PreparedStatement psrma = con.prepareStatement("SELECT A.id,A.nazwa,B.nazwa FROM Zwierzeta A JOIN Wybiegi B ON A.wybiegi_id = B.id");
        PreparedStatement psrmf = con.prepareStatement("SELECT * FROM Pokarm");
        ResultSet rsuser = psrmu.executeQuery();
        ResultSet rsanimal = psrma.executeQuery();
        ResultSet rsfood = psrmf.executeQuery();
        feeddisplayfood1.getItems().clear();
        feeddisplayfood2.getItems().clear();
        feeddisplayanimal1.getItems().clear();
        feeddisplayanimal2.getItems().clear();
        feeddisplayuser1.getItems().clear();
        feeddisplayuser2.getItems().clear();
        while(rsuser.next()){
            feeddisplayuser1.getItems().add(rsuser.getInt(1) + ". " + rsuser.getString(2) + " " + rsuser.getString(3));
            feeddisplayuser2.getItems().add(rsuser.getInt(1) + ". " + rsuser.getString(2) + " " + rsuser.getString(3));
        }
        while(rsanimal.next()){
            feeddisplayanimal1.getItems().add(rsanimal.getInt(1) + ". " + rsanimal.getString(2) + " Wybiegi : " + rsanimal.getString(3) );
            feeddisplayanimal2.getItems().add(rsanimal.getInt(1) + ". " + rsanimal.getString(2) + " Wybiegi : " + rsanimal.getString(3) );
        }
        while(rsfood.next()){
            feeddisplayfood1.getItems().add(rsfood.getInt(1) + ". " + rsfood.getString(2) + " ilosc:" + rsfood.getInt(3));
            feeddisplayfood2.getItems().add(rsfood.getInt(1) + ". " + rsfood.getString(2) + " ilosc:" + rsfood.getInt(3));
        }
        feeddisplayfiltr1.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldV, newV) -> {
                    int t = feeddisplayfiltr1.getSelectionModel().getSelectedIndex();
                    if(t == 1){
                        feeddisplayfood1.setVisible(true);
                        feeddisplayanimal1.setVisible(false);
                        feeddisplayuser1.setVisible(false);
                        feeddisplaytext1.setVisible(false);
                        feeddisplaydate1.setVisible(false);
                    }
                    else if(t == 2){
                        feeddisplayfood1.setVisible(false);
                        feeddisplayanimal1.setVisible(true);
                        feeddisplayuser1.setVisible(false);
                        feeddisplaytext1.setVisible(false);
                        feeddisplaydate1.setVisible(false);
                    }
                    else if(t == 3){
                        feeddisplayfood1.setVisible(false);
                        feeddisplayanimal1.setVisible(false);
                        feeddisplayuser1.setVisible(true);
                        feeddisplaytext1.setVisible(false);
                        feeddisplaydate1.setVisible(false);
                    }
                    else if(t == 4 || t == 5){
                        feeddisplayfood1.setVisible(false);
                        feeddisplayanimal1.setVisible(false);
                        feeddisplayuser1.setVisible(false);
                        feeddisplaytext1.setVisible(true);
                        feeddisplaydate1.setVisible(false);
                    }
                    else if(t == 6 || t == 7){
                        feeddisplayfood1.setVisible(false);
                        feeddisplayanimal1.setVisible(false);
                        feeddisplayuser1.setVisible(false);
                        feeddisplaytext1.setVisible(false);
                        feeddisplaydate1.setVisible(true);
                    }
                });
        feeddisplayfiltr3.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldV, newV) -> {
                    int t = feeddisplayfiltr3.getSelectionModel().getSelectedIndex();
                    if(t == 1){
                        feeddisplayfood2.setVisible(true);
                        feeddisplayanimal2.setVisible(false);
                        feeddisplayuser2.setVisible(false);
                        feeddisplaytext2.setVisible(false);
                        feeddisplaydate2.setVisible(false);
                    }
                    else if(t == 2){
                        feeddisplayfood2.setVisible(false);
                        feeddisplayanimal2.setVisible(true);
                        feeddisplayuser2.setVisible(false);
                        feeddisplaytext2.setVisible(false);
                        feeddisplaydate2.setVisible(false);
                    }
                    else if(t == 3){
                        feeddisplayfood2.setVisible(false);
                        feeddisplayanimal2.setVisible(false);
                        feeddisplayuser2.setVisible(true);
                        feeddisplaytext2.setVisible(false);
                        feeddisplaydate2.setVisible(false);
                    }
                    else if(t == 4 || t == 5){
                        feeddisplayfood2.setVisible(false);
                        feeddisplayanimal2.setVisible(false);
                        feeddisplayuser2.setVisible(false);
                        feeddisplaytext2.setVisible(true);
                        feeddisplaydate2.setVisible(false);
                    }
                    else if(t == 6 || t == 7){
                        feeddisplayfood2.setVisible(false);
                        feeddisplayanimal2.setVisible(false);
                        feeddisplayuser2.setVisible(false);
                        feeddisplaytext2.setVisible(false);
                        feeddisplaydate2.setVisible(true);
                    }
                });
    }
    /**
     Funkcja obslugujaca nacisniecie przycisku filtrowania danych  z tabeli
     */
    public void pressFeedFiltrButton(ActionEvent event) throws SQLException, ClassNotFoundException, ParseException {
        int id1 = feeddisplayfiltr1.getSelectionModel().getSelectedIndex();
        int id2 = feeddisplayfiltr3.getSelectionModel().getSelectedIndex();
        String act1 = null;
        String act2 = null;
        if(id1 == 1) {
            String t = (String) feeddisplayfood1.getSelectionModel().getSelectedItem();
            if (!"-".equals(t) && t != null) {
                act1 = t.split("\\.")[0];
            }
        }
        else if(id1 == 2){
            String t = (String) feeddisplayanimal1.getSelectionModel().getSelectedItem();
            if (!"-".equals(t) && t != null) {
                act1 = t.split("\\.")[0];
            }
        }
        else if(id1 == 3){
            String t = (String) feeddisplayuser1.getSelectionModel().getSelectedItem();
            if (!"-".equals(t) && t != null) {
                act1 = t.split("\\.")[0];
            }
        }
        else if(id1 == 4 || id1 == 5){
            act1 = feeddisplaytext1.getText();
        }
        else if(id1 == 6 || id1 == 7){
            act1 = String.valueOf(feeddisplaydate1.getValue());
        }
        if(id2 == 1) {
            String t = (String) feeddisplayfood2.getSelectionModel().getSelectedItem();
            if (!"-".equals(t) && t != null) {
                act2 = t.split("\\.")[0];
            }
        }
        else if(id2 == 2){
            String t = (String) feeddisplayanimal2.getSelectionModel().getSelectedItem();
            if (!"-".equals(t) && t != null) {
                act2 = t.split("\\.")[0];
            }
        }
        else if(id2 == 3){
            String t = (String) feeddisplayuser2.getSelectionModel().getSelectedItem();
            if (!"-".equals(t) && t != null) {
                act2 = t.split("\\.")[0];
            }
        }
        else if(id2 == 4 || id2 == 5){
            act2 = feeddisplaytext2.getText();
        }
        else if(id2 == 6 || id2 == 7){
            act2 = String.valueOf(feeddisplaydate2.getValue());
        }
        InsertFeed(feeddisplayfiltr1.getSelectionModel().getSelectedIndex(), feeddisplayfiltr2.getSelectionModel().getSelectedIndex(), feeddisplayfiltr3.getSelectionModel().getSelectedIndex(), act1, act2);
    }
    /**

     Funkcja dodajaca filtrowane dane do tabelis
     */
    public void InsertFeed(int act1 , int act2, int act3, String t1 , String t2) throws SQLException{
        tabledisplayfeed.getItems().clear();
        Connection con = null;
        con = Connect.getConnection();
        Feed feed = null;
        ResultSet rsfeed;
        String query = "";
        if(act1 == 0) {
            query = "SELECT A.id,A.data,A.ilosc,B.nazwa,C.imie || ' ' || C.nazwisko , D.nazwa FROM Karmienie A JOIN Zwierzeta B ON A.zwierzeta_id = B.id JOIN PRACOWNICY C ON A.pracownicy_id = C.id JOIN Pokarm D ON A.pokarm_id = D.id";
        }
        else if(act1 == 1){
            query = "SELECT A.id,A.data,A.ilosc,B.nazwa,C.imie || ' ' || C.nazwisko , D.nazwa FROM Karmienie A JOIN Zwierzeta B ON A.zwierzeta_id = B.id JOIN PRACOWNICY C ON A.pracownicy_id = C.id JOIN Pokarm D ON A.pokarm_id = D.id WHERE D.id = ?";
        }
        else if(act1 == 2){
            query = "SELECT A.id,A.data,A.ilosc,B.nazwa,C.imie || ' ' || C.nazwisko , D.nazwa FROM Karmienie A JOIN Zwierzeta B ON A.zwierzeta_id = B.id JOIN PRACOWNICY C ON A.pracownicy_id = C.id JOIN Pokarm D ON A.pokarm_id = D.id WHERE B.id = ? ";
        }
        else if(act1 == 3){
            query = "SELECT A.id,A.data,A.ilosc,B.nazwa,C.imie || ' ' || C.nazwisko , D.nazwa FROM Karmienie A JOIN Zwierzeta B ON A.zwierzeta_id = B.id JOIN PRACOWNICY C ON A.pracownicy_id = C.id JOIN Pokarm D ON A.pokarm_id = D.id WHERE C.id = ?";
        }
        else if(act1 == 4){
            query = "SELECT A.id,A.data,A.ilosc,B.nazwa,C.imie || ' ' || C.nazwisko , D.nazwa FROM Karmienie A JOIN Zwierzeta B ON A.zwierzeta_id = B.id JOIN PRACOWNICY C ON A.pracownicy_id = C.id JOIN Pokarm D ON A.pokarm_id = D.id WHERE A.ilosc >= ?";
        }
        else if(act1 == 5){
            query = "SELECT A.id,A.data,A.ilosc,B.nazwa,C.imie || ' ' || C.nazwisko , D.nazwa FROM Karmienie A JOIN Zwierzeta B ON A.zwierzeta_id = B.id JOIN PRACOWNICY C ON A.pracownicy_id = C.id JOIN Pokarm D ON A.pokarm_id = D.id WHERE A.ilosc <= ?";
        }
        else if(act1 == 6){
            query = "SELECT A.id,A.data,A.ilosc,B.nazwa,C.imie || ' ' || C.nazwisko , D.nazwa FROM Karmienie A JOIN Zwierzeta B ON A.zwierzeta_id = B.id JOIN PRACOWNICY C ON A.pracownicy_id = C.id JOIN Pokarm D ON A.pokarm_id = D.id WHERE A.data >= ?";
        }
        else if(act1 == 7) {
            query = "SELECT A.id,A.data,A.ilosc,B.nazwa,C.imie || ' ' || C.nazwisko , D.nazwa FROM Karmienie A JOIN Zwierzeta B ON A.zwierzeta_id = B.id JOIN PRACOWNICY C ON A.pracownicy_id = C.id JOIN Pokarm D ON A.pokarm_id = D.id WHERE A.data <= ?";
        }
        if(act1 != 0 && act2 != 0 && act3 != 0 && !t2.isEmpty()){
            if(act2 == 1){
                query += " OR ";
            }
            else{
                query += " AND ";
            }
            if(act3 == 1){
                query += "D.id = ?";
            }
            else if(act3 == 2){
                query += "B.id = ?";
            }
            else if(act3 == 3){
                query += "C.id = ?";
            }
            else if(act3 == 4){
                query += "A.ilosc >= ?";
            }
            else if(act3 == 5){
                query += "A.ilosc <= ?";
            }
            else if(act3 == 6){
                query += "A.data >= ?";
            }
            else if(act3 == 7){
                query += "A.data <= ?";
            }
            else if(act3 == 8){
                query += "data_zatrudnienia >= ?";
            }
            else if(act3 == 9){
                query += "data_zatrudnienia <= ?";
            }
        }
        PreparedStatement prsm = con.prepareStatement(query);
        if(act1 != 0 && !t1.isEmpty()){
            if(act1 == 1 || act1 == 2 || act1 == 3 || act1 == 4 || act1 == 5 ){
                prsm.setInt(1,Integer.valueOf(t1));
            }
            if(act1 == 6 || act1 == 7){
                Date date = Date.valueOf(t1);
                prsm.setDate(1,date);
            }
            if((act2 != 0) && (act3 != 0) && !t2.isEmpty()) {
                if(act3 == 1 || act3 == 2 || act3 == 3 || act3 == 4 || act3 == 5 ){
                    prsm.setInt(1,Integer.valueOf(t2));
                }
                if(act3 == 6 || act3 == 7){
                    Date date = Date.valueOf(t2);
                    prsm.setDate(1,date);
                }
            }
        }
        rsfeed = prsm.executeQuery();
        while (rsfeed.next()) {
            feed = new Feed(rsfeed.getInt(1), rsfeed.getDate(2), rsfeed.getInt(3), rsfeed.getString(4), rsfeed.getString(5), rsfeed.getString(6));
            tabledisplayfeed.getItems().add(feed);
        }
    }
}
