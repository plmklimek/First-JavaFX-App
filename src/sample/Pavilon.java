package sample;

/**
 Klasa pawilon przechowujaca informacje o pawilonach
 */
public class Pavilon {
    private int id;
    private String name;
    private String desc;

    /**
     *
     * @param id przechowuje id pawilonu
     * @param name przechowuje nazwe pawilonu
     * @param desc przechowuje opis pawilonu
     */
    Pavilon(int id, String name, String desc){
        this.id = id;
        this.name = name;
        this.desc = desc;
    }
    public int getId(){return id;}
    public String getName(){return name;}
    public String getDesc(){return desc;}
}