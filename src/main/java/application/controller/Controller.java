package application.controller;

import application.client.User;
import application.model.Atm;
import application.model.Card;
import application.model.Cost;
import application.seed.AtmSeed;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

public class Controller {

    private Date currentDate;
    private List<Atm> listOfAtms;
    private Map<Atm, Integer> mapOfDistancesFromUser;
    private int[][] atmGraph; // irrelevant
    private LocalTime currentTime;
    private Map<Card, Cost> mapCardCost;

    public Controller(Date currentDate) {
        this.currentDate = currentDate;
        currentTime = LocalTime.of(11, 30);
        AtmSeed atmSeed = new AtmSeed();
        mapCardCost = new HashMap<>();

        listOfAtms = atmSeed.getListOfAtms();
        mapOfDistancesFromUser = atmSeed.getMapOfDistances();
        atmGraph = atmSeed.getAtmGraph();
    }

    public void work() {
        User user = new User();
        // check if amount is available on a single card
        if (!canBuyWithOnlyOneCard(user)) {
            // find the closest atm
            Atm closestAtm = getClosestAtm();
            // select the card with the highest amount of money
            Card cardWithMostMoney = getCardWithMostMoney(user);
            // sort all cards according to their fee percentage (ascending)
            List<Card> copiedListOfCards = user.getListOfCards();
            copiedListOfCards.sort(new Comparator<Card>() {
                @Override
                public int compare(Card o1, Card o2) {
                    if(o1.getFeePercentage() < o2.getFeePercentage()){
                        return -1;
                    }
                    else {
                        return 1;
                    }
                }
            });

            for(Card currentCard: copiedListOfCards ){
                // while the product cost is not completely covered
                // transfer money from the card with the lowest fee to the chosen card

                if(!currentCard.equals(cardWithMostMoney)) {
                    float totalCostForCurrentCard = 0;
                    while (user.getObjectTotalCost() > cardWithMostMoney.getAmountAvailable() // if not enough money on one card
                            && currentCard.getAmountAvailable() > 0) { // and current card has money

                        // get possible amount of money to transfer
                        // as min(entire sum of money on card, withdraw limit, entire sum of money in atm, missing sum)
                        float valueToWithdraw = currentCard.getAmountAvailable() - currentCard.getFeeValue(currentCard.getAmountAvailable());
                        if(valueToWithdraw > currentCard.getWithdrawLimit()){
                            valueToWithdraw = currentCard.getWithdrawLimit();
                        }
                        if(valueToWithdraw > closestAtm.getAmountAvailable()){
                            valueToWithdraw = closestAtm.getAmountAvailable();
                        }
                        if(valueToWithdraw > user.getObjectTotalCost() - cardWithMostMoney.getAmountAvailable()){
                            valueToWithdraw = user.getObjectTotalCost() - cardWithMostMoney.getAmountAvailable();
                        }

                        transferMoney(valueToWithdraw, currentCard.getFeeValue(valueToWithdraw), currentCard, cardWithMostMoney, closestAtm);
                        totalCostForCurrentCard += valueToWithdraw;
                    }

                    Cost currentCost = new Cost(totalCostForCurrentCard, currentCard.getFeePercentage());
                    mapCardCost.put(currentCard, currentCost);
                }
                else{
                    Cost currentCost = new Cost(user.getObjectInitialCost());
                    mapCardCost.put(currentCard, currentCost);
                }
            }
        }
    }

    public void printStatus(){
        float totalFees = 0, totalTVA = 0;
        for(Card c: mapCardCost.keySet()){
            System.out.println(c.toString() + " | " + mapCardCost.get(c).toString());
            totalFees += mapCardCost.get(c).getFee();
            totalTVA += mapCardCost.get(c).getTVA();
        }
        System.out.println("Total fees: " + totalFees);
        System.out.println("Total TVA: " + totalTVA);
    }

    // method which checks if the user can buy the product he wants with money from a single card
    private boolean canBuyWithOnlyOneCard(User user) {
        for (Card card : user.getListOfCards()) {
            if (card.isCardValid(currentDate) && user.getObjectTotalCost() <= card.getAmountAvailable()) {
                System.out.println("User can buy the item with the following card:");
                System.out.println(card.toString());
                return true;
            }
        }
        return false;
    }

    // transferring money from one card to another means
    // go to atm
    // withdraw from atm a certain amount of money from one card
    // deposit into atm the same amount of money to another card
    // => the amount of money in the atm remains the same
    private void transferMoney(float amount, float fee, Card sourceCard, Card destinationCard, Atm atm) {
        sourceCard.setAmountAvailable(sourceCard.getAmountAvailable() - (amount + fee));
        //atm.setAmountAvailable(atm.getAmountAvailable() - amount);
        destinationCard.setAmountAvailable(destinationCard.getAmountAvailable() + amount);
        //atm.setAmountAvailable(atm.getAmountAvailable() + amount);
    }

    // method which returns the closest atm to the user
    // the atm also needs to be open and to have money inside
    private Atm getClosestAtm(){
        if(0 == listOfAtms.size()){
            System.out.println("List of atm's not initialized. Exiting.");
            System.exit(1);
        }

        Atm closestAtm = new Atm();
        boolean closestAtmInitialized = false;
        // find the closest atm to the user, open when he arrives and with some money in it
        for(Atm currentAtm: listOfAtms){
            // if atm is not open when user would arrive, then don't bother taking it into consideration
            if(currentAtm.isOpen(this.currentTime.plus(Duration.ofMinutes(mapOfDistancesFromUser.get(currentAtm))))
                    &&((false == closestAtmInitialized && currentAtm.getAmountAvailable() > 0)
                        ||
                        (true == closestAtmInitialized
                            && mapOfDistancesFromUser.get(currentAtm) < mapOfDistancesFromUser.get(closestAtm)))){
                closestAtm = currentAtm;
                closestAtmInitialized = true;
            }
        }

        if(false == closestAtmInitialized){
            System.out.println("No atm's available right now. Please try again later.");
            System.exit(2);
        }
        return closestAtm;
    }

    private Card getCardWithMostMoney(User user){
        Card bestCard = new Card();
        boolean cardInitialized = false;
        for(Card currentCard: user.getListOfCards()){
            // if the card is not valid then don't bother taking it into consideration
            if(true == currentCard.isCardValid(currentDate)) {
                if (false == cardInitialized) {
                    bestCard = currentCard;
                    cardInitialized = true;
                } else {
                    if(currentCard.getFeePercentage() < bestCard .getFeePercentage()){
                        bestCard = currentCard;
                    }
                }
            }
        }
        if(false == cardInitialized){
            System.out.println("No valid cards available. Exiting.");
            System.exit(3);
        }
        return bestCard;
    }

}
