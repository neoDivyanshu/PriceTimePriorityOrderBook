import java.util.*;

public class OrderBook {
    private final TreeMap<Double, List<Order>> buyMap = new TreeMap<>(Collections.reverseOrder()); //<Price, <oldestOrder, latestOrder>>, highest price first
    private final TreeMap<Double, List<Order>> sellMap = new TreeMap<>();  //<Price, <oldestOrder, latestOrder>>, Lowest price first
    private final HashMap<Integer, Order> orderCache = new HashMap<>();

    public Order getLowestSell(){
        if (sellMap.size() == 0)
            return null;
        return sellMap.firstEntry().getValue().get(0); // returning the oldest order of the Lowest sell price
    }

    public Order getHighestBuy(){
        if(buyMap.size() == 0)
            return null;
        return buyMap.firstEntry().getValue().get(0); // returning the oldest order of the highest bid price
    }

    public void addOrder(int orderId, String orderType ,String ticker, int qty, double price){
        Order order = new Order(orderId, orderType, ticker, qty, price); // assuming the system is using unique orderId and none of the order fields are blank.

        addToQueue(order.isBuy()? buyMap : sellMap, order);

        orderCache.put(orderId,order); // add to order cache for constant time look up while deleting
    }

    public boolean deleteOrder(int orderId){
        if(!orderCache.containsKey(orderId)) return false;

        Order orderToDelete  = orderCache.remove(orderId);
        removeFromOrderQueue(orderToDelete, orderToDelete.isBuy() ? buyMap : sellMap);

        return true;
    }

    private void addToQueue(TreeMap<Double, List<Order>> orderMap, Order order) {
        if(orderMap.containsKey(order.getPrice())){
            addOrderToBottomOfQueue(orderMap, order);
        }
        else {
            createPriceQueue(order, orderMap);
        }
    }

    private void addOrderToBottomOfQueue(TreeMap<Double, List<Order>> orderMap, Order order) {
        List<Order> orderQueue = orderMap.get(order.getPrice());

        order.setPrevOrder(orderQueue.get(1)); // add the last order as previous of the new order
        orderQueue.get(1).setNextOrder(order); // add the new order as the next of the current last order of the in the queue

        orderQueue.remove(1);
        orderQueue.add(1, order); //update List's bottom reference
    }

    private void createPriceQueue(Order order, TreeMap<Double, List<Order>> orderMap) {
        List<Order> newQueue = new ArrayList<>();
        newQueue.add(0, order);
        newQueue.add(1, order);

        orderMap.put(order.getPrice(), newQueue);
    }

    private void removeFromOrderQueue(Order orderToDelete, TreeMap<Double, List<Order>> orderMap) {
        List<Order> priceQueue = orderMap.get(orderToDelete.getPrice());

        if(priceQueue.get(0) == priceQueue.get(1)){
            // case for one element in queue
            orderMap.remove(orderToDelete.getPrice());

        } else if(priceQueue.get(0) == orderToDelete){
            // case for removing the top element

            Order top = priceQueue.remove(0);
            priceQueue.add(0,top.getNextOrder());
        } else if(priceQueue.get(1) == orderToDelete){
            // removing last element

            Order bottom = priceQueue.remove(1);
            priceQueue.add(1,bottom.getPrevOrder());
        } else {
            // more than 2 elements in queue
            Order prev = orderToDelete.getPrevOrder();
            Order next = orderToDelete.getNextOrder();

            orderToDelete.setNextOrder(null);
            orderToDelete.setPrevOrder(null);
            prev.setNextOrder(next);
            next.setPrevOrder(prev);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb =new StringBuilder();
        sb.append("buyMap = {\n");
        for (Map.Entry<Double, List<Order>>kv : buyMap.entrySet()) {
            sb.append(kv.getKey()).append(" : ").append(kv.getValue()).append("\n");
        }
        sb.append("}\n");
        sb.append("sellMap = {\n");
        for (Map.Entry<Double, List<Order>>kv : sellMap.entrySet()) {
            sb.append(kv.getKey()).append(" : ").append(kv.getValue()).append("\n");
        }
        sb.append("}\n");


        return sb.toString();
    }
}
