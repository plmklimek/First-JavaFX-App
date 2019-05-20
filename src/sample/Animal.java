package sample;

import java.sql.Date;

public class Animal {
    /**
     Klasa zwierze przechowujaca informacje o zwierzeciu
     */
    private int id;
    private String name;
    private String sex;
    private Date date;
    private String wybiegi;
    private String group;

    /**
     *
     * @param id przechowujace id zwierzecia
     * @param name  przechowujace nazwe zwierzecia
     * @param sex   przechowujace plec zwierzecia
     * @param date  przechowujace date urodzenia zweirzecia
     * @param wybiegi przechowujace id wybiegu w ktorym znajduje sie zwierze
     * @param group przechowujace grupe zwierzecia
     */
    Animal(int id, String name, String sex, Date date, String wybiegi, String group){
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.date = date;
        this.wybiegi = wybiegi;
        this.group = group;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getSex() {
        return sex;
    }
    public Date getDate(){
        return date;
    }
    public String getWybiegi(){
        return wybiegi;
    }
    public String getGroup(){
        return group;
    }
}

