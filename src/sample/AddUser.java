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
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Timer;

public class AddUser {
@FXML AnchorPane addusercontent;
@FXML  TextField addusername;
@FXML TextField addusersname;
@FXML ChoiceBox addusersex;
@FXML TextField adduserphone;
@FXML TextField addusersalary;
@FXML TextField adduserposition;
@FXML DatePicker adduserdate;
@FXML Button adduserbutton;
@FXML Text addusertext;
    public void initialize(){
        AddUserContent();
    }
    public void backtomenu(javafx.scene.input.MouseEvent event) throws IOException {
        addusercontent.getChildren().clear();
        AnchorPane usermenu = FXMLLoader.load(getClass().getResource("usermenu.fxml"));
        addusercontent.getChildren().setAll(usermenu);
    }
    public void AddUserContent(){
        addusertext.setText("");
        adduserdate.setValue(null);
        addusername.setText("");
        adduserphone.setText("");
        adduserposition.setText("");
        addusersalary.setText("");
        addusersname.setText("");
        addusersex.getItems().add("M");
        addusersex.setValue("M");
        addusersex.getItems().add("K");
    }

    /**
     Funkcja dodajaca pracownika
     */
    public void AddUser()throws SQLException, ClassNotFoundException {
        if(!addusername.getText().isEmpty() && !addusersname.getText().isEmpty() &&
                !adduserphone.getText().isEmpty() && !addusersex.getValue().toString().equals("-") &&
                !addusersalary.getText().isEmpty() && !adduserposition.getText().isEmpty() &
                !(adduserdate.getEditor().getText().isEmpty()))
        {
            Connection con = null;
            con = Connect.getConnection();
            PreparedStatement prsm = con.prepareStatement("INSERT INTO Pracownicy VALUES (?,?,?,?,?,?,?,?)");
            prsm.setInt(1,1);
            prsm.setString(2,addusername.getText());
            prsm.setString(3,addusersname.getText());
            prsm.setString(4,adduserphone.getText());
            prsm.setString(5, (String) addusersex.getSelectionModel().getSelectedItem());
            prsm.setString(6,addusersalary.getText());
            prsm.setString(7,adduserposition.getText());
            prsm.setDate(8, Date.valueOf(adduserdate.getValue()));
            try {
                prsm.executeQuery();
                addusername.setText("");
                addusersname.setText("");
                adduserphone.setText("");
                addusersalary.setText("");
                adduserposition.setText("");
                adduserdate.setValue(null);
                addusertext.setText("Dodano pracownika");
                Timer t = new java.util.Timer();
                t.schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                // your code here
                                // close the thread
                                addusertext.setText("");
                            }
                        },
                        2000
                );
            }
            catch(SQLException e) {
                if(e.getErrorCode() == 20000){
                    addusertext.setText("Wprowadzono niepoprawny numer telefonu");
                }
                else{
                    addusertext.setText("Wprowadzono błędne dane");
                }
            }
        }
        else{
            addusertext.setText("Uzupelnij wszystkie dane");
        }
    }

    /**
     Obsluga nacisniecia przycisku dodania pracownika
     */
    public void pressAddUser(ActionEvent event) throws SQLException, ClassNotFoundException {
        AddUser();
    }
}
