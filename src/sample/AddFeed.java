package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.*;

public class AddFeed {
    @FXML AnchorPane addfeedcontent;
    @FXML TextField addfeedilosc;
    @FXML ChoiceBox addfeedanimal;
    @FXML ChoiceBox addfeeduser;
    @FXML ChoiceBox addfeedfood;
    @FXML Text addfeedtext;
    public void initialize() throws SQLException {
        AddFeedContent();
    }
    public void backtomenu(javafx.scene.input.MouseEvent event) throws IOException {
        addfeedcontent.getChildren().clear();
        AnchorPane feedmenu = FXMLLoader.load(getClass().getResource("feedmenu.fxml"));
        addfeedcontent.getChildren().setAll(feedmenu);
    }
    /**
     Funkcja ustawiajaca kontrolki w oknie dodania karmienia
     */
    public void AddFeedContent() throws SQLException {
        addfeedanimal.setValue(null);
        addfeedfood.setValue(null);
        addfeedtext.setText("");
        addfeeduser.setValue(null);
        addfeedilosc.setText("");
        Connection con = null;
        con = Connect.getConnection();
        PreparedStatement psrmu = con.prepareStatement("SELECT id,imie,nazwisko FROM Pracownicy WHERE stanowisko = ?");
        psrmu.setString(1,"Opiekun");
        PreparedStatement psrma = con.prepareStatement("SELECT A.id,A.nazwa,B.nazwa FROM Zwierzeta A JOIN Wybiegi B ON A.wybiegi_id = B.id");
        PreparedStatement psrmf = con.prepareStatement("SELECT * FROM Pokarm");
        PreparedStatement psrmc = con.prepareStatement("SELECT A.id,B.data FROM Zwierzeta A JOIN Karmienie B ON B.ZWIERZETA_ID = A.ID WHERE TO_DATE(B.data, 'YY/MM/DD') = TO_DATE(sysdate, 'YY/MM/DD') AND A.ID = ? ");
        ResultSet rsuser = psrmu.executeQuery();
        ResultSet rsanimal = psrma.executeQuery();
        ResultSet rsfood = psrmf.executeQuery();
        addfeedfood.getItems().clear();
        addfeedilosc.setText("");
        addfeedanimal.getItems().clear();
        addfeeduser.getItems().clear();
        while(rsuser.next()){
            addfeeduser.getItems().add(rsuser.getInt(1) + ". " + rsuser.getString(2) + " " + rsuser.getString(3));
        }
        while(rsanimal.next()){
            psrmc.setInt(1,rsanimal.getInt(1));
            ResultSet rscheck = psrmc.executeQuery();
            if(rscheck.next() == false) {
                addfeedanimal.getItems().add(rsanimal.getInt(1) + ". " + rsanimal.getString(2) + " Wybiegi : " + rsanimal.getString(3));
            }
            else{
                addfeedanimal.getItems().add(rsanimal.getInt(1) + ". " + rsanimal.getString(2) + " Wybiegi : " + rsanimal.getString(3) + "  NAKARMIONY");
            }
        }
        while(rsfood.next()){
            addfeedfood.getItems().add(rsfood.getInt(1) + ". " + rsfood.getString(2) + " ilosc:" + rsfood.getInt(3));
        }
    }
    /**
     Funkcja dodajaca karmienie
     */
    public void AddFeed()throws SQLException, ClassNotFoundException {
        if(!addfeedilosc.getText().isEmpty()
                && addfeedfood.getSelectionModel().getSelectedIndex() != -1
                && addfeedanimal.getSelectionModel().getSelectedIndex() != -1
                && addfeeduser.getSelectionModel().getSelectedIndex() != -1
        )
        {
            Connection con = null;
            con = Connect.getConnection();
            PreparedStatement prsm = con.prepareStatement("INSERT INTO Karmienie VALUES (?,?,?,?,?,?)");
            PreparedStatement daterow = con.prepareStatement("SELECT sysdate FROM dual");
            ResultSet dateresult = daterow.executeQuery();
            dateresult.next();
            Date date = dateresult.getDate(1);
            prsm.setInt(1,1);
            prsm.setDate(2, new java.sql.Date(date.getTime()));
            prsm.setInt(3,Integer.valueOf(addfeedilosc.getText()));
            String a = (String) addfeedanimal.getSelectionModel().getSelectedItem();
            if (!"-".equals(a) && a != null) {
                prsm.setInt(4,Integer.valueOf(a.split("\\.")[0]));
            }
            String u = (String) addfeeduser.getSelectionModel().getSelectedItem();
            if (!"-".equals(u) && u != null) {
                prsm.setInt(5,Integer.valueOf(u.split("\\.")[0]));
            }
            String f = (String) addfeedfood.getSelectionModel().getSelectedItem();
            if (!"-".equals(f) && f != null) {
                prsm.setInt(6,Integer.valueOf(f.split("\\.")[0]));
            }
            try {
                prsm.executeQuery();
                AddFeedContent();
                addfeedtext.setText("Dodano karmienie");
            }
            catch(SQLException e) {
                if(e.getErrorCode() == 20001){
                    addfeedtext.setText("Niewystarczajaca ilosc pokarmu");
                }
                else{
                    addfeedtext.setText("Wprowadzono błędne dane");
                }
            }
        }
        else{
            addfeedtext.setText("Uzupelnij wszystkie dane");
        }
    }
    /**
     Obsluga nacisniecia przycisku dodania karmienia
     */
    public void pressAddFeed(ActionEvent event) throws SQLException, ClassNotFoundException {
        AddFeed();
    }
}
