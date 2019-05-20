package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DeleteAnimal {
    @FXML AnchorPane deleteanimalcontent;
    @FXML ChoiceBox deleteanimalselect;
    @FXML TextField deleteanimalname;
    @FXML TextField deleteanimalgroup;
    @FXML TextField deleteanimalsex;
    @FXML TextField deleteanimalcatwalks;
    @FXML DatePicker deleteanimaldate;
    @FXML Text deleteanimaltext;
    public void initialize() throws SQLException {
        DeleteAnimalContent();
    }
    public void backtomenu(javafx.scene.input.MouseEvent event) throws IOException {
        deleteanimalcontent.getChildren().clear();
        AnchorPane animalmenu = FXMLLoader.load(getClass().getResource("animalmenu.fxml"));
        deleteanimalcontent.getChildren().setAll(animalmenu);
    }
    /**
     ustawienie kontrolek w oknie usuniecia zwierzecia
     */
    public void DeleteAnimalContent() throws SQLException {
        deleteanimalselect.setValue(null);
        deleteanimalsex.setText("");
        deleteanimalcatwalks.setText("");
        deleteanimaldate.setValue(null);
        deleteanimalgroup.setText("");
        deleteanimalname.setText("");
        deleteanimaltext.setText("");
        Connection con = null;
        con = Connect.getConnection();
        PreparedStatement psrm = con.prepareStatement("SELECT A.id,A.nazwa,B.nazwa FROM Zwierzeta A JOIN Wybiegi B ON A.wybiegi_id = B.id ");
        ResultSet rsanimaldelete = psrm.executeQuery();
        deleteanimalselect.getItems().clear();
        deleteanimalselect.getItems().add("-");
        deleteanimalselect.setValue("-");
        while (rsanimaldelete.next()) {
            deleteanimalselect.getItems().add(rsanimaldelete.getInt(1) + ". nazwa : " + rsanimaldelete.getString(2)  + " Wybieg : " + rsanimaldelete.getString(3));
        }
        deleteanimalselect.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldV, newV) -> {
                    String t = (String)deleteanimalselect.getSelectionModel().getSelectedItem();
                    if(!"-".equals(t) && t != null){
                        String[] id = t.split("\\.");
                        try {
                            DeleteAnimalSelection(Integer.valueOf(id[0]));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }




                });
    }
    /**
     wyswietlenie danych wybranego zwierzecia
     */
    public void DeleteAnimalSelection(int n) throws SQLException {
        if(n != 0) {
            Connection con = null;
            con = Connect.getConnection();
            PreparedStatement prsm = con.prepareStatement("SELECT * FROM Zwierzeta WHERE id = ?");
            prsm.setInt(1, n);
            ResultSet rsanimalselected = prsm.executeQuery();
            rsanimalselected.next();
            deleteanimalname.setText(rsanimalselected.getString(2));
            deleteanimalgroup.setText(rsanimalselected.getString(4));
            if (!rsanimalselected.getString(3).isEmpty()){
                if (rsanimalselected.getString(3).equals("M")) {
                    deleteanimalsex.setText("Samiec");
                } else {
                    deleteanimalsex.setText("Samica");
                }
            }
            PreparedStatement psrms = con.prepareStatement("SELECT A.nazwa FROM Wybiegi A JOIN Zwierzeta B ON A.id = B.wybiegi_id WHERE B.id = ?    ");
            psrms.setInt(1,n);
            ResultSet rsanimaldelete = psrms.executeQuery();
            rsanimaldelete.next();
            deleteanimalcatwalks.setText(rsanimaldelete.getString(1));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            Date d = rsanimalselected.getDate(5);
            LocalDate localDate = LocalDate.parse(d.toString() , formatter);
            deleteanimaldate.setValue(localDate);
        }
        else{
            deleteanimalname.setText("");
            deleteanimalgroup.setText("");
            deleteanimalsex.setText("");
            deleteanimalcatwalks.setText("");
            deleteanimaldate.setValue(null);


        }

    }
    /**
     obsluga nacisniecia przycisku usuniecia zwierzecia
     */
    public void pressDeleteAnimal(ActionEvent event)throws SQLException, ClassNotFoundException {

        Connection con = null;
        con = Connect.getConnection();
        PreparedStatement psrm = con.prepareStatement("DELETE FROM Zwierzeta WHERE id = ?");

        psrm.setInt(1, Integer.valueOf(deleteanimalselect.getSelectionModel().getSelectedItem().toString().split("\\.")[0]));

        try {
            psrm.executeQuery();
            DeleteAnimalContent();
            deleteanimaltext.setText("Usunieto zwierze");
            deleteanimalname.setText("");
            deleteanimalgroup.setText("");
            deleteanimalcatwalks.setText("");
            deleteanimalsex.setText("");
            deleteanimaldate.setValue(null);
        } catch (SQLException e) {
            deleteanimaltext.setText(e.getMessage());
        }
    }
}
