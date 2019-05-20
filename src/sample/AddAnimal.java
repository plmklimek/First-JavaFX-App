package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;

public class AddAnimal {
    @FXML AnchorPane addanimalcontent;
    @FXML TextField addanimalname;
    @FXML ChoiceBox addanimalsex;
    @FXML TextField addanimalgroup;
    @FXML ChoiceBox addanimalcatwalks;
    @FXML DatePicker addanimaldate;
    @FXML Button addanimalbutton;
    @FXML Text addanimaltext;
    public void initialize() throws SQLException {
        AddAnimalContent();
    }

    public void backtomenu(javafx.scene.input.MouseEvent event) throws IOException {
        addanimalcontent.getChildren().clear();
        AnchorPane animalmenu = FXMLLoader.load(getClass().getResource("animalmenu.fxml"));
        addanimalcontent.getChildren().setAll(animalmenu);
    }
    /**
     Funkcja ustawiajaca kontrolki w oknie dodania zwierzecia
     */
    public void AddAnimalContent() throws SQLException {
        addanimalcatwalks.setValue(null);
        addanimaldate.setValue(null);
        addanimalgroup.setText("");
        addanimalname.setText("");
        addanimaltext.setText("");
        Connection con = null;
        con = Connect.getConnection();
        addanimalsex.getItems().add("M");
        addanimalsex.setValue("M");
        addanimalsex.getItems().add("K");
        PreparedStatement prsms = con.prepareStatement("SELECT id,nazwa FROM Wybiegi");
        ResultSet rsanimal = prsms.executeQuery();
        while (rsanimal.next()) {
            addanimalcatwalks.getItems().add(rsanimal.getInt(1)  + ". " +rsanimal.getString(2));
        }
    }
    /**
     Funkcja dodajaca zwierze
     */
    public void AddAnimal() throws SQLException, ClassNotFoundException {
        if(!addanimalname.getText().isEmpty() && !addanimalgroup.getText().isEmpty() &&
                !addanimalcatwalks.getValue().toString().equals("-")  && !addanimalsex.getValue().toString().equals("-") &&
                !(addanimaldate.getEditor().getText().isEmpty()))
        {
            Connection con = null;
            con = Connect.getConnection();
            PreparedStatement prsm = con.prepareStatement("INSERT INTO Zwierzeta VALUES (?,?,?,?,?,?)");
            prsm.setInt(1,1);
            prsm.setString(2,addanimalname.getText());
            prsm.setString(3, (String) addanimalsex.getSelectionModel().getSelectedItem());
            prsm.setString(4,addanimalgroup.getText());
            prsm.setDate(5, Date.valueOf(addanimaldate.getValue()));
            String[] id = addanimalcatwalks.getSelectionModel().getSelectedItem().toString().split("\\.");
            prsm.setInt(6,Integer.valueOf(id[0]));
            try {
                prsm.executeQuery();
                addanimaltext.setText("Dodano zwierze");
                addanimalgroup.setText("");
                addanimalname.setText("");
                addanimalsex.setValue(null);
                addanimalcatwalks.setValue(null);
                addanimaldate.setValue(null);
            }
            catch(SQLException e) {
                addanimaltext.setText("Wprowadzono błędne dane");

            }
        }
        else{
            addanimaltext.setText("Uzupelnij wszystkie dane");
        }
    }
    /**
     Obsluga nacisniecia przycisku dodania zwierzecia
     */
    public void pressAddAnimal(ActionEvent event) throws SQLException, ClassNotFoundException, ParseException {
        AddAnimal();
    }
}
