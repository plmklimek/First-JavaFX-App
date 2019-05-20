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

public class UpdateCatWalks {
    @FXML AnchorPane editcatwalkscontent;
    @FXML ChoiceBox updatecatwalkspavilon;
    @FXML TextField updatecatwalkstype;
    @FXML TextField updatecatwalksname;
    @FXML ChoiceBox updatecatwalksselect;
    @FXML Text updatecatwalkstext;
    public void initialize() throws SQLException {
        UpdateCatWalks();
    }
    public void backtomenu(javafx.scene.input.MouseEvent event) throws IOException {
        editcatwalkscontent.getChildren().clear();
        AnchorPane catwalksmenu = FXMLLoader.load(getClass().getResource("catwalksmenu.fxml"));
        editcatwalkscontent.getChildren().setAll(catwalksmenu);
    }
    /**
     Ustawienie kontrolek w oknie edycji wybiegu
     */
    public void UpdateCatWalks() throws SQLException {
        updatecatwalkstext.setText("");
        Connection con = null;
        con = Connect.getConnection();
        PreparedStatement psrm = con.prepareStatement("SELECT id,nazwa FROM Wybiegi");
        ResultSet rscatwalksselect = psrm.executeQuery();
        updatecatwalksselect.getItems().clear();
        updatecatwalksselect.getItems().add("-");
        updatecatwalksselect.setValue("-");
        while (rscatwalksselect.next()) {
            updatecatwalksselect.getItems().add(rscatwalksselect.getInt(1) + ". " + rscatwalksselect.getString(2));
        }
        updatecatwalksselect.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldV, newV) -> {

                    String t = (String) updatecatwalksselect.getSelectionModel().getSelectedItem();
                    if (!"-".equals(t) && t != null) {
                        try {
                            UpdateCatWalksSelection(Integer.parseInt(t.split("\\.")[0]));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            UpdateCatWalksSelection(0);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                });
    }
    /**
     ustawienie danych po wybraniu wybiegu
     */
    public void UpdateCatWalksSelection(int n) throws SQLException {
        if(n != 0) {
            Connection con = null;
            con = Connect.getConnection();
            PreparedStatement psrm = con.prepareStatement("SELECT A.id , A.nazwa , A.typ , B.nazwa , A.pawilony_id FROM Wybiegi A JOIN Pawilony B ON A.pawilony_id = B.id WHERE A.id = ?");
            psrm.setInt(1,n);
            ResultSet rscatwalksselected = psrm.executeQuery();
            rscatwalksselected.next();
            updatecatwalksname.setText(rscatwalksselected.getString(2));
            updatecatwalkstype.setText(rscatwalksselected.getString(3));
            PreparedStatement psrmsms = con.prepareStatement("SELECT B.id,B.nazwa FROM Pawilony B");
            ResultSet rscatwalksselectededed = psrmsms.executeQuery();
            updatecatwalkspavilon.getItems().clear();
            while(rscatwalksselectededed.next()){
                updatecatwalkspavilon.getItems().add(rscatwalksselectededed.getInt(1) + ". " + rscatwalksselectededed.getString(2));
                if(rscatwalksselectededed.getInt(1) == rscatwalksselected.getInt(5)){
                    updatecatwalkspavilon.setValue(rscatwalksselectededed.getInt(1) + ". " + rscatwalksselectededed.getString(2));
                }
            }
        }
        else{
            updatecatwalksname.setText("");
            updatecatwalkstype.setText("");
        }

    }
    /**
     obsluga nacisniecia przycisku edycji wybiegu
     */
    public void pressUpdateCatWalks(ActionEvent event)throws SQLException, ClassNotFoundException {
        updatecatwalkstext.setText("");
        if(!updatecatwalksname.getText().isEmpty() && !updatecatwalkstype.getText().isEmpty() &&
                !updatecatwalkspavilon.getValue().toString().equals("-")) {
            Connection con = null;
            con = Connect.getConnection();
            PreparedStatement psrm = con.prepareStatement("UPDATE Wybiegi SET nazwa = ? , typ =  ? , pawilony_id = ?  WHERE id = ?");
            psrm.setString(1,updatecatwalksname.getText());
            psrm.setString(2,updatecatwalkstype.getText());
            String t = (String) updatecatwalkspavilon.getSelectionModel().getSelectedItem();
            if (!"-".equals(t) && t != null) {
                psrm.setInt(3,Integer.parseInt(t.split("\\.")[0]));
            }
            String y = (String) updatecatwalksselect.getSelectionModel().getSelectedItem();
            if (!"-".equals(t) && y != null) {
                psrm.setInt(4,Integer.parseInt(y.split("\\.")[0]));
            }
            psrm.executeUpdate();
            try {

                UpdateCatWalks();
                updatecatwalkstext.setText("Edytowano Wybieg");
                updatecatwalksname.setText("");
                updatecatwalkspavilon.setValue(null);
                updatecatwalkstype.setText("");
            }
            catch(SQLException e) {
                updatecatwalkstext.setText("Wprowadzono błędne dane");
            }
        }
        else{
            updatecatwalkstext.setText("Uzupelnij wszystkie dane");
        }
    }
}
