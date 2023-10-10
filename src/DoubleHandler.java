public class DoubleHandler extends BaseAnswerHandler {

    @Override
    public boolean handleAnswer(String answer, PlayerDeck playerDeck) {
        if (answer.toLowerCase().equals("2x")) {
            Player player = playerDeck.getPlayer();
            player.reduceBalance(playerDeck.getBet());
            playerDeck.setBet(playerDeck.getBet() * 2);
            playerDeck.addCard(UtilService.getGameDeck().buyCard());

            System.out.println("Bahis 2'ye katlandı. Yeni kart çekildi. --- Güncel Bakiye: " + player.getBalance());
            playerDeck.showCards();
            
            playerDeck.setStatus(Status.INACTIVE);
            return true;
        }
        return false;
    }   
}
