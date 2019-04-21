package application.seed;

import application.model.Atm;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AtmSeed {

    private final int MAX_SIZE = 10; // maximum number of ATMs in the city
    private List<Atm> listOfAtms; // list of ATMs existent
    private Map<Atm, Integer> mapOfDistances; // distances from user to each ATM existent
    private int[][] atmGraph; // distances from each ATM to another

    public AtmSeed(){
        listOfAtms = new ArrayList<Atm>();
        mapOfDistances = new HashMap<Atm, Integer>();
        atmGraph = new int[MAX_SIZE][MAX_SIZE];

        populateAtms();
        populateDistancesFromUserToAtms();
        populateDistancesBetweenAtms();
    }

    // method which populates data of atm's as in the given problem
    private void populateAtms(){
        listOfAtms.add(new Atm(1, LocalTime.of(12, 0, 0), LocalTime.of(18, 0, 0)));
        listOfAtms.add(new Atm(2, LocalTime.of(10, 0, 0), LocalTime.of(17, 0, 0)));
        listOfAtms.add(new Atm(3, LocalTime.of(22, 0, 0), LocalTime.of(13, 0, 0)));
        listOfAtms.add(new Atm(4, LocalTime.of(17, 0, 0), LocalTime.of(1, 0, 0)));
    }

    // method which populates data of distances between user and atm's as in the given problem
    private void populateDistancesFromUserToAtms(){
        mapOfDistances.put(listOfAtms.get(0), 5);
        mapOfDistances.put(listOfAtms.get(1),60);
        mapOfDistances.put(listOfAtms.get(2),30);
        mapOfDistances.put(listOfAtms.get(3),45);
    }

    // method which populates data of distances between atm's as in the given problem
    private void populateDistancesBetweenAtms(){
        int newAtmGraph[][] = {
                {0, 40, 40, 45},
                {40, 0, 15, 30},
                {40, 15, 0, 15},
                {45, 30, 15, 0}
        };
        this.atmGraph = newAtmGraph;
    }

    public List<Atm> getListOfAtms(){
        return this.listOfAtms;
    }

    public Map<Atm, Integer> getMapOfDistances(){
        return this.mapOfDistances;
    }

    public int[][] getAtmGraph(){
        return this.atmGraph;
    }
}
