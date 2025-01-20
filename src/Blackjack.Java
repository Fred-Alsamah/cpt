import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
  
/**
 * @author Fred Alsamah
 * date: 05/11/2024
 * Blackjack cuz im a stupid degenerate gambler
*/
 
public class Blackjack {
    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
 
/**
* Manages the flow of the Blackjack game.
*/
class Game {
 
    protected Deck deck;
    protected Hand playerHand;
    protected Hand dealerHand;
    private Scanner scanner;
    protected int playerBalance;
    private String lastResult;

    public Game() {
        this.scanner = new Scanner(System.in);
        this.deck = new Deck();
        this.playerHand = new Hand();
        this.dealerHand = new Hand();
        this.playerBalance = 100;
        this.lastResult = "No games played yet.";
    }

    public void play() {
        System.out.println("Welcome to Blackjack!");

        while (playerBalance > 0) {
            System.out.println("Your current balance: $" + playerBalance);
            System.out.println("Last game result: " + lastResult);

            System.out.print("Enter your bet amount: ");
            int bet = getValidBet();

            resetHands();
            initialDeal();

            System.out.println("Your hand: " + playerHand + " (Score: " + playerHand.calculateScore() + ")");
            System.out.println("Dealer's face-up card: " + dealerHand.getFirstCard());

            boolean playerBusted = playerTurn();

            if (!playerBusted) {
                dealerTurn(bet);
            } else {
                System.out.println("Dealer wins.");
                lastResult = "You lost $" + bet + ".";
                playerBalance -= bet;
            }

            if (playerBalance <= 0) {
                System.out.println("Brokie no cash. Get Out!");
                break;
            }

            System.out.print("Do you want to play another game? (y/n): ");
            String choice = scanner.nextLine().toLowerCase();

            if (choice.equals("n")) {
                System.out.println("Thank you for playing! Your final balance: $" + playerBalance);
                break;
            }
        }

        scanner.close();
    }

    public void resetHands() {
        if (deck.getSize() < 15) {
            deck.resetDeck();
        }
        playerHand.clear();
        dealerHand.clear();
    }

    public void initialDeal() {
        playerHand.addCard(deck.drawCard());
        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());
    }

    private boolean playerTurn() {
        while (true) {
            System.out.print("Do you want to hit or stand? (h/s): ");
            String choice = scanner.nextLine().toLowerCase();

            if (choice.equals("h")) {
                playerHand.addCard(deck.drawCard());
                int playerScore = playerHand.calculateScore();
                System.out.println("Your hand: " + playerHand + " (Score: " + playerScore + ")");

                if (playerScore > 21) {
                    System.out.println("You busted ;)");
                    return true;
                }
            } else if (choice.equals("s")) {
                return false;
            } else {
                System.out.println("Invalid choice. Please enter 'h' to hit or 's' to stand.");
            }
        }
    }

    public void dealerTurn(int bet) {
        System.out.println("Dealer's turn...");
        System.out.println("Dealer's hand: " + dealerHand + " (Score: " + dealerHand.calculateScore() + ")");

        while (dealerHand.calculateScore() < 17) {
            dealerHand.addCard(deck.drawCard());
            System.out.println("Dealer hits. New hand: " + dealerHand + " (Score: " + dealerHand.calculateScore() + ")");
        }

        if (dealerHand.calculateScore() > 21) {
            System.out.println("Dealer busted;) You win!");
            lastResult = "You won $" + bet + "!";
            playerBalance += bet;
        } else {
            determineWinner(bet);
        }
    }

    public void determineWinner(int bet) {
        int playerScore = playerHand.calculateScore();
        int dealerScore = dealerHand.calculateScore();

        System.out.println("Final Scores - Player: " + playerScore + ", Dealer: " + dealerScore);

        if (playerScore > dealerScore) {
            System.out.println("You win!");
            lastResult = "You won $" + bet + "!";
            playerBalance += bet;
        } else if (playerScore < dealerScore) {
            System.out.println("Dealer wins!");
            lastResult = "You lost $" + bet + ".";
            playerBalance -= bet;
        } else {
            System.out.println("It's a tie!");
            lastResult = "It's a tie. No money lost.";
        }
    }

    private int getValidBet() {
        while (true) {
            int bet = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (bet > 0 && bet <= playerBalance) {
                return bet;
            } else {
                System.out.print("Invalid bet amount. Enter a value between 1 and $" + playerBalance + ": ");
            }
        }
    }
}

/**
 * Handles the creation and management of the card deck.
*/
class Deck {
    protected ArrayList<String> deck;

    public Deck() {
        resetDeck();
    }

    public void resetDeck() {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        deck = new ArrayList<>();

        for (String suit : suits) {
            for (String rank : ranks) {
                deck.add(rank + " of " + suit);
            }
        }

        Collections.shuffle(deck);
    }

    public String drawCard() {
        return deck.remove(0);
    }

    public int getSize() {
        return deck.size();
    }
}

/**
 * Represents a player's or dealer's hand.
*/
class Hand {
    protected ArrayList<String> hand;

    public Hand() {
        this.hand = new ArrayList<>();
    }

    public Hand( ArrayList<String> hand) {
        this.hand = hand;
    }

    public void addCard(String card) {
        hand.add(card);
    }

    public void clear() {
        hand.clear();
    }

    public int calculateScore() {
        int score = 0;
        int aces = 0;

        for (String card : hand) {
            String rank = card.split(" ")[0];
            if (rank.matches("\\d+")) {
                score += Integer.parseInt(rank);
            } else if (rank.equals("Jack") || rank.equals("Queen") || rank.equals("King")) {
                score += 10;
            } else if (rank.equals("Ace")) {
                score += 11;
                aces++;
            }
        }

        while (score > 21 && aces > 0) {
            score -= 10;
            aces--;
        }

        return score;
    }

    public String getFirstCard() {
        return hand.get(0);
    }

    @Override
    public String toString() {
        return hand.toString();
    }
}
 