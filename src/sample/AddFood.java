package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddFood {
    @FXML TextField addfoodname;
    @FXML TextField addfoodilosc;
    @FXML Button addfoodbutton;
    @FXML Text addfoodtext;
    @FXML ChoiceBox addfoodchoice;
    @FXML ChoiceBox addfoodexist;
    @FXML AnchorPane addfoodcontent;
    public void initialize() throws SQLException {
        AddFoodContent();
    }
    public void backtomenu(javafx.scene.input.MouseEvent event) throws IOException {
        addfoodcontent.getChildren().clear();
        AnchorPane foodmenu = FXMLLoader.load(getClass().getResource("foodmenu.fxml"));
        addfoodcontent.getChildren().setAll(foodmenu);
    }
    /**
     Funkcja ustawiajaca kontrolki w oknie dodania jedzenia
     */
    public void AddFoodContent() throws SQLException {
        addfoodname.setText("");
        addfoodilosc.setText("");
        addfoodtext.setText("");
        addfoodchoice.getItems().add("nowy");
        addfoodchoice.setValue("nowy");
        addfoodchoice.getItems().add("do istniejacego");
        Connection con = null;
        con = Connect.getConnection();
        PreparedStatement prsm = con.prepareStatement("SELECT nazwa FROM Pokarm");
        ResultSet rsfood = prsm.executeQuery();
        addfoodchoice.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldV, newV) -> {
                    if(addfoodchoice.getSelectionModel().getSelectedItem().equals("nowy")){
                        addfoodname.setVisible(true);
                        addfoodexist.setVisible(false);
                    }
                    else{
                        addfoodname.setVisible(false);
                        addfoodexist.setVisible(true);
                        while(true){
                            try {
                                if (!rsfood.next()) break;
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            try {
                                addfoodexist.getItems().add(rsfood.getString(1));
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }
    /**
     Funkcja dodajaca jedzenie
     */
    public void AddFood()throws SQLException, ClassNotFoundException {
        if(!addfoodilosc.getText().isEmpty() && (addfoodchoice.getSelectionModel().getSelectedIndex() != 0 || !addfoodname.getText().isEmpty()))
        {
            PreparedStatement prsm = null;
            Connection con = null;
            con = Connect.getConnection();
            if(addfoodchoice.getSelectionModel().getSelectedIndex() == 0) {
                prsm = con.prepareStatement("INSERT INTO Pokarm VALUES (?,?,?)");
                prsm.setInt(1,1);
                prsm.setString(2, addfoodname.getText());
                prsm.setInt(3,Integer.valueOf(addfoodilosc.getText()));
            }
            else{
                prsm = con.prepareStatement("UPDATE POKARM SET ilosc = ilosc + ? WHERE nazwa = ?");
                prsm.setInt(1,Integer.valueOf(addfoodilosc.getText()));
                prsm.setString(2,addfoodexist.getSelectionModel().getSelectedItem().toString());
            }
            try {
                prsm.executeQuery();
                addfoodtext.setText("Dodano pokarm");
                addfoodname.setText("");
                addfoodilosc.setText("");
            }
            catch(SQLException e) {
                addfoodtext.setText("Wprowadzono błędne dane");
            }
        }
        else{
            addfoodtext.setText("Uzupelnij wszystkie dane");
        }
    }
    /**
     Obsluga nacisniecia przycisku dodania pracownika
     */
    public void pressAddFood(ActionEvent event) throws SQLException, ClassNotFoundException {
        AddFood();
    }
}
