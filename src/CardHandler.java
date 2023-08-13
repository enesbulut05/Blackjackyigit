public class CardHandler extends BaseAnswerHandler {

    @Override
    public boolean handleAnswer(String answer, PlayerDeck playerDeck) {
        if (answer.toLowerCase().equals("kart")) {
            playerDeck.addCard(UtilService.getGameDeck().buyCard());
            playerDeck.showCards();
            return true;
        }
        return false;
    }
}
