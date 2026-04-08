// @author A0000000X
import cs2030s.fp.Lazy;
import cs2030s.fp.Maybe;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Shop {
  private final Map<String, Order> orders;
  private Lazy<Double> cachedRevenue;

  public Shop() {
    this.orders = new HashMap<>();
    this.cachedRevenue = Lazy.of(() -> computeRevenue());
  }

  private double computeRevenue() {
    return orders.values().stream()
        .filter(Order::isPaid)
        .mapToDouble(Order::getFinalAmount)
        .sum();
  }

  public void addOrder(Order order) {
    orders.put(order.getId(), order);
    this.cachedRevenue = Lazy.of(() -> computeRevenue());
  }

  public Maybe<Order> findOrder(String id) {
    return Maybe.some(orders.get(id))
        .filter(o -> o != null);
  }

  public Maybe<String> getCoupon(String id) {
    return findOrder(id).flatMap(Order::getCoupon);
  }

  public Maybe<Double> getDiscountPct(String id) {
    return findOrder(id).flatMap(Order::getDiscountPct);
  }

  public Maybe<Double> getFinalAmount(String id) {
    return findOrder(id).map(Order::getFinalAmount);
  }

  public void payOrder(String id) {
    findOrder(id)
        .map(Order::pay)
        .filter(o -> updateMap(o.getId(), o))
        .orElse(null);
    this.cachedRevenue = Lazy.of(() -> computeRevenue());
  }

  // Helper — puts paid order back in map and returns true.
  private boolean updateMap(String oid, Order o) {
    orders.put(oid, o);
    return true;
  }

  public double totalRevenue() {
    return cachedRevenue.get();
  }

  public List<String> unpaidOrderIds() {
    return orders.values().stream()
        .filter(o -> !o.isPaid())
        .map(Order::getId)
        .collect(Collectors.toList());
  }

  public List<String> ordersWithCoupon() {
    return orders.values().stream()
        .filter(o -> !o.getCoupon().equals(Maybe.none()))
        .map(Order::getId)
        .sorted()
        .collect(Collectors.toList());
  }

  @Override
  public String toString() {
    return "Shop(" + orders.size() + " orders)";
  }
}
