package application;

import application.controller.Controller;

import java.util.Date;

public class App {

    public static void main(String args[]){

        Controller controller = new Controller(new Date());
        controller.work();
        controller.printStatus();
        // the user has all the money on one card and TVA and potential fees are computed.
        // the online transaction has not been made yet (i.e. amount of one of the cards >= value of the product)
        // modify the values in AtmSeed and CardSeed to get new results
    }
}
/*
    Best case scenario:
        The user has enough money on a single card to make the transaction.

    Normal cases:
        The user does not have enough money on a single card so he needs to transfer money from one to another
        Strategy:
            1. Find the best ATM (best means that it is open, it has an amount of money > 0 and it is the closest to the user)
            2. Transfer money (max amount available or withdraw limit) from the card with the smallest fee to the card with the highest amount of money
            3. Repeat step 2 with the card with the next smallest fee if the required amount is not enough to pay the product

        Convenient example:
            User want to buy a product which costs him 5000 lei (including TVA).
            User has a card (A) with 4900 lei, 0.5% fee 2000 lei withdraw limit and another one (B) with 1000 lei, 0.1% fee and 2000 lei withdraw limit.
            User finds an ATM with 5000 lei in it. (closest, open)
            User withdraws 100 lei from card B. He pays 0.1 lei fee.
            User deposits 100 lei to card A. User can make the online transaction.

        Less convenient example
            User want to buy a product which costs him 5000 lei (including TVA).
            User has a card (A) with 4900 lei, 0.5% fee 2000 lei withdraw limit and another one (B) with 1000 lei, 0.1% fee and 2000 lei withdraw limit.
            User finds an ATM with 20 lei in it. (closest, open)
            Strategy:
                1. Withdraw max amount from B (20 lei). => Atm has 0 lei
                2. Deposit max amount in A (20 lei). => Atm has 20 lei
                3. Repeat steps 1 and 2 until the user has enough money on one card.

                Step        Total in ATM    Total in A (destination)      Total in B (source)      Cash

                Initially       20              4900                            1000                0
                1               0               4900                            979.98              20
                2               20              4920                            979.98              0
                3               0               4920                            959.96              20
                4               20              4940                            959.96              0
                5               0               4940                            939.94              20
                6               20              4960                            939.94              0
                7               0               4960                            919.92              20
                8               20              4980                            919.92              0
                9               0               4980                            899.90              20
                10              20              5000                            899.90              0
 */