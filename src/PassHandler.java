public class PassHandler extends BaseAnswerHandler {

    @Override
    public boolean handleAnswer(String answer, PlayerDeck playerDeck) {
        if (answer.toLowerCase().equals("pas")) {
            playerDeck.setStatus(Status.INACTIVE);
            return true;
        }
        return false;
    }
}
