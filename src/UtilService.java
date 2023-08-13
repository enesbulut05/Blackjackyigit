public class UtilService {
    private static GameDeck gameDeck;
    private static Vault vault;

    public static GameDeck createGameDeck() {
        gameDeck = new GameDeck();
        return gameDeck;
    }

    public static Vault createVault() {
        vault = new Vault();
        return vault;
    }

    public static GameDeck getGameDeck() {
        return gameDeck;
    }

    public static Vault getVault() {
        return vault;
    }
}
