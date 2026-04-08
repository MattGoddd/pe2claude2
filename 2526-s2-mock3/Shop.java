// @author
import cs2030s.fp.Lazy;
import cs2030s.fp.Maybe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A shop that manages a collection of orders keyed by order ID.
 *
 * --- TASK 2 ---
 * The methods below use loops, {@code null} checks, and conditional
 * statements.  Remove ALL of these, replacing them with {@code Maybe}
 * operations and Java Stream pipelines.  Each method body must be a
 * SINGLE return statement after refactoring.  You must NOT use:
 *   - any loop (for, while, do-while)
 *   - any conditional (if, switch, ternary ? :)
 *   - any block lambda  (x -> { ... })
 *   - any null literal
 *
 * --- TASK 3 ---
 * Add a {@code Lazy<Double>} field to cache the result of
 * {@code totalRevenue()}.  The revenue is expensive to compute across
 * all orders and should not be recalculated unless the shop's orders
 * have changed.
 *
 * Concretely:
 *  - Add a private {@code Lazy<Double>} field {@code cachedRevenue}.
 *  - Initialise it (lazily) in the constructor.
 *  - Rewrite {@code totalRevenue()} to return {@code cachedRevenue.get()}.
 *  - In {@code addOrder} and {@code payOrder}, reset {@code cachedRevenue}
 *    to a new {@code Lazy} so the next call to {@code totalRevenue()}
 *    recomputes from the updated orders.
 */
public class Shop {
  private final Map<String, Order> orders;

  public Shop() {
    this.orders = new HashMap<>();
  }

  /** Adds an order to this shop. */
  public void addOrder(Order order) {
    orders.put(order.getId(), order);
  }

  /**
   * Returns the order with the given ID, or an empty Maybe if not found.
   * CURRENTLY BROKEN: returns null instead of Maybe.none().
   * Fix this — change the return type to {@code Maybe<Order>}.
   */
  public Order findOrder(String id) {
    return orders.get(id);   // null if absent — REPLACE WITH Maybe
  }

  /**
   * Returns the coupon code of the order with the given ID, or
   * {@code Maybe.none()} if the order is not found or has no coupon.
   *
   * Currently has null checks — rewrite using Maybe.flatMap.
   */
  public Maybe<String> getCoupon(String id) {
    Order o = orders.get(id);
    if (o == null) {
      return Maybe.none();
    }
    return o.getCoupon();   // NOTE: after Task 1, getCoupon() returns Maybe<String>
  }

  /**
   * Returns the discount percentage of the order with the given ID, or
   * {@code Maybe.none()} if the order is not found or has no coupon.
   *
   * Currently has null checks — rewrite using chained Maybe.flatMap.
   */
  public Maybe<Double> getDiscountPct(String id) {
    Order o = orders.get(id);
    if (o == null) {
      return Maybe.none();
    }
    String coupon = o.getCoupon();   // NOTE: changes to Maybe<String> after Task 1
    if (coupon == null) {
      return Maybe.none();
    }
    return Maybe.some(o.getDiscountPct());
  }

  /**
   * Returns the final payable amount (after discount) of the order with the
   * given ID, or {@code Maybe.none()} if the order is not found.
   */
  public Maybe<Double> getFinalAmount(String id) {
    Order o = orders.get(id);
    if (o == null) {
      return Maybe.none();
    }
    return Maybe.some(o.getFinalAmount());
  }

  /**
   * Marks the order with the given ID as paid.  Does nothing if the order
   * is not found.  After Task 1, Order.pay() returns a new Order, so this
   * method must replace the entry in the map.
   */
  public void payOrder(String id) {
    Order o = orders.get(id);
    if (o != null) {
      orders.put(id, o.pay());
    }
    // TASK 3: reset cachedRevenue here
  }

  /**
   * Returns the total revenue: the sum of {@code getFinalAmount()} for all
   * PAID orders.
   *
   * Currently uses a loop — rewrite as a single Stream pipeline.
   * TASK 3: wrap the result in a Lazy and return cachedRevenue.get() instead.
   */
  public double totalRevenue() {
    double total = 0;
    for (Order o : orders.values()) {
      if (o.isPaid()) {
        total += o.getFinalAmount();
      }
    }
    return total;
  }

  /**
   * Returns a list of order IDs for all UNPAID orders, in no particular order.
   * Currently uses a loop — rewrite as a single Stream pipeline.
   */
  public List<String> unpaidOrderIds() {
    List<String> result = new ArrayList<>();
    for (Order o : orders.values()) {
      if (!o.isPaid()) {
        result.add(o.getId());
      }
    }
    return result;
  }

  /**
   * Returns a list of all order IDs where the order has a coupon applied,
   * regardless of paid status, sorted alphabetically by order ID.
   * Currently uses a loop — rewrite as a single Stream pipeline using Maybe.
   */
  public List<String> ordersWithCoupon() {
    List<String> result = new ArrayList<>();
    for (Order o : orders.values()) {
      if (o.getCoupon() != null) {   // NOTE: changes after Task 1
        result.add(o.getId());
      }
    }
    result.sort(String::compareTo);
    return result;
  }

  @Override
  public String toString() {
    return "Shop(" + orders.size() + " orders)";
  }
}
