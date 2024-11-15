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
            processInput(userInput);
        }
        if (!resident.isConscious()) printRegret();
        scanner.close();
    }

    private void fightOneRound() {
        if (!currentLocatione.equals(BURGLAR_LOCATION)) {
            System.out.println("There is no enemy to fight at this location!");
            return;
        }
        //player och monster kan nås här eftersom de är klassattribut
        executeAttack(resident, burglar);
        if (burglar.isConscious()) {
            executeAttack(burglar, resident);
        }
    }

    private void printWelcomeMenu() {
        System.out.println("***   Welcome to Adventure   ***\n");
        System.out.println("You fallen to sleep at the usual place in the tv-couch in the living-room");
        System.out.println("The living-room is the central room of your apartment and it is easy to crash there");
        System.out.println("before entering your bedroom when you comes home through the hall. From the living-room");
        System.out.println("you may also enter the kitchen and your home office.\n");
        System.out.println("Suddenly you wake up of a load metallic crash in the hall. You open one eye and see a");
        System.out.println("strange man in the kitchen searching in the pockets of your coat. Now you may choose:");
    }

    private void livingroom() {
        if (!currentLocatione.equals(LIVING_ROOM)) {
            System.out.println("Living room: <go hall | go bedroom | go kitchen | go office>");
            currentLocatione = LIVING_ROOM;
        } else {
            System.out.println("You current position is already the living room - please try again");
        }
    }

    private void hall() {
        if (currentLocatione.equals(LIVING_ROOM)) {
            currentLocatione = HALL;
            System.out.println("You are entering the hall. A nasty thief is ");
            if (burglar.isConscious()) {
                System.out.println("searching the pockets of your coat. Enter the command: fight -if you dare...");
            } else {
                System.out.println("laying on the floor unconscious");
            }
        } else {
            System.out.println("Illegal move -try again");
        }
    }

    private void kitchen() {
        if (currentLocatione.equals(LIVING_ROOM)) {
            currentLocatione = KITCHEN;
            if (fryingPanFound) {
                System.out.println("You run into the kitchen but you have already fetched the frying pan");
            } else {
                System.out.println("You run into the kitchen and see your frying pan near your stove");
                System.out.println("Enter command: fetch frying pan -to pick it up");
            }
        } else {
            System.out.println("Illegal move -try again");
        }
    }

    private void fetchFryingPan() {
        if (!fryingPanFound) {
            if (currentLocatione.equals(KITCHEN)) {
                System.out.println("You are fetching the frying pan from the kitchen and may now hit harder");
                fryingPanFound = true;
                // Öka spelarens damage när stekpannan plockas upp
                resident.setDamage(resident.getDamage() + 3);
            } else {
                System.out.println("There is no frying pan in this room?");
            }
        } else {
            System.out.println("You have already fetched the frying pan");
        }
    }

    private void bedroom() {
        if (currentLocatione.equals(LIVING_ROOM)) {
            currentLocatione = BEDROOM;
            System.out.println("You are entering the bedroom. This will not help you in present state!");
        } else {
            System.out.println("Illegal move -try again");
        }
    }

    private void office() {
        if (currentLocatione.equals(LIVING_ROOM)) {
            currentLocatione = OFFICE_ROOM;
            System.out.print("You are in your office room. You look around");
            //if (currentLocatione.equals(BURGLAR_LOCATION)) {
            //    System.out.println("A nasty monster! Type fight if you dare!?");
            //}
            if (!burglar.isConscious()) {
                System.out.println(" and see a telephone at the table.");
                System.out.println("The burglar lies unconscious in the hall.");
                System.out.println("To get help, type: call the police");
            } else {
                System.out.println(" but see nothing hard to hit the burglar with.");
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
                running = false;
            } else {
                System.out.println("The burglar is conscious!!!");
            }
        } else {
            System.out.println("There is no phone in this room: " + currentLocatione);
        }
    }

    private String getUserInput() {
        return scanner.nextLine();
    }

    private void processInput(String input) {
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
                running = false;
            }
            default -> System.out.println("Bad input. Please try again.");
        }
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

    void printRegret(){
        System.out.println("\n\nSorry Burje has left with your most valueful assets and is far away by now");
        System.out.println("when you waking up having severe headache. Needless to say you lost... sorry!");
    }
}
