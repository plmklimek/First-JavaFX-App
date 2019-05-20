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

public class UpdateAnimal {
    @FXML ChoiceBox updateanimalselect;
    @FXML TextField updateanimalname;
    @FXML ChoiceBox updateanimalsex;
    @FXML TextField updateanimalgroup;
    @FXML ChoiceBox updateanimalcatwalks;
    @FXML DatePicker updateanimaldate;
    @FXML Text updateanimaltext;
    @FXML AnchorPane editanimalcontent;
    public void initialize() throws SQLException, ClassNotFoundException {
        UpdateAnimalContent();
    }
    public void backtomenu(javafx.scene.input.MouseEvent event) throws IOException {
        editanimalcontent.getChildren().clear();
        AnchorPane animalmenu = FXMLLoader.load(getClass().getResource("animalmenu.fxml"));
        editanimalcontent.getChildren().setAll(animalmenu);
    }
    /**
     Ustawienie kontrolek w oknie edycji zwierzecia
     */
    public void UpdateAnimalContent() throws SQLException, ClassNotFoundException {
        if(updateanimalsex.getItems().isEmpty()) {
            updateanimalsex.getItems().add("Samiec");
            updateanimalsex.getItems().add("Samica");
        }
        updateanimaltext.setText("");
        Connection con = null;
        con = Connect.getConnection();
        PreparedStatement psrm = con.prepareStatement("SELECT id,nazwa FROM Zwierzeta");
        ResultSet rsanimalselect = psrm.executeQuery();
        updateanimalselect.getItems().clear();
        updateanimalselect.getItems().add("-");
        updateanimalselect.setValue("-");
        while (rsanimalselect.next()) {
            updateanimalselect.getItems().add(rsanimalselect.getInt(1) + ". nazwa: "+ rsanimalselect.getString(2));
        }
        updateanimalselect.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldV, newV) -> {

                    try {
                        String t = (String)updateanimalselect.getSelectionModel().getSelectedItem();
                        if(!"-".equals(t) && t != null) {
                            String[] id = t.split("\\.");
                            try {
                                UpdateAnimalSelection(Integer.valueOf(id[0]));
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            UpdateAnimalSelection(0);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
    }
    /**
     ustawienie danych po wybraniu zwierzecia
     */
    public void UpdateAnimalSelection(int n) throws SQLException {
        if(n != 0) {
            Connection con = null;
            con = Connect.getConnection();
            PreparedStatement psrm = con.prepareStatement("SELECT * FROM Zwierzeta WHERE id = ?");
            psrm.setInt(1,n);
            ResultSet rsanimalselected = psrm.executeQuery();
            rsanimalselected.next();
            updateanimalname.setText(rsanimalselected.getString(2));
            updateanimalgroup.setText(rsanimalselected.getString(4));
            PreparedStatement psrmsms = con.prepareStatement("SELECT B.id,B.nazwa FROM Wybiegi B");
            ResultSet rsanimalselectededed = psrmsms.executeQuery();
            while(rsanimalselectededed.next()){
                updateanimalcatwalks.getItems().add(rsanimalselectededed.getInt(1) + ". nazwa: " + rsanimalselectededed.getString(2));
                String t = String.valueOf(rsanimalselectededed.getInt(1));
                if(!"-".equals(t) && t != null) {
                    String[] id = t.split("\\.");
                    try {
                        if(Integer.valueOf(id[0]) == rsanimalselected.getInt(6)){
                            updateanimalcatwalks.setValue(rsanimalselectededed.getInt(1) + ". nazwa: " + rsanimalselectededed.getString(2));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(rsanimalselected.getString(3).equals("M")){
                updateanimalsex.setValue("Samiec");
            }
            else{
                updateanimalsex.setValue("Samica");
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            Date d = rsanimalselected.getDate(5);
            LocalDate localDate = LocalDate.parse(d.toString() , formatter);
            updateanimaldate.setValue(localDate);
        }
        else{
            updateanimalgroup.setText("");
            updateanimalname.setText("");
            updateanimalsex.setValue(null);
            updateanimalcatwalks.setValue(null);
            updateanimaldate.setValue(null);
        }

    }
    /**
     obsluga nacisniecia przycisku edycji zwierzecia
     */
    public void pressUpdateAnimal(ActionEvent event)throws SQLException, ClassNotFoundException {
        updateanimaltext.setText("");
        if(!updateanimalname.getText().isEmpty() && !updateanimalgroup.getText().isEmpty() &&
                !updateanimalcatwalks.getValue().toString().equals("-")  && !updateanimalsex.getValue().toString().equals("-") &&
                !(updateanimaldate.getEditor().getText().isEmpty())) {
            Connection con = null;
            con = Connect.getConnection();
            PreparedStatement psrm = con.prepareStatement("UPDATE Zwierzeta SET nazwa = ? , plec =  ? , gromada = ? , data_urodzenia = ? , wybiegi_id = ? WHERE id = ?");
            psrm.setString(1, updateanimalname.getText());
            psrm.setString(3, updateanimalgroup.getText());
            if (updateanimalsex.getSelectionModel().getSelectedItem().equals("Samiec")) {
                psrm.setString(2, "M");
            } else {
                psrm.setString(2, "K");
            }
            psrm.setDate(4, Date.valueOf(updateanimaldate.getValue()));
            String t = updateanimalcatwalks.getValue().toString();
            if(!"-".equals(t) && t != null) {
                String[] id = t.split("\\.");
                try {
                    psrm.setInt(5, Integer.valueOf(id[0]));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            String t1 = updateanimalselect.getValue().toString();
            if(!"-".equals(t1) && t1 != null) {
                String[] id = t1.split("\\.");
                try {
                    psrm.setInt(6, Integer.valueOf(id[0]));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            psrm.executeUpdate();
            try {

                UpdateAnimalContent();
                updateanimaltext.setText("Edytowano zwierze");
                updateanimalname.setText("");
                updateanimalgroup.setText("");
                updateanimalcatwalks.setValue("");
                updateanimalsex.setValue("");
                updateanimaldate.setValue(null);
            }
            catch(SQLException e) {
                updateanimaltext.setText("Wprowadzono błędne dane");
            }
        }
        else{
            updateanimaltext.setText("Uzupelnij wszystkie dane");
        }
    }
}
