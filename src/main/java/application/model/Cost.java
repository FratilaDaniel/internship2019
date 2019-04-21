package application.model;

public class Cost {
    private final float TVA = 0.19f;
    private float tva;
    private float fee;

    // for the cards where withdraw fees do not apply (main card used for payment)
    public Cost(float amount){
        this.tva = amount * TVA;
        this.fee = 0;
    }

    // for the cards where TVA does not apply, only withdraw fees apply
    public Cost(float amount, float feePercentage){
        this.tva = 0;
        this.fee = amount * feePercentage / 100;
    }

    public String toString(){
        String status = "";
        status += "TVA: " + tva + "; Fee value: " + fee;
        return status;
    }

    public float getTVA(){
        return this.tva;
    }

    public float getFee(){
        return this.fee;
    }
}
