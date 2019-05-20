package sample;


import java.sql.Date;

public class Feed {
    /**
     Klasa karmienie przechowujace informacje o karmieniu
     */
    private int id;
    private int ilosc;
    private Date date;
    private String zwierzeta_id;
    private String pracownicy_id;
    private String pokarm_id;

    /**
     *
     * @param id przechowujace id karmienia
     * @param date przechowujace date karmienia
     * @param ilosc przechowujace ilosc uzytego jedzenia do karmienia
     * @param zwierzeta_id przechowujace id nakarmionego zwierzeta
     * @param pracownicy_id przechowujace id pracownika ktory nakarmil
     * @param pokarm_id przechowujace id pokarmu ktorym nakarmiono
     */
    Feed(int id, Date date, int ilosc, String zwierzeta_id, String pracownicy_id, String pokarm_id){
        this.id = id;
        this.date = date;
        this.ilosc = ilosc;
        this.zwierzeta_id = zwierzeta_id;
        this.pracownicy_id = pracownicy_id;
        this.pokarm_id = pokarm_id;
    }
    public int getId() {
        return id;
    }
    public Date getDate() {
        return date;
    }
    public int getIlosc(){
        return ilosc;
    }
    public String getZwierzeta_id() {
        return zwierzeta_id;
    }
    public String getPracownicy_id(){
        return pracownicy_id;
    }
    public String getPokarm_id() {
        return pokarm_id;
    }
}