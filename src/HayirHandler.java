
public class HayirHandler extends BaseAnswerHandler {


	public boolean handleAnswer(String answer, PlayerDeck playerDeck) {
		if (answer.toLowerCase().equals("hayir")) {
			playerDeck.setSgStatus(SgStatus.SIGORTASIZ);
			return true;
		}
		return false;
	}

}