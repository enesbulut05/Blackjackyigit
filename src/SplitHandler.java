import java.util.ArrayList;
import java.util.List;

public class SplitHandler extends BaseAnswerHandler {

    @Override
    public boolean handleAnswer(String answer, PlayerDeck playerDeck) {
        if (answer.toLowerCase().equals("bol")||answer.toLowerCase().equals("böl")) {
            Player player = playerDeck.getPlayer();
            player.reduceBalance(playerDeck.getBet());
            player.setPlayerDeck(new PlayerDeck(new ArrayList<>(List.of(playerDeck.getCard(1), UtilService.getGameDeck().buyCard()))));
            playerDeck.setCard(1, UtilService.getGameDeck().buyCard());

            System.out.println("Kartlar Bölündü. Yeni kartlar çekildi. --- Güncel Bakiye: " + player.getBalance());
            player.showCards();
            return true;
        }
        return false;
    }

}
