import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class GameTest {

    @Test
    void testCalculateScore() {
        Game game = new Game();
        
        ArrayList<String> hand = new ArrayList<>();
        hand.add("10 of Hearts");
        hand.add("Ace of Spades");
        game.playerHand.hand = hand;
        assertEquals(21, game.playerHand.calculateScore(), "Score with an Ace and a 10 should be 21");
        
        hand.clear();
        hand.add("Ace of Spades");
        hand.add("Ace of Hearts");
        hand.add("9 of Diamonds");
        game.playerHand.hand = hand;
        assertEquals(21, game.playerHand.calculateScore(), "Score with two Aces and a 9 should be 21");

        hand.clear();
        hand.add("Jack of Diamonds");
        hand.add("7 of Clubs");
        game.playerHand.hand = hand;
        assertEquals(17, game.playerHand.calculateScore(),"Score with a Jack and 7 should be 17");
    }

    @Test
    void testCreateDeck() {
        Game game = new Game();
        ArrayList<String> deck = game.deck.deck;
        assertEquals(52, deck.size(), "Deck size should be 52");
        assertTrue(deck.contains("Ace of Hearts"), "Deck should contain Ace of Hearts");
        assertTrue(deck.contains("King of Spades"), "Deck should contain King of Spades");
    }

    @Test
    void testResetHands() {
        Game game = new Game();
        game.playerHand.hand.add("Ace of Spades");
        game.dealerHand.hand.add("King of Hearts");
        game.resetHands();

        assertTrue(game.playerHand.hand.isEmpty(), "Player hand should be empty after reset");
        assertTrue(game.dealerHand.hand.isEmpty(), "Dealer hand should be empty after reset");
    }

    @Test
    void testInitialDeal() {
        Game game = new Game();
        game.resetHands();
        game.initialDeal();

        assertEquals(2, game.playerHand.hand.size(), "Player should have 2 cards after initial deal");
        assertEquals(2, game.dealerHand.hand.size(), "Dealer should have 2 cards after initial deal");
    }

    @Test
    void testGetValidBet() {
        // This test would involve mocking `System.in` or capturing console input.
        // Example usage could involve a library like Mockito for simulation.
    }

    @Test
    void testPlayerTurn() {
        // This test involves simulating user input for "hit" or "stand".
        // Could also use a mocking framework to emulate user choices.
    }

    @Test
    void testDealerTurn() {
        Game game = new Game();
        game.resetHands();
        game.dealerHand.hand.add("10 of Hearts");
        game.dealerHand.hand.add("6 of Diamonds");
        game.dealerTurn(10);

        assertTrue(game.dealerHand.calculateScore() >= 17, "Dealer should stand with a score of 17 or higher");
    }

    @Test
    void testDetermineWinner() {
        Game game = new Game();
        game.playerHand.hand.add("10 of Hearts");
        game.playerHand.hand.add("7 of Diamonds");
        game.playerHand.hand.add("9 of Clubs");
        game.playerHand.hand.add("8 of Hearts");

        game.determineWinner(20);
        assertEquals(120, game.playerBalance, "Player balance should increase on win");
    }
}
