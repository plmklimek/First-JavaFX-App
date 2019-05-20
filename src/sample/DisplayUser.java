package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;

public class DisplayUser {
@FXML AnchorPane displayusercontent;
@FXML TableView tabledisplayuser;
@FXML ChoiceBox userdisplayfiltr1;
@FXML ChoiceBox userdisplayfiltr2;
@FXML ChoiceBox userdisplayfiltr3;
@FXML TextField userdisplaytext1;
@FXML TextField userdisplaytext2;
@FXML Button userdisplaysend;
@FXML ChoiceBox userdisplaysex1;
@FXML ChoiceBox userdisplaysex2;
@FXML DatePicker userdisplaydate1;
@FXML DatePicker userdisplaydate2;

    public void initialize() throws ParseException, SQLException, ClassNotFoundException {
        TableUser();
    }

    public void backtomenu(javafx.scene.input.MouseEvent event) throws IOException {
        displayusercontent.getChildren().clear();
        AnchorPane usermenu = FXMLLoader.load(getClass().getResource("usermenu.fxml"));
        displayusercontent.getChildren().setAll(usermenu);
    }

    /**
     Funkcja tworzaca strukture tabeli w oknie wyswietlania tabeli pracownika
     */
    public void TableUser() throws SQLException, ClassNotFoundException, ParseException {
        userdisplaysex1.setVisible(false);
        userdisplaytext1.setVisible(true);
        userdisplaydate1.setVisible(false);
        userdisplaysex2.setVisible(false);
        userdisplaytext2.setVisible(true);
        userdisplaydate2.setVisible(false);
        TableColumn iduser = new TableColumn("Id");
        iduser.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn nameuser = new TableColumn("Imie");
        nameuser.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn SecondName = new TableColumn("Nazwisko");
        SecondName.setCellValueFactory(new PropertyValueFactory<>("SecondName"));
        TableColumn PhoneNumber = new TableColumn("Telefon");
        PhoneNumber.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
        TableColumn sexuser = new TableColumn("Plec");
        sexuser.setCellValueFactory(new PropertyValueFactory<>("sex"));
        TableColumn salary = new TableColumn("Pensja");
        salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        TableColumn place = new TableColumn("Stanowisko");
        place.setCellValueFactory(new PropertyValueFactory<>("JobPosition"));
        TableColumn dateuser = new TableColumn("Data zatrudnienia");
        dateuser.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateuser.setMinWidth(250);

        tabledisplayuser.getColumns().addAll(iduser, nameuser, SecondName, PhoneNumber, sexuser, salary, place, dateuser);
        InsertUser(0,0, 0,"", "");
        UserFiltr();
    }

