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

public class DeleteCatWalks {
    @FXML AnchorPane deletecatwalkscontent;
    @FXML ChoiceBox deletecatwalksselect;
    @FXML TextField deletecatwalksname;
    @FXML TextField deletecatwalkstype;
    @FXML TextField deletecatwalkspavilon;
    @FXML Text deletecatwalkstext;
    public void initialize() throws SQLException {
        DeleteCatWalksContent();
    }
    public void backtomenu(javafx.scene.input.MouseEvent event) throws IOException {
        deletecatwalkscontent.getChildren().clear();
        AnchorPane catwalksmenu = FXMLLoader.load(getClass().getResource("catwalksmenu.fxml"));
        deletecatwalkscontent.getChildren().setAll(catwalksmenu);
    }
    /**
     ustawienie kontrolek w oknie usuniecia wybiegu
     */
    public void DeleteCatWalksContent() throws SQLException {
        deletecatwalksname.setText("");
        deletecatwalkspavilon.setText("");
        deletecatwalkstype.setText("");
        deletecatwalkstext.setText("");
        Connection con = null;
        con = Connect.getConnection();
        PreparedStatement psrm = con.prepareStatement("SELECT A.id , A.nazwa , A.typ , B.nazwa FROM Wybiegi A JOIN Pawilony B ON A.pawilony_id = B.id");
        ResultSet rscatwalksdelete = psrm.executeQuery();
        deletecatwalksselect.getItems().clear();
        deletecatwalksselect.getItems().add("-");
        deletecatwalksselect.setValue("-");
        while (rscatwalksdelete.next()) {
            deletecatwalksselect.getItems().add(rscatwalksdelete.getInt(1) + ". nazwa : " + rscatwalksdelete.getString(2));
        }

        deletecatwalksselect.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldV, newV) -> {
                    String t = (String)deletecatwalksselect.getSelectionModel().getSelectedItem();
                    if(!"-".equals(t) && t != null){
                        String[] id = t.split("\\.");
                        try {
                            DeleteCatWalksSelection(Integer.valueOf(id[0]));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }




                });
    }
    /**
     wyswietlenie danych wybranego wybiegu
     */
    public void DeleteCatWalksSelection(int n) throws SQLException {
        if(n != 0) {
            Connection con = null;
            con = Connect.getConnection();
            PreparedStatement prsm = con.prepareStatement("SELECT A.id , A.nazwa , A.typ , B.nazwa FROM Wybiegi A JOIN Pawilony B ON A.pawilony_id = B.id WHERE A.id = ?");
            prsm.setInt(1, n);
            ResultSet rscatwalksselected = prsm.executeQuery();
            rscatwalksselected.next();
            deletecatwalksname.setText(rscatwalksselected.getString(2));
            deletecatwalkstype.setText(rscatwalksselected.getString(3));
            deletecatwalkspavilon.setText(rscatwalksselected.getString(4));
        }
        else{
            deletecatwalksname.setText("");
            deletecatwalkstype.setText("");
            deletecatwalkspavilon.setText("");
        }
    }
    /**
     obsluga nacisniecia przycisku usuniecia wybiegu
     */
    public void pressDeleteCatWalks(ActionEvent event)throws SQLException, ClassNotFoundException {

        Connection con = null;
        con = Connect.getConnection();
        PreparedStatement psrm = con.prepareStatement("DELETE FROM Wybiegi WHERE id = ?");
        psrm.setInt(1, Integer.valueOf(deletecatwalksselect.getSelectionModel().getSelectedItem().toString().split("\\.")[0]));
        try {
            psrm.executeQuery();
            DeleteCatWalksContent();
            deletecatwalkstext.setText("Usunieto Wybieg");
            deletecatwalksname.setText("");
            deletecatwalkspavilon.setText("");
            deletecatwalkstype.setText("");
        } catch (SQLException e) {
            deletecatwalkstext.setText(e.getMessage());
        }
    }
}
