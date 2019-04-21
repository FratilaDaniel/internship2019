package application.model;

import java.util.Date;

public class Card {
    private CardType type;
    private float feePercentage;
    private float withdrawLimit;
    private Date expirationDate;
    private float amountAvailable;

    public Card(){

    }

    public Card(CardType type, float feePercentage, float withdrawLimit, Date expirationDate, float amountAvailable){
        this.type = type;
        this.feePercentage = feePercentage;
        this.withdrawLimit = withdrawLimit;
        this.expirationDate = expirationDate;
        this.amountAvailable = amountAvailable;
    }

    public CardType getCardType(){
        return this.type;
    }

    public float getFeePercentage(){
        return this.feePercentage;
    }


    public float getFeeValue(float amount){
        return amount * this.feePercentage / 100;
    }

    public float getWithdrawLimit(){
        return this.withdrawLimit;
    }

    public float getAmountAvailable() {
        return this.amountAvailable;
    }

    public void setAmountAvailable(float newAmount) {
        this.amountAvailable = newAmount;
    }

    public boolean isCardValid(Date currentDate){
        return currentDate.getTime() < expirationDate.getTime();
    }

    public String toString(){
        String status = "";
        status = "Card type: " + type.toString() + "; "
                +"Fee percentage: " + feePercentage + "%; "
                +"Withdraw limit: " + withdrawLimit + "; "
                +"Expiration date: " + expirationDate.toString() + "; "
                +"Amount available: " + amountAvailable + "; ";
        return status;
    }
}
