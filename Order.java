import java.util.Date;
import java.util.Objects;

public class Order {
    final private int orderId;
    final private String orderType;
    final private String ticker;
    final private int qty;
    final private double price;
    private Order prevOrder;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    @Override
    public String toString() {
        return "{orderId=" + orderId +
        ", Price=" + getPrice() + "}";
    }

    private Order nextOrder;

    public int getOrderId() {
        return orderId;
    }

    public boolean isBuy() {
        return orderType.toLowerCase().equals("buy");
    }

    public String getOrderType() {
        return orderType;
    }

    public String getTicker() {
        return ticker;
    }

    public int getQty() {
        return qty;
    }

    public double getPrice() {
        return price;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    final private Date creationTime;

    public Order(int orderId, String orderType, String ticker, int qty, double price) {
        this.orderId = orderId;
        this.orderType = orderType;
        this.ticker = ticker;
        this.qty = qty;
        this.price = price;
        this.creationTime = new Date();
    }

    public void setNextOrder(Order nextOrder) {
        this.nextOrder = nextOrder;
    }

    public void setPrevOrder(Order prevOrder){
        this.prevOrder = prevOrder;
    }

    public Order getNextOrder(){
        return nextOrder;
    }
    public Order getPrevOrder(){
        return prevOrder;
    }
}