    /**
     Funkcja ustawiajace kontrolki do filtrowania tabeli pracownikow
     */
    public void UserFiltr(){
        userdisplayfiltr1.getItems().add("-");
        userdisplayfiltr1.setValue("-");
        userdisplayfiltr1.getItems().add("imie");
        userdisplayfiltr1.getItems().add("nazwisko");
        userdisplayfiltr1.getItems().add("telefon");
        userdisplayfiltr1.getItems().add("plec");
        userdisplayfiltr1.getItems().add("pensja wieksza");
        userdisplayfiltr1.getItems().add("pensja mniejsza");
        userdisplayfiltr1.getItems().add("stanowisko");
        userdisplayfiltr1.getItems().add("zatrudniony po");
        userdisplayfiltr1.getItems().add("zatrudniony przed");

        userdisplayfiltr2.getItems().add("-");
        userdisplayfiltr2.setValue("-");
        userdisplayfiltr2.getItems().add("lub");
        userdisplayfiltr2.getItems().add("i");

        userdisplayfiltr3.getItems().add("-");
        userdisplayfiltr3.setValue("-");
        userdisplayfiltr3.getItems().add("imie");
        userdisplayfiltr3.getItems().add("nazwisko");
        userdisplayfiltr3.getItems().add("telefon");
        userdisplayfiltr3.getItems().add("plec");
        userdisplayfiltr3.getItems().add("pensja wieksza");
        userdisplayfiltr3.getItems().add("pensja mniejsza");
        userdisplayfiltr3.getItems().add("stanowisko");
        userdisplayfiltr3.getItems().add("zatrudniony po");
        userdisplayfiltr3.getItems().add("zatrudniony przed");

        userdisplaysex1.getItems().add("Mężczyzna");
        userdisplaysex1.getItems().add("Kobieta");
        userdisplaysex1.setValue("Mężczyzna");
        userdisplaysex2.getItems().add("Mężczyzna");
        userdisplaysex2.getItems().add("Kobieta");
        userdisplaysex2.setValue("Mężczyzna");
        userdisplaydate2.setVisible(false);
        userdisplaydate1.setVisible(false);
        userdisplaysex1.setVisible(false);
        userdisplaysex2.setVisible(false);
        userdisplayfiltr1.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldV, newV) -> {
                    int t = userdisplayfiltr1.getSelectionModel().getSelectedIndex();
                    if(t == 0){
                        userdisplaytext1.setText("");
                    }
                    if(t == 4){
                        userdisplaysex1.setVisible(true);
                        userdisplaytext1.setVisible(false);
                        userdisplaydate1.setVisible(false);
                    }
                    else if(t == 8 || t == 9) {
                        userdisplaysex1.setVisible(false);
                        userdisplaytext1.setVisible(false);
                        userdisplaydate1.setVisible(true);
                    }
                    else{
                        userdisplaysex1.setVisible(false);
                        userdisplaytext1.setVisible(true);
                        userdisplaydate1.setVisible(false);
                    }
                });
        userdisplayfiltr3.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldV, newV) -> {
                    int t = userdisplayfiltr3.getSelectionModel().getSelectedIndex();
                    if(t == 0){
                        userdisplaytext2.setText("");
                    }
                    if(t == 4){
                        userdisplaysex2.setVisible(true);
                        userdisplaytext2.setVisible(false);
                        userdisplaydate2.setVisible(false);
                    }
                    else if(t == 8 || t == 9) {
                        userdisplaysex2.setVisible(false);
                        userdisplaytext2.setVisible(false);
                        userdisplaydate2.setVisible(true);
                    }
                    else{
                        userdisplaysex2.setVisible(false);
                        userdisplaytext2.setVisible(true);
                        userdisplaydate2.setVisible(false);
                    }
                });
    }

    /**
     Funkcja obslugujaca nacisniecie przycisku filtrowania danych  z tabeli
     */
    public void pressUserFiltrButton(ActionEvent event) throws SQLException, ClassNotFoundException, ParseException {
        int id1 = userdisplayfiltr1.getSelectionModel().getSelectedIndex();
        int id2 = userdisplayfiltr3.getSelectionModel().getSelectedIndex();
        String act1 = null;
        String act2 = null;
        if(id1 == 4){
            if(userdisplaysex1.getSelectionModel().getSelectedItem().equals("Mężczyzna")){
                act1 = "M";
            }
            else{
                act1 = "K";
            }
        }
        else if(id1 == 8 || id1 == 9) {
            act1 = userdisplaydate1.getValue().toString();
        }
        else{
            act1 = userdisplaytext1.getText();
        }
        if(id2 == 4){
            if(userdisplaysex2.getSelectionModel().getSelectedItem().equals("Mężczyzna")){
                act2 = "M";
            }
            else{
                act2 = "K";
            }
        }
        else if(id2 == 8 || id2 == 9) {
            act2 = userdisplaydate2.getValue().toString();
        }
        else{
            act2 = userdisplaytext2.getText();
        }
        InsertUser(userdisplayfiltr1.getSelectionModel().getSelectedIndex(),userdisplayfiltr2.getSelectionModel().getSelectedIndex(),userdisplayfiltr3.getSelectionModel().getSelectedIndex(),act1, act2);

    }

    /**

     Funkcja dodajaca filtrowane dane do tabelis
     */
    public void InsertUser(int act1 , int act2, int act3, String t1 , String t2) throws SQLException, ClassNotFoundException, ParseException {
        tabledisplayuser.getItems().clear();
        Connection con = null;
        con = Connect.getConnection();
        Person user = null;
        ResultSet rsuser;
        String query = "";
        if((act1 == 0 || t1.isEmpty())) {
            query = "select * from Pracownicy";
        }
        else if(act1 == 1){
            query = "select * from Pracownicy WHERE imie LIKE ?";
            t1 = "%" + t1 + "%";
        }
        else if(act1 == 2){
            query = "select * from Pracownicy WHERE nazwisko LIKE ?";
            t1 = "%" + t1 + "%";
        }
        else if(act1 == 3){
            query = "select * from Pracownicy WHERE telefon LIKE ?";
            t1 = "%" + t1 + "%";
        }
        else if(act1 == 4){
            if(t1.equals("M")) {
                query = "select * from Pracownicy WHERE plec LIKE ?";
            }
            else{
                query = "select * from Pracownicy WHERE plec LIKE ?";
            }
        }
        else if(act1 == 5){
            query = "select * from Pracownicy WHERE pensja > ?";
        }
        else if(act1 == 6){
            query = "select * from Pracownicy WHERE pensja < ?";
        }
        else if(act1 == 7){
            query = "select * from Pracownicy WHERE stanowisko LIKE ?";
            t1 = "%" + t1 + "%";
        }
        else if(act1 == 8){
            query = "select * from Pracownicy WHERE data_zatrudnienia >= ?";
        }
        else if(act1 == 9){
            query = "select * from Pracownicy WHERE data_zatrudnienia <= ?";
        }
        if(act1 != 0 && act2 != 0 && act3 != 0 && !t2.isEmpty()){
            if(act2 == 1){
                query += " OR ";
            }
            else{
                query += " AND ";
            }
            if(act3 == 1){
                query += "imie LIKE ?";
                t2 = "%" + t2 + "%";
            }
            else if(act3 == 2){
                query += "nazwisko LIKE ?";
                t2 = "%" + t2 + "%";
            }
            else if(act3 == 3){
                query += "telefon LIKE ?";
                t2 = "%" + t2 + "%";
            }
            else if(act3 == 4){
                if(t2.equals("M")) {
                    query += "plec LIKE ?";
                }
                else{
                    query += "plec LIKE ?";
                }
            }
            else if(act3 == 5){
                query += "pensja > ?";
            }
            else if(act3 == 6){
                query += "pensja < ?";
            }
            else if(act3 == 7){
                query += "stanowisko LIKE ?";
                t2 = "%" + t2 + "%";
            }
            else if(act3 == 8){
                query += "data_zatrudnienia >= ?";
            }
            else if(act3 == 9){
                query += "data_zatrudnienia <= ?";
            }
        }
        PreparedStatement prsm = con.prepareStatement(query);
        if(act1 != 0 && !t1.isEmpty()){
            if(act1 == 8 || act1 == 9){
                Date date = Date.valueOf(t1);
                prsm.setDate(1,date);
            }
            else if(act1 == 5 || act1 == 6){
                prsm.setDouble(1,  Double.parseDouble(t1));
            }
            else {
                prsm.setString(1, t1);
            }
            if((act2 != 0) && (act3 != 0) && !t2.isEmpty()) {
                if(act3 == 8 || act3 == 9){
                    Date date = Date.valueOf(t2);
                    prsm.setDate(2,date);
                }
                else if(act3 == 5 || act3 == 6){
                    prsm.setDouble(2,  Double.parseDouble(t2));
                }
                else {
                    prsm.setString(2, t2);
                }
            }
        }
        rsuser = prsm.executeQuery();
        int t = 0;
        while (rsuser.next()) {
            t++;
            if (rsuser.getString(5) != null) {
                if (rsuser.getString(5).equals("M")) {
                    user = new Person(rsuser.getInt(1), rsuser.getString(2), rsuser.getString(3), rsuser.getString(4), "Mężczyzna", rsuser.getDouble(6), rsuser.getString(7), rsuser.getDate(8));
                } else {
                    user = new Person(rsuser.getInt(1), rsuser.getString(2), rsuser.getString(3), rsuser.getString(4), "Kobieta", rsuser.getDouble(6), rsuser.getString(7), rsuser.getDate(8));
                }

                tabledisplayuser.getItems().add(user);
            }
        }
    }
}
