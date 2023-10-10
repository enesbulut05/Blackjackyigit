public abstract class AbstractPlayerDeck extends AbstractDeck {
    protected int cardTotal = 0;
    protected Status status = Status.ACTIVE;
    protected SgStatus sgstatus = SgStatus.SIGORTASIZ;

    public abstract void showResult();

    public String calculateCardTotal() {
        int total = 0;
        int aceCount = 0;

        for (Card card : this.cards) {
            total += card.getValue();

            // Eğer kart "A" ise "aceCount"i arttır
            if (card.getName().equals("A")) {
                aceCount++;
            }
        }

        String result = String.valueOf(total);

        // "A" varsa ve 11 olarak kullanılabilirse sonucu düzenle
        if (aceCount > 0 && total <= 11) {
            result = result + " ya da " + String.valueOf(total + 10);
        }
        if (getStatus() == Status.BLACKJACK){
        	result = "21";
        }

        return result;
    }

    public void showCards() {
        String cardTotal = this.calculateCardTotal();
        System.out.println(this.toString() + " - Toplam Değer: " + cardTotal + "\n");

        try {
            this.cardTotal = Integer.parseInt(cardTotal);
            if (this.cardTotal > 21) {
                System.out.println("PATLADI :)\n");
                this.status = Status.INACTIVE;
            }
        } catch (Exception e) {
            String cardTotals[] = cardTotal.split(" ya da ");
            this.cardTotal = Integer.parseInt(cardTotals[1]);
        }
    }

    public int getCardTotal() {
        return cardTotal;
    }

    public void setCardTotal(int cardTotal) {
        this.cardTotal = cardTotal;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public SgStatus getSgStatus() {
        return sgstatus;
    }

    public void setSgStatus(SgStatus sgstatus) {
        this.sgstatus = sgstatus;
    }
    
}
