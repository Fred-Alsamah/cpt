/**
 * @author: Fred
 * date: 05/11/2024
 * Blackjack cuz im a stupid degenerate gambler
 */

 import java.util.ArrayList;

public class Blackjack {
    public static void main(String[] args) {
        Dealer s = new Dealer();
        System.out.println(s.dealer);

        Player x = new Player(23);
        System.out.println(x.player);
        System.out.println(x.bet);
        System.out.println(x.balance);

    }
}

class Deck{
    
    ArrayList<Integer> deck = new ArrayList<Integer>();
    
    private int two;
    private int three;
    private int four;
    private int five;
    private int six;
    private int seven;
    private int eight;
    private int nine;
    private int ten;
    
    
    //use a stack here implemented with what Mr.Habib said, and pop pop pop and then deck will decrease, and this way you do not have to get the same car twice.
    //variable for every single card in the deck
    // hitmethod standmethod
}
    

class Dealer{
    protected int dealer;
    
    public Dealer(){
        this.dealer = 2 + (int)(Math.random() * 20);
    }
}

class Player{
    protected int balance;
    protected int bet;
    protected int player;

    public Player(){
        this.bet = 0;
        this.balance = 100;
        this.player = 2 + (int)(Math.random() * 20);
    }

    public Player(int bet){
        this.bet = bet;
        this.balance = 100 - bet;
        this.player = 2 + (int)(Math.random() * 20);
    }

}

