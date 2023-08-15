import java.util.List;

public class PlayerDeck extends AbstractPlayerDeck {
    private Player player;  
    private double bet = 50;

    public PlayerDeck() {
    }

    public PlayerDeck(List<Card> cards) {
        this.cards = cards;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public double getBet() {
        return this.bet;
    }

    public void setBet(double bet) {
        this.bet = bet;
    }

    @Override
    public void showResult() {
        System.out.println(player.getName() + ":");
        System.out.print(this.toString() + " - Toplam Değer: " + this.cardTotal);
        int vaultDeckTotal = UtilService.getVault().getVaultDeck().getCardTotal();

        if (this.cardTotal > 21 || (this.cardTotal < vaultDeckTotal && vaultDeckTotal<=21)) {
            System.out.print(" --- Kaybetti.");
        } else if (this.cardTotal == vaultDeckTotal) {
            player.addBalance(bet);
            System.out.print(" --- Berabere.");
        } else {
            player.addBalance(bet * 2);
            System.out.print(" --- Kazandı.");
        }
        System.out.println(" --- Güncel Bakiye: " + player.getBalance() + "\n");
    }
   
}
