package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class AddCatWalks {
    @FXML AnchorPane addcatwalkscontent;
    @FXML TextField addcatwalksname;
    @FXML TextField addcatwalkstype;
    @FXML ChoiceBox addcatwalkspavilon;
    @FXML Text addcatwalkstext;
    public void initialize() throws SQLException {
        AddCatWalksContent();
    }
    public void backtomenu(javafx.scene.input.MouseEvent event) throws IOException {
        addcatwalkscontent.getChildren().clear();
        AnchorPane catwalksmenu = FXMLLoader.load(getClass().getResource("catwalksmenu.fxml"));
        addcatwalkscontent.getChildren().setAll(catwalksmenu);
    }
    /**
     Funkcja ustawiajaca kontrolki w oknie dodania wybiegow
     */
    public void AddCatWalksContent() throws SQLException {
        addcatwalkspavilon.setValue(null);
        addcatwalksname.setText("");
        addcatwalkstext.setText("");
        addcatwalkstype.setText("");
        Connection con = null;
        con = Connect.getConnection();
        PreparedStatement prsms = con.prepareStatement("SELECT id,nazwa FROM Pawilony");
        ResultSet catwalks = prsms.executeQuery();
        while (catwalks.next()) {
            addcatwalkspavilon.getItems().add(catwalks.getInt(1) + ". " + catwalks.getString(2));
        }
    }
    /**
     Funkcja dodajaca wybiegi
     */
    public void AddCatWalks() throws SQLException, ClassNotFoundException {
        if(!addcatwalksname.getText().isEmpty() && !addcatwalkstype.getText().isEmpty() &&
                !addcatwalkspavilon.getValue().toString().equals("-"))
        {
            Connection con = null;
            con = Connect.getConnection();
            PreparedStatement prsm = con.prepareStatement("INSERT INTO Wybiegi VALUES (?,?,?,?)");
            prsm.setInt(1,1);
            prsm.setString(2,addcatwalksname.getText());
            prsm.setString(3, addcatwalkstype.getText());
            String t = (String) addcatwalkspavilon.getSelectionModel().getSelectedItem();
            if (!"-".equals(t) && t != null) {
                prsm.setInt(4, Integer.parseInt(t.split("\\.")[0]));
            }
            try {
                prsm.executeQuery();
                addcatwalkstext.setText("Dodano Wybieg");
                addcatwalkstype.setText("");
                addcatwalksname.setText("");
                addcatwalkspavilon.setValue(null);
            }
            catch(SQLException e) {
                addcatwalkstext.setText("Wprowadzono błędne dane");

            }
        }
        else{
            addcatwalkstext.setText("Uzupelnij wszystkie dane");
        }
    }
    /**
     Obsluga nacisniecia przycisku dodania wybiegu
     */
    public void pressAddCatWalks(ActionEvent event) throws SQLException, ClassNotFoundException, ParseException {
        AddCatWalks();
    }

}
