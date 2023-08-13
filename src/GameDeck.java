import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GameDeck extends AbstractDeck {

    public GameDeck() {
        this.createDeck();
    }

    // Deste oluştur
    public void createDeck() {
        ObjectMapper objectMapper = new ObjectMapper();

        try (BufferedReader reader = new BufferedReader(new FileReader("cards.json"))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            this.cards = objectMapper.readValue(jsonContent.toString(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Card.class));

            this.shuffleCards();
            this.cards = this.cards.subList((this.cards.size() / 2), this.cards.size()); // Destenin yarısını sil
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Desteyi karıştır
    public void shuffleCards() {
        Collections.shuffle(this.cards);
    }

    // Kart çek
    public Card buyCard() {
        return this.buyCard(0);
    }

    // Index'e göre kart çek
    public Card buyCard(int index) {
        Card card = this.cards.get(index);
        this.cards.remove(card);

        return card;
    }

    // İlk el için iki kart çek
    public List<Card> buyDoubleCard(int index) {
        return new ArrayList<>(List.of(this.buyCard(), this.buyCard(index)));
    }
}
