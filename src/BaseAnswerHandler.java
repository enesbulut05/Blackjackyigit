
public abstract class BaseAnswerHandler {
    protected BaseAnswerHandler nextAnswerHandler;

    public abstract boolean handleAnswer(String answer, PlayerDeck playerDeck);

    public boolean handle(String answer, PlayerDeck playerDeck) {
        boolean hasAnswered = this.handleAnswer(answer, playerDeck);

        if (!hasAnswered && this.nextAnswerHandler != null) {
            hasAnswered = this.nextAnswerHandler.handle(answer, playerDeck);
        }
        return hasAnswered;
    }

    public BaseAnswerHandler setNextHandler(BaseAnswerHandler nextAnswerHandler) {
        this.nextAnswerHandler = nextAnswerHandler;

        return nextAnswerHandler;
    }
}
