package sample;

/**
 Klasa przechowujaca informacje o wybiegu
 */
public class CatWalks{
    private int id;
    private String name;
    private String type;
    private String pavilonN;

    /**
     *
     * @param id przechowujace id wybiegu
     * @param name przechowujace nazwe wybiegu
     * @param type przechowujace typ wybiegu
     * @param pavilonN przechowujace id pawilonu w ktorym znajduje sie wybieg
     */
    CatWalks(int id, String name, String type, String pavilonN){
        this.id = id;
        this.name = name;
        this.type = type;
        this.pavilonN = pavilonN;
    }
    public int getId(){return id;}
    public String getName(){return name;}
    public String getType(){return type;}
    public String getPavilonN(){return pavilonN;}


}