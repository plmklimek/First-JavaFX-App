package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeletePavilon {
    @FXML AnchorPane deletepaviloncontent;
    @FXML ChoiceBox deletepavilonselect;
    @FXML TextField deletepavilonname;
    @FXML TextArea deletepavilondesc;
    @FXML Text deletepavilontext;
    public void initialize() throws SQLException {
        DeletePavilonContent();
    }
    public void backtomenu(javafx.scene.input.MouseEvent event) throws IOException {
        deletepaviloncontent.getChildren().clear();
        AnchorPane pavilonmenu = FXMLLoader.load(getClass().getResource("pavilonmenu.fxml"));
        deletepaviloncontent.getChildren().setAll(pavilonmenu);
    }
    /**
     ustawienie kontrolek w oknie usuniecia pawilonu
     */
    public void DeletePavilonContent() throws SQLException {
        deletepavilondesc.setText("");
        deletepavilonname.setText("");
        deletepavilontext.setText("");
        Connection con = null;
        con = Connect.getConnection();
        PreparedStatement psrm = con.prepareStatement("SELECT * FROM Pawilony");
        ResultSet rspavilondelete = psrm.executeQuery();
        deletepavilonselect.getItems().clear();
        deletepavilonselect.getItems().add("-");
        deletepavilonselect.setValue("-");
        while (rspavilondelete.next()) {
            deletepavilonselect.getItems().add(rspavilondelete.getInt(1) + ". " + rspavilondelete.getString(2));
        }

        deletepavilonselect.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldV, newV) -> {
                    try {
                        String t = (String)deletepavilonselect.getSelectionModel().getSelectedItem();
                        if(!"-".equals(t) && t != null) {
                            DeletePavilonSelection(Integer.parseInt(t.split("\\.")[0]));
                        }
                        else{
                            DeletePavilonSelection(0);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }



                });
    }
    /**
     wyswietlenie danych wybranego pawilonu
     */
    public void DeletePavilonSelection(int n) throws SQLException {
        if(n != 0) {
            Connection con = null;
            con = Connect.getConnection();
            PreparedStatement prsm = con.prepareStatement("SELECT * FROM Pawilony WHERE id = ?");
            prsm.setInt(1, n);
            ResultSet rspavilonselected = prsm.executeQuery();
            rspavilonselected.next();
            deletepavilonname.setText(rspavilonselected.getString(2));
            deletepavilondesc.setText(rspavilonselected.getString(3));
        }
        else{
            deletepavilonname.setText("");
            deletepavilondesc.setText("");
        }

    }
    /**
     obsluga nacisniecia przycisku usuniecia pawilonu
     */
    public void pressDeletePavilon(ActionEvent event)throws SQLException, ClassNotFoundException {

        Connection con = null;
        con = Connect.getConnection();
        PreparedStatement psrm = con.prepareStatement("DELETE FROM Pawilony WHERE id = ?");
        String t = (String)deletepavilonselect.getSelectionModel().getSelectedItem();
        if(!"-".equals(t)) {
            psrm.setInt(1,Integer.parseInt(t.split("\\.")[0]));
        }
        try {
            psrm.executeQuery();
            DeletePavilonContent();
            deletepavilontext.setText("Usunieto pawilon");
            deletepavilonname.setText("");
            deletepavilondesc.setText("");
        } catch (SQLException e) {
            deletepavilontext.setText(e.getMessage());
        }
    }
}
