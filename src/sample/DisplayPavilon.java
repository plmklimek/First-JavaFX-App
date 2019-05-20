package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class DisplayPavilon {
    @FXML AnchorPane displaypaviloncontent;
    @FXML TableView tabledisplaypavilon;
    @FXML ChoiceBox pavilondisplayfiltr1;
    @FXML ChoiceBox pavilondisplayfiltr2;
    @FXML ChoiceBox pavilondisplayfiltr3;
    @FXML TextField pavilondisplaytext1;
    @FXML TextField pavilondisplaytext2;
    public void initialize() throws SQLException {
        TablePavilon();
    }
    public void backtomenu(javafx.scene.input.MouseEvent event) throws IOException {
        displaypaviloncontent.getChildren().clear();
        AnchorPane pavilonmenu = FXMLLoader.load(getClass().getResource("pavilonmenu.fxml"));
        displaypaviloncontent.getChildren().setAll(pavilonmenu);
    }

    /**
     Funkcja tworzaca strukture tabeli w oknie wyswietlania tabeli pawilonu
     */
    public void TablePavilon() throws SQLException {
        Connection con = null;
        con = Connect.getConnection();
        TableColumn idPavilon = new TableColumn("Id");
        idPavilon.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn namePavilon = new TableColumn("Nazwa");
        namePavilon.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn descPavilon = new TableColumn("Opis");
        descPavilon.setCellValueFactory(new PropertyValueFactory<>("desc"));
        tabledisplaypavilon.getColumns().addAll(idPavilon, namePavilon, descPavilon);
        Pavilon pavilon = null;
        PreparedStatement prsm = con.prepareStatement("SELECT * FROM Pawilony");
        ResultSet rspavilon = prsm.executeQuery();
        while (rspavilon.next()) {
            pavilon = new Pavilon(rspavilon.getInt(1), rspavilon.getString(2), rspavilon.getString(3));
            tabledisplaypavilon.getItems().add(pavilon);

        }
        InsertPavilon(0,0, 0,"", "");
        PavilonFiltr();
    }
    /**
     Funkcja ustawiajace kontrolki do filtrowania tabeli pawilonu
     */
    public void PavilonFiltr(){
        pavilondisplayfiltr1.getItems().add("-");
        pavilondisplayfiltr1.setValue("-");
        pavilondisplayfiltr1.getItems().add("nazwa");
        pavilondisplayfiltr1.getItems().add("opis");

        pavilondisplayfiltr2.getItems().add("-");
        pavilondisplayfiltr2.setValue("-");
        pavilondisplayfiltr2.getItems().add("lub");
        pavilondisplayfiltr2.getItems().add("i");

        pavilondisplayfiltr3.getItems().add("-");
        pavilondisplayfiltr3.setValue("-");
        pavilondisplayfiltr3.getItems().add("nazwa");
        pavilondisplayfiltr3.getItems().add("opis");

    }
    /**
     Funkcja obslugujaca nacisniecie przycisku filtrowania danych  z tabeli
     */
    public void pressPavilonFiltrButton(ActionEvent event) throws SQLException, ClassNotFoundException, ParseException {
        InsertPavilon(pavilondisplayfiltr1.getSelectionModel().getSelectedIndex(),pavilondisplayfiltr2.getSelectionModel().getSelectedIndex(),pavilondisplayfiltr3.getSelectionModel().getSelectedIndex(),pavilondisplaytext1.getText(),pavilondisplaytext2.getText());
    }
    /**

     Funkcja dodajaca filtrowane dane do tabelis
     */
    public void InsertPavilon(int act1 , int act2, int act3, String t1 , String t2) throws SQLException{
        tabledisplaypavilon.getItems().clear();
        Connection con = null;
        con = Connect.getConnection();
        Pavilon pavilon = null;
        ResultSet rspavilon;
        String query = "";
        if((act1 == 0 || t1.isEmpty())) {
            query = "select * from Pawilony";
        }
        else if(act1 == 1){
            query = "select * from Pawilony WHERE nazwa LIKE ?";
            t1 = "%" + t1 + "%";
        }
        else if(act1 == 2){
            query = "select * from Pawilony WHERE opis LIKE ?";
            t1 = "%" + t1 + "%";
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
                query += "opis LIKE ?";
                t2 = "%" + t2 + "%";
            }
        }
        PreparedStatement prsm = con.prepareStatement(query);
        if(act1 != 0 && !t1.isEmpty()){
            prsm.setString(1,t1);
            if((act2 != 0) && (act3 != 0) && !t2.isEmpty()) {
                prsm.setString(2,t2);
            }
        }
        rspavilon = prsm.executeQuery();
        while (rspavilon.next()) {
            pavilon = new Pavilon(rspavilon.getInt(1), rspavilon.getString(2), rspavilon.getString(3));
            tabledisplaypavilon.getItems().add(pavilon);
        }
    }

}
