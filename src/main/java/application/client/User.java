package application.client;

import application.model.Card;
import application.seed.CardSeed;

import java.util.*;

public class User {

    private List<Card> listOfCards;
    private float objectInitialCost;
    private float objectTotalCost;

    public User(){
        CardSeed cardSeed = new CardSeed();
        listOfCards = cardSeed.populateCards();
        objectInitialCost = 10000;
        objectTotalCost = objectInitialCost * 1.19f;
    }


    public float getObjectInitialCost(){
        return this.objectInitialCost;
    }

    public float getObjectTotalCost(){
        return this.objectTotalCost;
    }

    public List<Card> getListOfCards(){
        return this.listOfCards;
    }
}
