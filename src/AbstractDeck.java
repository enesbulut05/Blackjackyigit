import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDeck {
    protected List<Card> cards;

    public AbstractDeck() {
        this.cards = new ArrayList<>();
    }

    public List<Card> getCards() {
        return this.cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Card getCard(int index) {
        return this.cards.get(index);
    }

    public void setCard(int index, Card card) {
        this.cards.set(index, card);
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    @Override
    public String toString() {
        return this.cards.toString();
    }
    
    public void resetCards() {
    	this.cards.clear();
    }
}
