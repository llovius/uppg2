package se.lars.uppgB;

import se.lars.uppgB.model.Burglar;
import se.lars.uppgB.model.Entity;
import se.lars.uppgB.model.Resident;

import java.util.Scanner;


public class Game {
    private static final String HALL = "hall";
    private static final String KITCHEN = "kitchen";
    private static final String BEDROOM = "bedroom";
    private static final String OFFICE_ROOM = "office room";
    private static final String LIVING_ROOM = "living room";
    private static final String START = "start";
    private static final String BURGLAR_LOCATION = HALL;

    private boolean running = true;
    private boolean fryingPanFound = false;
    private String currentLocatione = START;
    private static final Scanner scanner = new Scanner(System.in);
    private Resident resident;
    private Burglar burglar;

    public void start() {
        burglar = new Burglar(12, 4, "Burje");
        resident = new Resident(12, 3, "Rese");
        printWelcomeMenu();
        livingroom();
        while (running && resident.isConscious()) {
            String userInput = getUserInput().toLowerCase();
            running = processInput(userInput);
        }
        scanner.close();
    }

    private void fightOneRound() {
        if (!currentLocatione.equals(BURGLAR_LOCATION)) {
            System.out.println("There is no enemy to fight at this location!");
            return;
        }
        //player och monster kan nås här eftersom de är klassattribut
        executeAttack(resident,burglar);
        if (burglar.isConscious()) {
            executeAttack(burglar,resident);
        }
    }

    private void printWelcomeMenu() {
        System.out.println("Welcome to Adventure");
        System.out.println("type go north quit etc");
    }

    private void livingroom() {
        if (!currentLocatione.equals(LIVING_ROOM)) {
            System.out.println("You are in the living room");
            System.out.println("type go bedroom quit etc");
            currentLocatione = LIVING_ROOM;
        } else {
            System.out.println("You current position is already the living room - please try again");
        }
    }

    private void hall() {
        if (currentLocatione.equals(LIVING_ROOM)) {
            currentLocatione = HALL;
            System.out.println("You are entering the hall. A nasty thief, You see...What's next");
        } else {
            System.out.println("Illegal move -try again");
        }
    }

    private void kitchen() {
        if (currentLocatione.equals(LIVING_ROOM)) {
            currentLocatione = KITCHEN;
            if (fryingPanFound) {
                System.out.println("A kitchen with no frying pan...");
            }
            else {
                System.out.println("A kitchen with a frying pan...");
                System.out.println("Enter fetch frying pan to pick it up");
            }
        } else {
            System.out.println("Illegal move -try again");
        }
    }

    private void fetchFryingPan(){
        if (!fryingPanFound) {
            if (currentLocatione.equals(KITCHEN)) {
                System.out.println("You are fetching the frying pan from the kitchen and may now hit harder");
                fryingPanFound = true;
                // Öka spelarens damage när stekpannan plockas upp
                resident.setDamage(resident.getDamage() + 3);
            }
            else {
                System.out.println("There is no frying pan in this room?");
            }
        }
        else {
            System.out.println("You have already fetched the frying pan");
        }
    }

    private void bedroom() {
        if (currentLocatione.equals(LIVING_ROOM)) {
            currentLocatione = BEDROOM;
            System.out.println("You are entering the bedroom. You see...What's next");
        } else {
            System.out.println("Illegal move -try again");
        }
    }

    private void office() {
        if (currentLocatione.equals(LIVING_ROOM)) {
            currentLocatione = OFFICE_ROOM;
            System.out.println("You are in an office room. You see...What's next");
            //if (currentLocatione.equals(BURGLAR_LOCATION)) {
            //    System.out.println("A nasty monster! Type fight if you dare!?");
            //}
            if (!burglar.isConscious()) {
                System.out.println("The burglar i unconscious and you see a telephone at the table");
                System.out.println("to get help: call the police");
            }
        } else {
            System.out.println("Illegal move -try again");
        }
    }

    private void callThePolice() {
        if (currentLocatione.equals(OFFICE_ROOM)) {
            if (!burglar.isConscious()) {
                System.out.print("You are calling the police while the burglar lies ");
                System.out.println("unconscious on the floor of the hall");
                System.out.println("CONGRATULATIONS! You won the game");
                running =false;
            }
            else {
                System.out.println("The burglar is conscious!!!");
            }
        }
        else {
            System.out.println("There is no phone in this room: "+currentLocatione);
        }
    }
    private String getUserInput() {
        return scanner.nextLine();
    }

    private boolean processInput(String input) {
        switch (input) {
            case "go hall" -> hall();
            case "go bedroom" -> bedroom();
            case "go kitchen" -> kitchen();
            case "go office" -> office();
            case "go living-room" -> livingroom();
            case "fetch frying pan" -> fetchFryingPan();
            case "call the police" -> callThePolice();
            case "fight" -> fightOneRound();
            case "quit" -> {
                //returnera false för att avbryta, måsvingar behövs här
                return false;
            }
            default -> System.out.println("Bad input. Please try again.");
        }
        //returnera true för att fortsätta spelet
        return true;
    }
    void executeAttack(Entity attacker, Entity defender) {
        //       1. attacker attackerar defender (via metoden attack)
        attacker.punch(defender);
        System.out.println(attacker.getRole() + " attacks " + defender.getRole());
        if (defender.isConscious()) {
            System.out.println(defender.getRole() + " got the health: " + defender.getHealth());
        } else {
            System.out.println(defender.getRole() + " is unconscious!");
        }
    }
}
