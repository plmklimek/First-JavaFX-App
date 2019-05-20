package sample;

/**
 Klasa jedzenie przechowujace informacje o jedzeniu
 */
public class Food {
    private int id;
    private String name;
    private int ilosc;

    /**
     *
     * @param id przechowujace id jedzenia
     * @param name przechowujace nazwe jedzenia
     * @param ilosc przechowujace ilosc jedzenia
     */
    Food(int id, String name, int ilosc){
        this.id = id;
        this.name = name;
        this.ilosc = ilosc;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getIlosc(){
        return ilosc;
    }
}