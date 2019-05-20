package sample;

import java.sql.Date;

/**
 Klasa osoba przechowujaca informacje o pracowniku
 */
public class Person {
    private int id;
    private String name;
    private String SecondName;
    private String PhoneNumber;
    private String sex;
    private double salary;
    private String JobPosition;
    private Date date;

    /**
     *
     * @param id przechowujace id pracownika
     * @param name przechowujace imie pracownika
     * @param SecondName przechowujace nazwisko pracownika
     * @param PhoneNumber   przechowujace numer telefonu pracownika
     * @param sex przechowujace plec pracownika
     * @param salary przechowujace pensje pracownika
     * @param JobPosition   przechowujace stanowisko pracownika
     * @param date przechowujace date zatrudnienia pracownika
     */
    Person(int id, String name, String SecondName, String PhoneNumber, String sex, double salary, String JobPosition, Date date){
        this.id = id;
        this.name = name;
        this.SecondName = SecondName;
        this.PhoneNumber = PhoneNumber;
        this.sex = sex;
        this.salary = salary;
        this.JobPosition = JobPosition;
        this.date = date;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getSecondName() {
        return SecondName;
    }
    public String getPhoneNumber() {
        return PhoneNumber;
    }
    public String getSex() {
        return sex;
    }
    public double getSalary() {
        return salary;
    }
    public String getJobPosition() {
        return JobPosition;
    }
    public Date getDate(){
        return date;
    }
}