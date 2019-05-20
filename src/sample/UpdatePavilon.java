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

public class UpdatePavilon {
    @FXML ChoiceBox updatepavilonselect;
    @FXML TextField updatepavilonname;
    @FXML Text updatepavilontext;
    @FXML TextArea updatepavilondesc;
    @FXML AnchorPane editpaviloncontent;
    public void initialize() throws SQLException {
        UpdatePavilonContent();
    }
    public void backtomenu(javafx.scene.input.MouseEvent event) throws IOException {
        editpaviloncontent.getChildren().clear();
        AnchorPane pavilonmenu = FXMLLoader.load(getClass().getResource("pavilonmenu.fxml"));
        editpaviloncontent.getChildren().setAll(pavilonmenu);
    }
    public void UpdatePavilonContent() throws SQLException {
        updatepavilontext.setText("");
        Connection con = null;
        con = Connect.getConnection();
        PreparedStatement psrm = con.prepareStatement("SELECT * FROM Pawilony");
        ResultSet rspavilonselect = psrm.executeQuery();
        updatepavilonselect.getItems().clear();
        updatepavilonselect.getItems().add("-");
        updatepavilonselect.setValue("-");
        while (rspavilonselect.next()) {
            updatepavilonselect.getItems().add(rspavilonselect.getInt(1) + ". " + rspavilonselect.getString(2));
        }
        updatepavilonselect.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldV, newV) -> {

                    try {
                        String t = (String)updatepavilonselect.getSelectionModel().getSelectedItem();
                        if(!"-".equals(t) && t != null) {
                            UpdatePavilonSelection(Integer.parseInt(t.split("\\.")[0]));
                        }
                        else{
                            UpdatePavilonSelection(0);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
    }
    /**
     Ustawienie kontrolek w oknie edycji pawilonu
     */
    public void UpdatePavilonSelection(int n) throws SQLException {
        if(n != 0) {
            Connection con = null;
            con = Connect.getConnection();
            PreparedStatement psrm = con.prepareStatement("SELECT * FROM Pawilony WHERE id = ?");
            psrm.setInt(1,n);
            ResultSet rspavilonselected = psrm.executeQuery();
            rspavilonselected.next();
            updatepavilonname.setText(rspavilonselected.getString(2));
            updatepavilondesc.setText(rspavilonselected.getString(3));
        }
        else{
            updatepavilonname.setText("");
            updatepavilondesc.setText("");
        }

    }
    /**
     obsluga nacisniecia przycisku edycji pawilonu
     */
    public void pressUpdatePavilon(ActionEvent event)throws SQLException, ClassNotFoundException {
        updatepavilontext.setText("");
        if(!updatepavilonname.getText().isEmpty() && !updatepavilondesc.getText().isEmpty() )
        {
            Connection con = null;
            con = Connect.getConnection();
            PreparedStatement psrm = con.prepareStatement("UPDATE Pawilony SET nazwa = ? , opis =  ? WHERE id = ?");
            psrm.setString(1,updatepavilonname.getText());
            psrm.setString(2,updatepavilondesc.getText());
            String t = (String)updatepavilonselect.getSelectionModel().getSelectedItem();
            if(!"-".equals(t)) {
                psrm.setInt(3,Integer.parseInt(t.split("\\.")[0]));
            }

            try {
                psrm.executeUpdate();
                UpdatePavilonContent();
                updatepavilontext.setText("Edytowano wybieg");
                updatepavilonname.setText("");
                updatepavilondesc.setText("");
            }
            catch(SQLException e) {
                updatepavilontext.setText("Wprowadzono błędne dane");
            }

        }
        else{
            updatepavilontext.setText("Uzupelnij wszystkie dane");
        }
    }
}
