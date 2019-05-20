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

public class DisplayCatWalks {
    @FXML AnchorPane displaycatwalkscontent;
    @FXML TableView tabledisplaycatwalks;
    @FXML ChoiceBox catwalksdisplayfiltr1;
    @FXML ChoiceBox catwalksdisplayfiltr2;
    @FXML ChoiceBox catwalksdisplayfiltr3;
    @FXML TextField catwalksdisplaytext1;
    @FXML TextField catwalksdisplaytext2;
    @FXML ChoiceBox catwalkschoicepavilon2;
    @FXML ChoiceBox catwalkschoicepavilon1;
    public void initialize() throws SQLException {
        TableCatWalks();
    }
    public void backtomenu(javafx.scene.input.MouseEvent event) throws IOException {
        displaycatwalkscontent.getChildren().clear();
        AnchorPane catwalksmenu = FXMLLoader.load(getClass().getResource("catwalksmenu.fxml"));
        displaycatwalkscontent.getChildren().setAll(catwalksmenu);
    }
    /**
     Funkcja tworzaca strukture tabeli w oknie wyswietlania tabeli wybiegi
     */
    public void TableCatWalks() throws SQLException {
        catwalkschoicepavilon1.setVisible(false);
        catwalksdisplaytext1.setVisible(true);
        catwalksdisplaytext2.setVisible(true);
        catwalkschoicepavilon2.setVisible(false);
        Connection con = null;
        con = Connect.getConnection();
        TableColumn idCatWalks = new TableColumn("Id");
        idCatWalks.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn nameCatWalks = new TableColumn("Nazwa");
        nameCatWalks.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn typePavilon = new TableColumn("Typ");
        typePavilon.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn PavilonN = new TableColumn("Nazwa Pawilonu");
        PavilonN.setCellValueFactory(new PropertyValueFactory<>("pavilonN"));
        PavilonN.setMinWidth(600);
        tabledisplaycatwalks.getColumns().addAll(idCatWalks, nameCatWalks, typePavilon, PavilonN);
        CatWalks catwalks = null;
        PreparedStatement prsm = con.prepareStatement("SELECT A.ID,A.NAZWA,A.TYP,B.Nazwa FROM WYBIEGI A JOIN Pawilony B ON A.PAWILONY_ID = B.ID");
        ResultSet rscatwalks = prsm.executeQuery();
        while (rscatwalks.next()) {
            catwalks = new CatWalks(rscatwalks.getInt(1), rscatwalks.getString(2), rscatwalks.getString(3), rscatwalks.getString(4));
            tabledisplaycatwalks.getItems().add(catwalks);

        }
        InsertCatWalks(0,0, 0,"", "");
        CatWalksFiltr();
    }
    /**
     Funkcja ustawiajace kontrolki do filtrowania tabeli wybiegow
     */
    public void CatWalksFiltr() throws SQLException {
        catwalksdisplayfiltr1.getItems().add("-");
        catwalksdisplayfiltr1.setValue("-");
        catwalksdisplayfiltr1.getItems().add("nazwa");
        catwalksdisplayfiltr1.getItems().add("typ");
        catwalksdisplayfiltr1.getItems().add("pawilon");

        catwalksdisplayfiltr2.getItems().add("-");
        catwalksdisplayfiltr2.setValue("-");
        catwalksdisplayfiltr2.getItems().add("lub");
        catwalksdisplayfiltr2.getItems().add("i");

        catwalksdisplayfiltr3.getItems().add("-");
        catwalksdisplayfiltr3.setValue("-");
        catwalksdisplayfiltr3.getItems().add("nazwa");
        catwalksdisplayfiltr3.getItems().add("typ");
        catwalksdisplayfiltr3.getItems().add("pawilon");
        Connection con = null;
        con = Connect.getConnection();
        PreparedStatement prsm = con.prepareStatement("SELECT * FROM Pawilony");
        ResultSet rspavilon = (prsm.executeQuery());
        while (rspavilon.next()) {
            catwalkschoicepavilon1.getItems().add(rspavilon.getInt(1) + ". " + rspavilon.getString(2));
        }
        rspavilon = (prsm.executeQuery());
        while (rspavilon.next()) {
            catwalkschoicepavilon2.getItems().add(rspavilon.getInt(1) + ". " + rspavilon.getString(2));
        }
        catwalksdisplayfiltr1.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldV, newV) -> {
                    int t = catwalksdisplayfiltr1.getSelectionModel().getSelectedIndex();
                    if(t == 3){
                        catwalkschoicepavilon1.setVisible(true);
                        catwalksdisplaytext1.setVisible(false);
                    }
                    else{
                        catwalkschoicepavilon1.setVisible(false);
                        catwalksdisplaytext1.setVisible(true);
                    }
                });
        catwalksdisplayfiltr3.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldV, newV) -> {
                    int t = catwalksdisplayfiltr3.getSelectionModel().getSelectedIndex();
                    if(t == 3){
                        catwalkschoicepavilon2.setVisible(true);
                        catwalksdisplaytext2.setVisible(false);
                    }
                    else{
                        catwalkschoicepavilon2.setVisible(false);
                        catwalksdisplaytext2.setVisible(true);
                    }
                });
    }
    /**
     Funkcja obslugujaca nacisniecie przycisku filtrowania danych  z tabeli
     */
    public void pressCatWalksFiltrButton(ActionEvent event) throws SQLException, ClassNotFoundException, ParseException {
        int id1 = catwalksdisplayfiltr1.getSelectionModel().getSelectedIndex();
        int id2 = catwalksdisplayfiltr3.getSelectionModel().getSelectedIndex();
        Connection con = null;
        con = Connect.getConnection();
        String act1 = null;
        String act2 = null;
        if(id1 == 3){
            String t = (String) catwalkschoicepavilon1.getSelectionModel().getSelectedItem();
            if (!"-".equals(t) && t != null) {
                act1 = t.split("\\.")[0];
            }
        }
        else{
            act1 = catwalksdisplaytext1.getText();
        }
        if(id2 == 3){
            String t = (String) catwalkschoicepavilon2.getSelectionModel().getSelectedItem();
            if (!"-".equals(t) && t != null) {
                act2 = t.split("\\.")[0];
            }
        }
        else{
            act2 = catwalksdisplaytext2.getText();
        }
        InsertCatWalks(catwalksdisplayfiltr1.getSelectionModel().getSelectedIndex(), catwalksdisplayfiltr2.getSelectionModel().getSelectedIndex(), catwalksdisplayfiltr3.getSelectionModel().getSelectedIndex(), act1, act2);
    }
    /**

     Funkcja dodajaca filtrowane dane do tabelis
     */
    public void InsertCatWalks(int act1 , int act2, int act3, String t1 , String t2) throws SQLException{
        tabledisplaycatwalks.getItems().clear();
        Connection con = null;
        con = Connect.getConnection();
        CatWalks catWalks = null;
        ResultSet rscatwalks;
        String query = "";
        if((act1 == 0 || t1.isEmpty())) {
            query = "SELECT A.id , A.nazwa , A.typ , B.nazwa FROM Wybiegi A JOIN Pawilony B ON A.pawilony_id = B.id";
        }
        else if(act1 == 1){
            query = "SELECT A.id , A.nazwa , A.typ , B.nazwa FROM Wybiegi A JOIN Pawilony B ON A.pawilony_id = B.id WHERE A.nazwa LIKE ?";
            t1 = "%" + t1 + "%";
        }
        else if(act1 == 2){
            query = "SELECT A.id , A.nazwa , A.typ , B.nazwa FROM Wybiegi A JOIN Pawilony B ON A.pawilony_id = B.id WHERE A.typ LIKE ?";
            t1 = "%" + t1 + "%";
        }
        else if(act1 == 3){
            query = "SELECT A.id , A.nazwa , A.typ , B.nazwa FROM Wybiegi A JOIN Pawilony B ON A.pawilony_id = B.id WHERE A.pawilony_id = ?";
        }
        if(act1 != 0 && act2 != 0 && act3 != 0 && !t2.isEmpty()){
            if(act2 == 1){
                query += " OR ";
            }
            else{
                query += " AND ";
            }
            if(act3 == 1){
                query += "A.nazwa LIKE ?";
                t2 = "%" + t2 + "%";
            }
            else if(act3 == 2){
                query += "A.typ LIKE ?";
                t2 = "%" + t2 + "%";
            }
            else if(act3 == 3){
                query += "A.pawilony_id = ?";
            }
        }
        PreparedStatement prsm = con.prepareStatement(query);
        if(act1 != 0 && !t1.isEmpty()){
            if(act1 == 3){
                prsm.setInt(1, Integer.parseInt(t1));
            }
            else {
                prsm.setString(1, t1);
            }
            if((act2 != 0) && (act3 != 0) && !t2.isEmpty()) {
                if(act2 == 3){
                    prsm.setInt(2, Integer.parseInt(t2));
                }
                else {
                    prsm.setString(2, t2);
                }
            }
        }
        rscatwalks = prsm.executeQuery();
        while (rscatwalks.next()) {
            catWalks = new CatWalks(rscatwalks.getInt(1), rscatwalks.getString(2),rscatwalks.getString(3),rscatwalks.getString(4));
            tabledisplaycatwalks.getItems().add(catWalks);
        }
    }
}
