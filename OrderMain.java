public class OrderMain {
    static OrderBook appleBook = new OrderBook();

    public  static void main(String[] args){
        appleBook.addOrder(1,"Buy", "AAPL", 100, 175);
        appleBook.addOrder(2,"Buy", "AAPL", 100, 175);
        appleBook.addOrder(3,"Sell", "AAPL", 100, 176.8);
        appleBook.addOrder(4,"Sell", "AAPL", 100, 177);
        appleBook.addOrder(5,"Buy", "AAPL", 100, 174);
        appleBook.addOrder(6,"Buy", "AAPL", 100, 175);
        appleBook.addOrder(7,"Buy", "AAPL", 100, 178);
        System.out.println(appleBook);

        delteId(6);
        delteId(2);
        delteId(1);
        delteId(4);

        System.out.println("Highest buy: " + appleBook.getHighestBuy().getPrice());
        System.out.println("Lowest sell: " + appleBook.getLowestSell().getPrice());

    }

    private static void delteId(int id) {
        if(appleBook.deleteOrder(id)){
            System.out.println("order deleted successfully "+ id +", Book after deletion ---------\n" + appleBook );
        }else {
            System.out.println("order" + id + "doesn't exist");
        }
    }
}
