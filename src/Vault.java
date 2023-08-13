public class Vault implements GameParticipant {
    private VaultDeck vaultDeck;

    public VaultDeck getVaultDeck() {
        return this.vaultDeck;
    }

    public void setVaultDeck(VaultDeck vaultDeck) {
        this.vaultDeck = vaultDeck;
    }

    @Override
    public void showCards() {
        vaultDeck.showCards();

        if (vaultDeck.getCardTotal() >= 17) {
            vaultDeck.setStatus(Status.INACTIVE);
        }
    }

    @Override
    public void showResult() {
        System.out.print("Kasanın en son durumu: ");
        vaultDeck.showResult();
    }
}
