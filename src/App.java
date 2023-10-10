import java.io.Closeable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.sql.rowset.serial.SerialJavaObject;

public class App {
	public static Scanner scanner = new Scanner(System.in);
	public static List<Player> players = new ArrayList<>();
	public static GameDeck gameDeck;
	public static Vault vault;

	public static void main(String[] args) throws Exception {
		boolean newGame = true;

		// Oyun başladı
		while (newGame) {
			gameDeck = UtilService.createGameDeck();
			vault = UtilService.createVault();
			createPlayer();

			// El başladı
			while (true) {

				for (Player player : players) {
					PlayerDeck playerDeck = new PlayerDeck();
					player.resetPlayerDeck();

					// Bu kontrol yeni ele taşınacak ???
					if (playerDeck.getBet() > player.getBalance()) {
						System.out.println(player.getName() + " Yetersiz Bakiye Sebeyile Oyuna Dahil Edilmedi.");
						players.remove(player);
					} else {
						// Oyuncu kartlarını çek
						playerDeck.setCards(gameDeck.buyDoubleCard(players.size() - players.indexOf(player)));
						// playerDeck.setCards(new ArrayList<>(List.of(new Card("10", 10), new Card("A",
						// 1))));
						player.setPlayerDeck(playerDeck);

						player.reduceBalance(playerDeck.getBet());

						if (playerDeck.calculateCardTotal().contains("21")) {
							playerDeck.setStatus(Status.BLACKJACK);
						}

						System.out.println(player.getName() + ": Bahis miktarı: " + playerDeck.getBet()
								+ " --- Güncel Bakiye: " + player.getBalance());
						playerDeck.showCards();

					}
				}

				// Kasa kartlarını çek
				VaultDeck vaultDeck = new VaultDeck(gameDeck.buyDoubleCard(0));
				vault.setVaultDeck(vaultDeck);
				//vaultDeck.setCards(new ArrayList<>(List.of(new Card("A", 1), new Card("3", 3))));
				vaultDeck.showFirstCard();

				if (vaultDeck.getCard(0).getName().equals("A"))
				{
					vaultDeck.setSgStatus(SgStatus.SIGORTAELI);
				}
				
				
				if (vaultDeck.calculateCardTotal().contains("21")) {
					vaultDeck.setStatus(Status.BLACKJACK);
				}

				// Soruları sor
				if (vaultDeck.getSgStatus() == SgStatus.SIGORTAELI) {
					for (Player player : players) {
						sigortaSorusu(player, vaultDeck);
					}
				}

				if (vaultDeck.getStatus() != Status.BLACKJACK) {
					if (vaultDeck.getSgStatus() == SgStatus.SIGORTAELI) {
						System.out.println("Kasa BlackJack değil. Oyun devam ediyor...");
					}
					for (Player player : players) {

						firstQuestion(player);
						regularQuestion(player);
					}
				}

				// Kasa oynat
				System.out.print("Kasanın Kartları: ");
				vault.showCards();
				while (vaultDeck.getStatus() == Status.ACTIVE) {
					System.out.println("Kasa kart çekiyor...");
					vaultDeck.addCard(gameDeck.buyCard());
					vault.showCards();
				}

				// Sonuçları yazdır
				for (Player player : players) {
					player.showResult();
				}

				System.out.println(
						"Devam etmek için herhangi bir tuşa basın. Oyunu kapatmak için 'kapat' girin. Yeni oyun için 'yeni' girin.");

				String giris = scanner.nextLine();
				if (giris.equals("kapat")) {
					newGame = false;
					break;
				} else if (giris.equals("yeni")) {
					break;
				}
			}
		}
	}

	// Oyuncuları oluştur
	public static void createPlayer() {
		players.clear();
		int playerCount = 0;

		while (true) {
			try {
				System.out.print("Oyuncu sayısını (en fazla 7) girin: ");
				String answer = scanner.nextLine();
				playerCount = Integer.parseInt(answer);

				if (playerCount <= 0 || playerCount > 7) {
					System.out.print("Hatalı Giriş Yaptınız. ");
				} else {
					break;
				}
			} catch (NumberFormatException e) {
				System.out.print("Oyuncu sayısı tam sayı olmalı. ");
			}
		}

		for (int i = 1; i <= playerCount; i++) {
			System.out.print("Lütfen " + i + ". Oyuncunun adını girin: ");
			String name = scanner.nextLine();

			if (name.trim().isEmpty()) {
				name = "Oyuncu " + i;
			}
			players.add(new Player(name));
		}
		System.out.println();
	}

	// Sigorta Sor
	public static void sigortaSorusu(Player player, VaultDeck vaultDeck) {
		String soru = player.getName() + " Sigorta Yapmak istiyor musunuz? -> EVET / HAYIR ";
		PlayerDeck playerDeck = player.getPlayerDeckByIndex(0);
		BaseAnswerHandler SigortaHandler = new EvetHandler();
		BaseAnswerHandler lastHandler = SigortaHandler.setNextHandler(new HayirHandler());
		askQuestion(SigortaHandler, soru, playerDeck);

	}

	// Oyunculara ilk soruyu sor
	public static void firstQuestion(Player player) {

		String question = "Lütfen Seçiniz -> PAS / KART";
		PlayerDeck playerDeck = player.getPlayerDeckByIndex(0);
		boolean areCardsSame = playerDeck.getCard(0).getValue() == playerDeck.getCard(1).getValue();
		BaseAnswerHandler firstHandler = new CardHandler();
		BaseAnswerHandler lastHandler = firstHandler.setNextHandler(new PassHandler());

		// Şartlar sağlanırsa soruya 2x ve böl seçeneklerini ekle
		if (player.getBalance() > playerDeck.getBet()) {
			question += " / 2x (Bahsi İkiye Katlar)";
			lastHandler = lastHandler.setNextHandler(new DoubleHandler());
			if (areCardsSame) {
				question += " / Böl";
				lastHandler = lastHandler.setNextHandler(new SplitHandler());
			}
		}
		System.out.print(player.getName() + ": ");
		if (playerDeck.getStatus() != Status.BLACKJACK) {
			askQuestion(firstHandler, question, playerDeck);
		}
	}

	// Oyunculara soru sorma
	public static void regularQuestion(Player player) {
		BaseAnswerHandler firstHandler = new CardHandler();
		firstHandler.setNextHandler(new PassHandler());
		List<PlayerDeck> playerDecks = player.getPlayerDecks();

		for (PlayerDeck playerDeck : playerDecks) {
			while (playerDeck.getStatus() == Status.ACTIVE) {
				if (playerDecks.size() > 1) {
					System.out.print((playerDecks.indexOf(playerDeck) + 1) + ". Deste İçin ");
				}
				askQuestion(firstHandler, "Lütfen Seçiniz -> PAS / KART", playerDeck);
			}
		}
	}

	// Oyuncuya soru sor. Cevap hatalı ise doğru cevap alana kadar soruyu tekrarla
	public static void askQuestion(BaseAnswerHandler answerHandler, String question, PlayerDeck playerDeck) {
		System.out.println(question);
		String answer = scanner.nextLine();
		boolean hasAnswered = answerHandler.handle(answer, playerDeck);
		while (!hasAnswered) {
			System.out.println("Hatalı Giriş Yaptınız. " + question);
			hasAnswered = answerHandler.handle(scanner.nextLine(), playerDeck);
		}
	}

}
