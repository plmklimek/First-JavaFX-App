package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;

public class DisplayAnimal {
    @FXML AnchorPane displayanimalcontent;
    @FXML ChoiceBox animaldisplayfiltr1;
    @FXML ChoiceBox animaldisplayfiltr2;
    @FXML ChoiceBox animaldisplayfiltr3;
    @FXML TextField animaldisplaytext1;
    @FXML TextField animaldisplaytext2;
    @FXML Button animaldisplaysend;
    @FXML TableView tabledisplayanimal;
    @FXML DatePicker animaldisplaydate1;
    @FXML DatePicker animaldisplaydate2;
    @FXML ChoiceBox displayanimalsex1;
    @FXML ChoiceBox displayanimalsex2;
    @FXML ChoiceBox animalchoicecatwalks1;
    @FXML ChoiceBox animalchoicecatwalks2;

    public void initialize() throws SQLException {
        TableAnimal();
    }
    public void backtomenu(javafx.scene.input.MouseEvent event) throws IOException {
        displayanimalcontent.getChildren().clear();
        AnchorPane animalmenu = FXMLLoader.load(getClass().getResource("animalmenu.fxml"));
        displayanimalcontent.getChildren().setAll(animalmenu);
    }
    /**
     Funkcja tworzaca strukture tabeli w oknie wyswietlania tabeli zwierzecia
     */
    public void TableAnimal()  throws SQLException {
        displayanimalsex1.setVisible(false);
        animalchoicecatwalks1.setVisible(false);
        animaldisplaytext1.setVisible(true);
        animaldisplaydate1.setVisible(false);
        displayanimalsex2.setVisible(false);
        animalchoicecatwalks2.setVisible(false);
        animaldisplaytext2.setVisible(true);
        animaldisplaydate2.setVisible(false);
        Connection con = null;
        con = Connect.getConnection();
        TableColumn idanimal = new TableColumn("Id");
        idanimal.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn nameanimal = new TableColumn("Nazwa");
        nameanimal.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn sexanimal = new TableColumn("Plec");
        sexanimal.setCellValueFactory(new PropertyValueFactory<>("sex"));
        TableColumn dateanimal = new TableColumn("Data urodzenia:");
        dateanimal.setCellValueFactory(new PropertyValueFactory<>("date"));
        TableColumn wybiegi = new TableColumn("Wybiegi");
        wybiegi.setCellValueFactory(new PropertyValueFactory<>("wybiegi"));
        TableColumn grupa = new TableColumn("Grupa");
        grupa.setCellValueFactory(new PropertyValueFactory<>("group"));
        grupa.setMinWidth(300);
        tabledisplayanimal.getColumns().addAll(idanimal, nameanimal, sexanimal, dateanimal, wybiegi, grupa);

        Animal animal = null;
        PreparedStatement prsm = con.prepareStatement("select * from Zwierzeta");
        ResultSet rsanimal = prsm.executeQuery();
        while (rsanimal.next()) {
            animal = new Animal(rsanimal.getInt(1), rsanimal.getString(2), rsanimal.getString(3), rsanimal.getDate(5), rsanimal.getString(6), rsanimal.getString(4));
            tabledisplayanimal.getItems().add(animal);

        }
        InsertAnimal(0,0, 0,"", "");
        AnimalFiltr();
    }
    /**
     Funkcja ustawiajace kontrolki do filtrowania tabeli zwierzecia
     */
    public void AnimalFiltr() throws SQLException {
        animaldisplayfiltr1.getItems().add("-");
        animaldisplayfiltr1.setValue("-");
        animaldisplayfiltr1.getItems().add("nazwa");
        animaldisplayfiltr1.getItems().add("plec");
        animaldisplayfiltr1.getItems().add("gromada");
        animaldisplayfiltr1.getItems().add("wybieg");;
        animaldisplayfiltr1.getItems().add("urodzony po");
        animaldisplayfiltr1.getItems().add("urodzony przed");

        animaldisplayfiltr2.getItems().add("-");
        animaldisplayfiltr2.setValue("-");
        animaldisplayfiltr2.getItems().add("lub");
        animaldisplayfiltr2.getItems().add("i");

        animaldisplayfiltr3.getItems().add("-");
        animaldisplayfiltr3.setValue("-");
        animaldisplayfiltr3.getItems().add("nazwa");
        animaldisplayfiltr3.getItems().add("plec");
        animaldisplayfiltr3.getItems().add("gromada");
        animaldisplayfiltr3.getItems().add("wybieg");;
        animaldisplayfiltr3.getItems().add("urodzony po");
        animaldisplayfiltr3.getItems().add("urodzony przed");
        displayanimalsex1.getItems().add("Samiec");
        displayanimalsex1.getItems().add("Samica");
        displayanimalsex1.setValue("Samiec");
        displayanimalsex2.getItems().add("Samiec");
        displayanimalsex2.getItems().add("Samica");
        displayanimalsex2.setValue("Samiec");
        Connection con = null;
        con = Connect.getConnection();
        PreparedStatement prsm = con.prepareStatement("SELECT * FROM Wybiegi");
        ResultSet rsanimal = (prsm.executeQuery());
        while (rsanimal.next()) {
            animalchoicecatwalks1.getItems().add(rsanimal.getString(2) + "(id: " + rsanimal.getInt(1) + ")");
        }
        rsanimal = (prsm.executeQuery());
        while (rsanimal.next()) {
            animalchoicecatwalks2.getItems().add(rsanimal.getString(2) + "(id: " + rsanimal.getInt(1) + ")");
        }
        animaldisplayfiltr1.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldV, newV) -> {
                    int t = animaldisplayfiltr1.getSelectionModel().getSelectedIndex();

                    if(t == 2){
                        displayanimalsex1.setVisible(true);
                        animalchoicecatwalks1.setVisible(false);
                        animaldisplaytext1.setVisible(false);
                        animaldisplaydate1.setVisible(false);
                    }
                    else if(t == 4){
                        displayanimalsex1.setVisible(false);
                        animalchoicecatwalks1.setVisible(true);
                        animaldisplaytext1.setVisible(false);
                        animaldisplaydate1.setVisible(false);
                    }
                    else if(t == 5 || t == 6) {
                        displayanimalsex1.setVisible(false);
                        animalchoicecatwalks1.setVisible(false);
                        animaldisplaytext1.setVisible(false);
                        animaldisplaydate1.setVisible(true);
                    }
                    else{
                        displayanimalsex1.setVisible(false);
                        animalchoicecatwalks1.setVisible(false);
                        animaldisplaytext1.setVisible(true);
                        animaldisplaydate1.setVisible(false);
                    }
                });
        animaldisplayfiltr3.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldV, newV) -> {
                    int t = animaldisplayfiltr3.getSelectionModel().getSelectedIndex();
                    if(t == 2){
                        displayanimalsex2.setVisible(true);
                        animalchoicecatwalks2.setVisible(false);
                        animaldisplaytext2.setVisible(false);
                        animaldisplaydate2.setVisible(false);
                    }
                    else if(t == 4){
                        displayanimalsex2.setVisible(false);
                        animalchoicecatwalks2.setVisible(true);
                        animaldisplaytext2.setVisible(false);
                        animaldisplaydate2.setVisible(false);
                    }
                    else if(t == 5 || t == 6) {
                        displayanimalsex2.setVisible(false);
                        animalchoicecatwalks2.setVisible(false);
                        animaldisplaytext2.setVisible(false);
                        animaldisplaydate2.setVisible(true);
                    }
                    else{
                        displayanimalsex2.setVisible(false);
                        animalchoicecatwalks2.setVisible(false);
                        animaldisplaytext2.setVisible(true);
                        animaldisplaydate2.setVisible(false);
                    }
                });
    }
    /**
     Funkcja obslugujaca nacisniecie przycisku filtrowania danych  z tabeli
     */
    public void pressAnimalFiltrButton(ActionEvent event) throws SQLException, ClassNotFoundException, ParseException {
        int id1 = animaldisplayfiltr1.getSelectionModel().getSelectedIndex();
        int id2 = animaldisplayfiltr3.getSelectionModel().getSelectedIndex();
        Connection con = null;
        con = Connect.getConnection();
        String act1 = null;
        String act2 = null;
        if(id1 == 2){
            if(displayanimalsex1.getSelectionModel().getSelectedItem().toString().equals("Samiec")){
                act1 = "M";
            }
            else{
                act1 = "K";
            }
        }
        else if(id1 == 4){
            PreparedStatement prsm = con.prepareStatement("SELECT * FROM Wybiegi");
            ResultSet rsanimal = (prsm.executeQuery());
            while (rsanimal.next()) {
                if(animalchoicecatwalks1.getSelectionModel().getSelectedItem().equals(rsanimal.getString(2) + "(id: " + rsanimal.getInt(1) + ")")) {
                    act1 = String.valueOf(rsanimal.getInt(1));
                }
            }
        }
        else if(id1 == 5 || id1 == 6){
            act1 = animaldisplaydate1.getValue().toString();
        }
        else{
            act1 = animaldisplaytext1.getText();
        }
        if(id2 == 2){
            if(displayanimalsex2.getSelectionModel().getSelectedItem().toString().equals("Samiec")){
                act2 = "M";
            }
            else{
                act2 = "K";
            }
        }
        else if(id2 == 4){
            PreparedStatement prsm = con.prepareStatement("SELECT * FROM Wybiegi");
            ResultSet rsanimal = (prsm.executeQuery());
            while (rsanimal.next()) {
                if(animalchoicecatwalks2.getSelectionModel().getSelectedItem().equals(rsanimal.getString(2) + "(id: " + rsanimal.getInt(1) + ")")) {
                    act2 = String.valueOf(rsanimal.getInt(1));
                }
            }
        }
        else if(id2 == 5 || id2 == 6){
            act2 = animaldisplaydate2.getValue().toString();
        }
        else{
            act2 = animaldisplaytext2.getText();
        }
        InsertAnimal(animaldisplayfiltr1.getSelectionModel().getSelectedIndex(), animaldisplayfiltr2.getSelectionModel().getSelectedIndex(), animaldisplayfiltr3.getSelectionModel().getSelectedIndex(), act1, act2);
    }

    /**

     Funkcja dodajaca filtrowane dane do tabelis
     */
    public void InsertAnimal(int act1 , int act2, int act3, String t1 , String t2) throws SQLException{
        tabledisplayanimal.getItems().clear();
        Connection con = null;
        con = Connect.getConnection();
        Animal animal = null;
        ResultSet rsanimal;
        String query = "";
        if((act1 == 0 || t1.isEmpty())) {
            query = "SELECT A.*,B.nazwa,B.id FROM Zwierzeta A JOIN Wybiegi B ON A.wybiegi_id = B.id";
        }
        else if(act1 == 1){
            query = "SELECT A.*,B.nazwa,B.id FROM Zwierzeta A JOIN Wybiegi B ON A.wybiegi_id = B.id WHERE A.nazwa LIKE ?";
            t1 = "%" + t1 + "%";
        }
        else if(act1 == 2){
            query = "SELECT A.*,B.nazwa,B.id FROM Zwierzeta A JOIN Wybiegi B ON A.wybiegi_id = B.id WHERE A.plec LIKE ?";
        }
        else if(act1 == 3){
            query = "SELECT A.*,B.nazwa,B.id FROM Zwierzeta A JOIN Wybiegi B ON A.wybiegi_id = B.id WHERE A.gromada LIKE ?";
            t1 = "%" + t1 + "%";
        }
        else if(act1 == 4){
            query = "SELECT A.*,B.nazwa,B.id FROM Zwierzeta A JOIN Wybiegi B ON A.wybiegi_id = B.id WHERE A.wybiegi_id = ?";
        }
        else if(act1 == 5){
            query = "SELECT A.*,B.nazwa,B.id FROM Zwierzeta A JOIN Wybiegi B ON A.wybiegi_id = B.id WHERE A.data_urodzenia >= ?";
        }
        else if(act1 == 6){
            query = "SELECT A.*,B.nazwa,B.id FROM Zwierzeta A JOIN Wybiegi B ON A.wybiegi_id = B.id WHERE A.data_urodzenia <= ?";
        }
        if(act1 != 0 && act2 != 0 && act3 != 0 && !t2.isEmpty()){
            if(act2 == 1){
                query += " OR ";
            }
            else{
                query += " AND ";
            }
            if(act3 == 1){
                query += "A.nazwa LIKE ?";
                t2 = "%" + t2 + "%";
            }
            else if(act3 == 2){
                query += "A.plec LIKE ?";
                t2 = "%" + t2 + "%";
            }
            else if(act3 == 3){
                query += "A.gromada LIKE ?";
                t2 = "%" + t2 + "%";
            }
            else if(act3 == 4){
                query += "A.wybiegi_id = ?";
            }
            else if(act3 == 5){
                query += "A.data_urodzenia >= ?";
            }
            else if(act3 == 6){
                query += "A.data_urodzenia <= ?";
            }
        }
        PreparedStatement prsm = con.prepareStatement(query);
        if(act1 != 0 && !t1.isEmpty()){
            if(act1 == 5 || act1 == 6){
                Date date = Date.valueOf(t1);
                prsm.setDate(1,date);
            }
            else if(act1 == 4){
                prsm.setInt(1,  Integer.parseInt(t1));
            }
            else {
                prsm.setString(1, t1);
            }
            if((act2 != 0) && (act3 != 0) && !t2.isEmpty()) {
                if(act3 == 5 || act3 == 6){
                    Date date = Date.valueOf(t2);
                    prsm.setDate(2,date);
                }
                else if(act3 == 4){
                    prsm.setInt(2,  Integer.parseInt(t2));
                }
                else {
                    prsm.setString(2, t2);
                }
            }
        }
        rsanimal = prsm.executeQuery();
        int t = 0;
        while (rsanimal.next()) {
            t++;
            if (rsanimal.getString(3) != null) {
                if (rsanimal.getString(3).equals("M")) {
                    animal = new Animal(rsanimal.getInt(1), rsanimal.getString(2), "Samiec", rsanimal.getDate(5), rsanimal.getString(7) + "(id:" + rsanimal.getString(8) + ")" ,rsanimal.getString(4));
                } else {
                    animal = new Animal(rsanimal.getInt(1), rsanimal.getString(2), "Samica", rsanimal.getDate(5), rsanimal.getString(7) + "(id:" + rsanimal.getString(8) + ")",rsanimal.getString(4));
                }

                tabledisplayanimal.getItems().add(animal);
            }
        }
    }
}
