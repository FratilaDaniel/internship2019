package application.model;

import java.time.LocalTime;
public class Atm {
    private int id;
    private float amountAvailable = 5000;
    private LocalTime openingTime;
    private LocalTime closingTime;

    public Atm(){

    }

    public Atm(int id, LocalTime openingTime, LocalTime closingTime){
        this.id = id;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public float getAmountAvailable(){
        return amountAvailable;
    }

    public boolean isOpen(LocalTime currentTime){
        return (currentTime.isAfter(openingTime)) && (currentTime.isBefore(closingTime));
    }

    public String toString(){
        String status = "";
        status =
                "Atm id: " + id + "; "
                + "Amount available: " + amountAvailable + "; "
                + "Opening time: " + openingTime.toString() + "; "
                + "Closing time: " + closingTime.toString() + "; ";
        return status;
    }
}
