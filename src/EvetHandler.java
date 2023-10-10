
public class EvetHandler extends BaseAnswerHandler {

	public boolean handleAnswer(String answer, PlayerDeck playerDeck) {
		if (answer.toLowerCase().equals("evet")) {
			playerDeck.getPlayer().reduceBalance(playerDeck.getBet());
			playerDeck.setSgStatus(SgStatus.SIGORTALI);
			System.out.println(playerDeck.getPlayer().getName() + " Sigorta Yaptı. Yeni Bakiye : "
					+ playerDeck.getPlayer().getBalance());

			return true;
		}
		return false;
	}

}