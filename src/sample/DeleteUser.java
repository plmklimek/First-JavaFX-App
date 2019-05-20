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

public class DeleteUser {
@FXML AnchorPane deleteusercontent;
@FXML ChoiceBox deleteuserselect;
@FXML TextField deleteusername;
@FXML TextField deleteusersname;
@FXML TextField deleteuserphone;
@FXML TextField deleteusersex;
@FXML TextField deleteusersalary;
@FXML TextField deleteuserposition;
@FXML DatePicker deleteuserdate;
@FXML Text deleteusertext;
    public void initialize() throws SQLException {
        DeleteUserContent();
    }
    public void backtomenu(javafx.scene.input.MouseEvent event) throws IOException {
        deleteusercontent.getChildren().clear();
        AnchorPane usermenu = FXMLLoader.load(getClass().getResource("usermenu.fxml"));
        deleteusercontent.getChildren().setAll(usermenu);
    }
    /**
     ustawienie kontrolek w oknie usuniecia pracownika
     */
    public void DeleteUserContent() throws SQLException {
        deleteuserdate.setValue(null);
        deleteusername.setText("");
        deleteuserphone.setText("");
        deleteuserposition.setText("");
        deleteusersalary.setText("");
        deleteusersname.setText("");
        deleteusertext.setText("");
        Connection con = null;
        con = Connect.getConnection();
        PreparedStatement psrm = con.prepareStatement("SELECT id,imie,nazwisko FROM Pracownicy");
        ResultSet rsuserdelete = psrm.executeQuery();
        deleteuserselect.getItems().clear();
        deleteuserselect.getItems().add("-");
        deleteuserselect.setValue("-");
        while (rsuserdelete.next()) {
            deleteuserselect.getItems().add(rsuserdelete.getInt(1) + ". imie: "+ rsuserdelete.getString(2) + " nazwisko : " + rsuserdelete.getString(3));
        }
        deleteuserselect.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldV, newV) -> {
                    try {
                        String t = (String)deleteuserselect.getSelectionModel().getSelectedItem();
                        if(!"-".equals(t) && t != null) {
                            String[] id = t.split("\\.");
                            try {
                                DeleteUserSelection(Integer.valueOf(id[0]));
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            DeleteUserSelection(0);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }



                });
    }

    /**
     wyswietlenie danych wybranego pracownika
     */
    public void DeleteUserSelection(int n) throws SQLException {
        if(n != 0) {
            Connection con = null;
            con = Connect.getConnection();
            PreparedStatement prsm = con.prepareStatement("SELECT * FROM Pracownicy WHERE id = ?");
            prsm.setInt(1, n);
            ResultSet rsuserselected = prsm.executeQuery();
            rsuserselected.next();
            deleteusername.setText(rsuserselected.getString(2));
            deleteusersname.setText(rsuserselected.getString(3));
            deleteuserphone.setText(rsuserselected.getString(4));
            if (!rsuserselected.getString(5).isEmpty()){
                if (rsuserselected.getString(5).equals("M")) {
                    deleteusersex.setText("Mężczyzna");
                } else {
                    deleteusersex.setText("Kobieta");
                }
            }
            deleteusersalary.setText(rsuserselected.getString(6));
            deleteuserposition.setText(rsuserselected.getString(7));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            Date d = rsuserselected.getDate(8);
            LocalDate localDate = LocalDate.parse(d.toString() , formatter);
            deleteuserdate.setValue(localDate);
        }
        else{
            deleteusername.setText("");
            deleteusersname.setText("");
            deleteuserphone.setText("");
            deleteusersex.setText("");
            deleteusersalary.setText("");
            deleteuserposition.setText("");
            deleteuserdate.setValue(null);


        }

    }

    /**
     obsluga nacisniecia przycisku usuniecia pracownika
     */
    public void pressDeleteUser(ActionEvent event)throws SQLException {

        Connection con = null;
        con = Connect.getConnection();
        PreparedStatement psrm = con.prepareStatement("DELETE FROM Pracownicy WHERE id = ?");
        String t = (String)deleteuserselect.getSelectionModel().getSelectedItem();
        if(!"-".equals(t) && t != null) {
            String[] id = t.split("\\.");
            try {
                psrm.setInt(1,Integer.valueOf(id[0]));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            psrm.executeQuery();
            DeleteUserContent();
            deleteusertext.setText("Usunieto pracownika");
            deleteusername.setText("");
            deleteusersname.setText("");
            deleteuserphone.setText("");
            deleteusersex.setText("");
            deleteusersalary.setText("");
            deleteuserposition.setText("");
            deleteuserselect.setValue("-");
            deleteuserdate.setValue(null);
        } catch (SQLException e) {
            deleteusertext.setText(e.getMessage());
        }
    }
}
