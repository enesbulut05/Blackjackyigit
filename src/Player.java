import java.util.ArrayList;
import java.util.List;

public class Player implements GameParticipant {
    private String name;
    private double balance = 500;
    private List<PlayerDeck> playerDecks;

    public Player(String name) {
        this.name = name;
        this.playerDecks = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addBalance(double quantity) {
        this.balance += quantity;
    }

    public void reduceBalance(double quantity) {
        this.balance -= quantity;
    }

    public List<PlayerDeck> getPlayerDecks() {
        return this.playerDecks;
    }

    public PlayerDeck getPlayerDeckByIndex(int index) {
        return this.playerDecks.get(index);
    }

    public void setPlayerDeck(PlayerDeck playerDeck) {
        playerDeck.setPlayer(this);
        this.playerDecks.add(playerDeck);
    }

    @Override
    public void showCards() {
        System.out.println(this.name + " Kartları:");
        for (PlayerDeck playerDeck : this.playerDecks) {
            if (this.playerDecks.size() > 1) {
                System.out.print((this.playerDecks.indexOf(playerDeck) + 1) + ". Deste: ");
            }
            playerDeck.showCards();
        }
    }

    @Override
    public void showResult() {
        for (PlayerDeck playerDeck : this.playerDecks) {
            playerDeck.showResult();
        }
    }
    
    public void resetPlayerDeck() {
    	this.playerDecks.clear();
    }
}
