import java.util.List;

public class VaultDeck extends AbstractPlayerDeck {
    public VaultDeck() {
    }

    public VaultDeck(List<Card> cards) {
        this.cards = cards;
    }

    public void showFirstCard() {
        System.out.println("Kasa Kartları:\n[" + this.cards.get(0).getName() + ", *]\n");
    }

    @Override
    public void showResult() {
        System.out.println(this.toString() + "\n");
    }
}
