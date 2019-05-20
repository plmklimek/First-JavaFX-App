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

public class DeleteFood {
    @FXML AnchorPane deletefoodcontent;
    @FXML ChoiceBox deletefoodselect;
    @FXML TextField deletefoodname;
    @FXML TextField deletefoodilosc;
    @FXML Text deletefoodtext;
    public void initialize() throws SQLException {
        DeleteFoodContent();
    }
    public void backtomenu(javafx.scene.input.MouseEvent event) throws IOException {
        deletefoodcontent.getChildren().clear();
        AnchorPane foodmenu = FXMLLoader.load(getClass().getResource("foodmenu.fxml"));
        deletefoodcontent.getChildren().setAll(foodmenu);
    }
    /**
     ustawienie kontrolek w oknie usuniecia jedzenia
     */
    public void DeleteFoodContent() throws SQLException {
        deletefoodilosc.setText("");
        deletefoodname.setText("");
        deletefoodtext.setText("");
        Connection con = null;
        con = Connect.getConnection();
        PreparedStatement psrm = con.prepareStatement("SELECT * FROM Pokarm");
        ResultSet rsfooddelete = psrm.executeQuery();
        deletefoodselect.getItems().clear();
        deletefoodselect.getItems().add("-");
        deletefoodselect.setValue("-");
        while (rsfooddelete.next()) {
            deletefoodselect.getItems().add(rsfooddelete.getInt(1) + ". nazwa : " + rsfooddelete.getString(2));

        }
        deletefoodselect.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldV, newV) -> {
                    String t = (String)deletefoodselect.getSelectionModel().getSelectedItem();
                    if(!"-".equals(t) && t != null){
                        String[] id = t.split("\\.");
                        try {
                            DeleteFoodSelection(Integer.valueOf(id[0]));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        try {
                            DeleteFoodSelection(0);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }



                });
    }
    /**
     wyswietlenie danych wybranego jedzenia
     */
    public void DeleteFoodSelection(int n) throws SQLException {
        if(n != 0) {
            Connection con = null;
            con = Connect.getConnection();
            PreparedStatement prsm = con.prepareStatement("SELECT * FROM Pokarm WHERE id = ?");
            prsm.setInt(1, n);
            ResultSet rsfoodselected = prsm.executeQuery();
            rsfoodselected.next();
            deletefoodname.setText(rsfoodselected.getString(2));
            deletefoodilosc.setText(rsfoodselected.getString(3));
        }
        else{
            deletefoodname.setText("");
            deletefoodilosc.setText("");
        }

    }
    /**
     obsluga nacisniecia przycisku usuniecia jedzenia
     */
    public void pressDeleteFood(ActionEvent event)throws SQLException, ClassNotFoundException {

        Connection con = null;
        con = Connect.getConnection();
        PreparedStatement psrm = con.prepareStatement("DELETE FROM Pokarm WHERE id = ?");
        String t = (String)deletefoodselect.getSelectionModel().getSelectedItem();
        if(!"-".equals(t) && t != null){
            String[] id = t.split("\\.");
            psrm.setInt(1,Integer.valueOf(id[0]));
        }

        try {
            psrm.executeQuery();
            DeleteFoodContent();
            deletefoodtext.setText("Usunieto pokarm");
            deletefoodilosc.setText("");
            deletefoodname.setText("");
        } catch (SQLException e) {
            deletefoodtext.setText(e.getMessage());
        }
    }
}
