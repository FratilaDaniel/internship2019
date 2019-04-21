package application.seed;

import application.model.Card;
import application.model.CardType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CardSeed {

    private List<Card> listOfCards;

    public CardSeed(){
        listOfCards = populateCards();
    }

    // method which populates data of a user's cards as in the given problem
    public List<Card> populateCards() {
        List<Card> listOfCards = new ArrayList<Card>();
        listOfCards.add(new Card(CardType.SILVER, 0.2f, 2000.0f, new Date(), 4000.0f));
        listOfCards.add(new Card(CardType.GOLD, 0.1f, 2000.0f, new Date(), 2000.0f));
        listOfCards.add(new Card(CardType.PLATINUM, 0.3f, 2000.0f, new Date(), 3000.0f));
        listOfCards.add(new Card(CardType.IRIDIUM, 0.2f, 2000.0f, new Date(), 5000.0f));
        listOfCards.add(new Card(CardType.BRONZE, 0.5f, 2000.0f, new Date(), 2500.0f));
        listOfCards.add(new Card(CardType.PREMIUM, 0.15f, 2000.0f, new Date(), 2000.0f));

        return listOfCards;
    }
}
