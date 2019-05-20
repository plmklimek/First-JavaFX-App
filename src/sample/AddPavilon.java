package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddPavilon {
    @FXML TextField addpavilonname;
    @FXML Text addpavilontext;
    @FXML TextArea addpavilondesc;
    @FXML AnchorPane addpaviloncontent;

    public void backtomenu(javafx.scene.input.MouseEvent event) throws IOException {
        addpaviloncontent.getChildren().clear();
        AnchorPane pavilonmenu = FXMLLoader.load(getClass().getResource("pavilonmenu.fxml"));
        addpaviloncontent.getChildren().setAll(pavilonmenu);
    }
    public void initialize() {
        addpavilontext.setText("");
    }
    /**
     Funkcja ustawiajaca kontrolki w oknie dodania pawilonu
     */
    public void AddPavilon()throws SQLException, ClassNotFoundException {
        if(!addpavilonname.getText().isEmpty() && !addpavilondesc.getText().isEmpty())
        {
            Connection con = null;
            con = Connect.getConnection();
            PreparedStatement prsm = con.prepareStatement("INSERT INTO Pawilony VALUES (?,?,?)");
            prsm.setInt(1,1);
            prsm.setString(2,addpavilonname.getText());
            prsm.setString(3,addpavilondesc.getText());
            try {
                prsm.executeQuery();
                addpavilontext.setText("Dodano pawilon");
                addpavilonname.setText("");
                addpavilondesc.setText("");
            }
            catch(SQLException e) {
                addpavilontext.setText("Wprowadzono błędne dane");
            }

        }
        else{
            addpavilontext.setText("Uzupelnij wszystkie dane");
        }
    }
    /**
     Obsluga nacisniecia przycisku dodania pawilonu
     */
    public void pressAddPavilon(ActionEvent event) throws SQLException, ClassNotFoundException {
        AddPavilon();
    }
}
