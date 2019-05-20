package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UpdateUser {
@FXML AnchorPane editusercontent;
@FXML ChoiceBox updateuserselect;
@FXML TextField updateusername;
@FXML TextField updateusersname;
@FXML ChoiceBox updateusersex;
@FXML TextField updateuserphone;
@FXML TextField updateusersalary;
@FXML DatePicker updateuserdate;
@FXML TextField updateuserposition;
@FXML Text updateusertext;
    public void initialize() throws SQLException {
        UpdateUserContent();
    }
    public void backtomenu(javafx.scene.input.MouseEvent event) throws IOException {
        editusercontent.getChildren().clear();
        AnchorPane usermenu = FXMLLoader.load(getClass().getResource("usermenu.fxml"));
        editusercontent.getChildren().setAll(usermenu);
    }
    /**
     Ustawienie kontrolek w oknie edycji pracownika
     */
    public void UpdateUserContent() throws SQLException {
        if(updateusersex.getItems().isEmpty()) {
            updateusersex.getItems().add("Mężczyzna");
            updateusersex.getItems().add("Kobieta");
        }
        updateusertext.setText("");
        Connection con = null;
        con = Connect.getConnection();
        PreparedStatement psrm = con.prepareStatement("SELECT id,imie,nazwisko FROM Pracownicy");
        ResultSet rsuserselect =  psrm.executeQuery();
        updateuserselect.getItems().clear();
        updateuserselect.getItems().add("-");
        updateuserselect.setValue("-");
        while (rsuserselect.next()) {
            updateuserselect.getItems().add(rsuserselect.getInt(1) + ". imie: "+ rsuserselect.getString(2) + " nazwisko : " + rsuserselect.getString(3));
        }
        updateuserselect.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldV, newV) -> {

                    try {
                        String t = (String)updateuserselect.getSelectionModel().getSelectedItem();
                        if(!"-".equals(t) && t != null) {
                            String[] id = t.split("\\.");
                            try {
                                UpdateUserSelection(Integer.valueOf(id[0]));
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            UpdateUserSelection(0);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
    }

    /**
     ustawienie danych po wybraniu pracownika
     */
    public void UpdateUserSelection(int n) throws SQLException {
        if(n != 0) {
            Connection con = null;
            con = Connect.getConnection();
            PreparedStatement psrm = con.prepareStatement("SELECT * FROM Pracownicy WHERE id = ?");
            psrm.setInt(1,n);
            ResultSet rsuserselected = psrm.executeQuery();
            rsuserselected.next();
            updateusername.setText(rsuserselected.getString(3));
            updateusersname.setText(rsuserselected.getString(2));
            updateuserphone.setText(rsuserselected.getString(4));
            if(rsuserselected.getString(5).equals("M")){
                updateusersex.setValue("Mężczyzna");
            }
            else{
                updateusersex.setValue("Kobieta");
            }
            updateusersalary.setText(rsuserselected.getString(6));
            updateuserposition.setText(rsuserselected.getString(7));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            Date d = rsuserselected.getDate(8);
            LocalDate localDate = LocalDate.parse(d.toString() , formatter);
            updateuserdate.setValue(localDate);
        }
        else{
            updateusername.setText("");
            updateusersname.setText("");
            updateuserphone.setText("");
            updateusersex.setValue("");
            updateusersalary.setText("");
            updateuserposition.setText("");
            updateuserdate.setValue(null);
        }

    }

    /**
     obsluga nacisniecia przycisku edycji pracownika
     */
    public void pressUpdateUser(ActionEvent event)throws SQLException, ClassNotFoundException {
        updateusertext.setText("");
        if(!updateusername.getText().isEmpty() && !updateusersname.getText().isEmpty() &&
                !updateuserphone.getText().isEmpty() && !updateusersex.getValue().toString().equals("-") &&
                !updateusersalary.getText().isEmpty() && !updateuserposition.getText().isEmpty() &
                !(updateuserdate.getEditor().getText().isEmpty()))
        {
            Connection con = null;
            con = Connect.getConnection();
            PreparedStatement psrm = con.prepareStatement("UPDATE Pracownicy SET imie = ? , nazwisko =  ? , telefon = ? , plec = ? , pensja = ? , stanowisko = ? , data_zatrudnienia = ? WHERE id = ?");
            psrm.setString(1,updateusersname.getText());
            psrm.setString(2,updateusername.getText());
            psrm.setString(3,updateuserphone.getText());
            if(updateusersex.getSelectionModel().getSelectedItem().equals("Mężczyzna")){
                psrm.setString(4,"M");
            }
            else{
                psrm.setString(4,"K");
            }
            psrm.setString(5,updateusersalary.getText());
            psrm.setString(6,updateuserposition.getText());
            psrm.setDate(7, Date.valueOf(updateuserdate.getValue()));
            String t = (String)updateuserselect.getSelectionModel().getSelectedItem();
            if(!"-".equals(t) && t != null) {
                String[] id = t.split("\\.");
                try {
                    psrm.setInt(8, Integer.valueOf(id[0]));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            try {
                psrm.executeUpdate();
                UpdateUserContent();
                updateusertext.setText("Edytowano pracownika");
                updateusername.setText("");
                updateusersname.setText("");
                updateuserphone.setText("");
                updateusersalary.setText("");
                updateuserposition.setText("");
                updateuserselect.setValue("-");
                updateuserdate.setValue(null);
            }
            catch(SQLException e) {
                if(e.getErrorCode() == 20000){
                    updateusertext.setText("Wprowadzono niepoprawny numer telefonu");
                }
                else{
                    updateusertext.setText("Wprowadzono błędne dane");
                }
            }
        }
        else{
            updateusertext.setText("Uzupelnij wszystkie dane");
        }
    }
}
