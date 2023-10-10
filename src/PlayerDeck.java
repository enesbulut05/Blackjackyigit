import java.util.List;

public class PlayerDeck extends AbstractPlayerDeck {
	private Player player;
	
	private int defaultBet = 5;
	private double bet = defaultBet;
	
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
		if (UtilService.getVault().getVaultDeck().getSgStatus() == SgStatus.SIGORTAELI
				&& getSgStatus()== SgStatus.SIGORTALI) {
			if(UtilService.getVault().getVaultDeck().getStatus() == Status.BLACKJACK) {
				player.addBalance(defaultBet*3);
				System.out.println(player.getName()+" Sigortadan "+ defaultBet*3 + " kazandı.");
			}
			else {
				System.out.println(player.getName()+" Sigortadan " + defaultBet + " kaybetti.");
			}
		}
		
		
		System.out.println(player.getName() + ":");
		System.out.print(this.toString() + " - Toplam Değer: " + this.cardTotal);
		int vaultDeckTotal = UtilService.getVault().getVaultDeck().getCardTotal();
		

		if (this.cardTotal > 21 || (this.cardTotal < vaultDeckTotal && vaultDeckTotal <= 21)) {
			System.out.print(" --- Kaybetti.");
		} else if (this.cardTotal == vaultDeckTotal) {
			if (getStatus() == Status.BLACKJACK
					&& UtilService.getVault().getVaultDeck().getStatus() != Status.BLACKJACK) {
				player.addBalance(bet * 2);
				System.out.print(" --- Kazandı.");
			} else if (getStatus() != Status.BLACKJACK
					&& UtilService.getVault().getVaultDeck().getStatus() == Status.BLACKJACK) {

				System.out.print(" --- Kaybetti.");
			}

			else {
				player.addBalance(bet);
				System.out.print(" --- Berabere.");
			}
		} else {
			player.addBalance(bet * 2);
			System.out.print(" --- Kazandı.");
		}
		System.out.println(" --- Güncel Bakiye: " + player.getBalance() + "\n");
	}

}
