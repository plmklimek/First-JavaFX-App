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

public class UpdateFood {
    @FXML AnchorPane editfoodcontent;
    @FXML Text updatefoodtext;
    @FXML ChoiceBox updatefoodselect;
    @FXML TextField updatefoodname;
    @FXML TextField updatefoodilosc;
    public void initialize() throws SQLException {
        UpdateFoodContent();
    }
    public void backtomenu(javafx.scene.input.MouseEvent event) throws IOException {
        editfoodcontent.getChildren().clear();
        AnchorPane foodmenu = FXMLLoader.load(getClass().getResource("foodmenu.fxml"));
        editfoodcontent.getChildren().setAll(foodmenu);
    }
    /**
     Ustawienie kontrolek w oknie edycji jedzenia
     */
    public void UpdateFoodContent() throws SQLException {
        updatefoodtext.setText("");
        Connection con = null;
        con = Connect.getConnection();
        PreparedStatement psrm = con.prepareStatement("SELECT * FROM Pokarm");
        ResultSet rsfoodselect = psrm.executeQuery();
        updatefoodselect.getItems().clear();
        updatefoodselect.getItems().add("-");
        updatefoodselect.setValue("-");
        while (rsfoodselect.next()) {
            updatefoodselect.getItems().add(rsfoodselect.getInt(1) + ". nazwa : " + rsfoodselect.getString(2));
        }
        updatefoodselect.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldV, newV) -> {

                    try {
                        String t = (String)updatefoodselect.getSelectionModel().getSelectedItem();
                        if(!"-".equals(t) && t != null){
                            String[] id = t.split("\\.");
                            try {
                                UpdateFoodSelection(Integer.valueOf(id[0]));
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            UpdateFoodSelection(0);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
    }
    /**
     ustawienie danych po wybraniu jedzenia
     */
    public void UpdateFoodSelection(int n) throws SQLException {
        if(n != 0) {
            Connection con = null;
            con = Connect.getConnection();
            PreparedStatement psrm = con.prepareStatement("SELECT * FROM Pokarm WHERE id = ?");
            psrm.setInt(1,n);
            ResultSet rsfoodselected = psrm.executeQuery();
            rsfoodselected.next();
            updatefoodname.setText(rsfoodselected.getString(2));
            updatefoodilosc.setText(rsfoodselected.getString(3));
        }
        else{
            updatefoodname.setText("");
            updatefoodilosc.setText("");
        }

    }
    /**
     obsluga nacisniecia przycisku edycji jedzenia
     */
    public void pressUpdateFood(ActionEvent event)throws SQLException, ClassNotFoundException {
        updatefoodtext.setText("");
        if(!updatefoodilosc.getText().isEmpty() && !updatefoodname.getText().isEmpty())
        {
            Connection con = null;
            con = Connect.getConnection();
            PreparedStatement psrm = con.prepareStatement("UPDATE Pokarm SET nazwa = ? , ilosc =  ? WHERE id = ?");
            psrm.setString(1,updatefoodname.getText());
            psrm.setInt(2,Integer.valueOf(updatefoodilosc.getText()));
            String t = (String)updatefoodselect.getSelectionModel().getSelectedItem();
            if(!"-".equals(t) && t != null){
                String[] id = t.split("\\.");
                psrm.setInt(3,Integer.valueOf(id[0]));
            }


            try {
                psrm.executeUpdate();
                UpdateFoodContent();
                updatefoodtext.setText("Edytowano pokarm");
                updatefoodname.setText("");
                updatefoodilosc.setText("");
            }
            catch(SQLException e) {
                updatefoodtext.setText("Wprowadzono błędne dane");
            }
        }
        else{
            updatefoodtext.setText("Uzupelnij wszystkie dane");
        }
    }
}
