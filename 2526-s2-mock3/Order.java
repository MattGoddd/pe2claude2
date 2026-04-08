// @author
import cs2030s.fp.Maybe;

/**
 * Represents a customer order.
 *
 * Each order has an ID, a customer ID, an amount (the base total before
 * any discount), a flag indicating whether it has been paid, and an
 * optional coupon code with an associated discount percentage.
 *
 * --- TASK 1 ---
 * This class is currently mutable and uses {@code null} to represent
 * the absence of a coupon.  You must:
 *
 *  1. Make ALL fields {@code private final}.
 *
 *  2. Replace the nullable {@code String coupon} and {@code double discountPct}
 *     fields with a single field {@code Maybe<String> coupon} and a single
 *     field {@code Maybe<Double> discountPct}, each of type {@code Maybe}.
 *
 *  3. Rewrite every mutating method to return a new {@code Order} instead
 *     of modifying {@code this}.  You may NOT add new methods.
 *
 *  4. Rewrite the accessors to reflect the new types.
 *
 * You should NOT change the constructor signature or the existing method
 * signatures (parameter types and names, and return types).
 */
public class Order {
  private String id;
  private String customerId;
  private double amount;
  private boolean paid;
  private String coupon;        // null means no coupon  -- CHANGE THIS
  private double discountPct;   // 0.0 means no discount -- CHANGE THIS

  public Order(String id, String customerId, double amount) {
    this.id          = id;
    this.customerId  = customerId;
    this.amount      = amount;
    this.paid        = false;
    this.coupon      = null;
    this.discountPct = 0.0;
  }

  public String getId()         { return id; }
  public String getCustomerId() { return customerId; }
  public double getAmount()     { return amount; }
  public boolean isPaid()       { return paid; }

  /**
   * Returns the coupon code for this order, or {@code null} if none.
   * CHANGE the return type to {@code Maybe<String>} after Task 1.
   */
  public String getCoupon() {
    return coupon;
  }

  /**
   * Returns the discount percentage for this order, or {@code null} if none.
   * CHANGE the return type to {@code Maybe<Double>} after Task 1.
   */
  public double getDiscountPct() {
    return discountPct;
  }

  /**
   * Returns the final payable amount: {@code amount * (1 - discountPct/100)}.
   * If there is no discount, returns {@code amount}.
   */
  public double getFinalAmount() {
    if (coupon == null) {
      return amount;
    }
    return amount * (1.0 - discountPct / 100.0);
  }

  /**
   * Marks this order as paid.
   * CHANGE: should return a new paid Order instead of mutating this one.
   */
  public Order pay() {
    this.paid = true;
    return this;
  }

  /**
   * Applies a coupon to this order.
   * CHANGE: should return a new Order with the coupon applied, not mutate.
   *
   * @param code       the coupon code
   * @param discountPct the discount percentage (e.g. 10.0 for 10%)
   */
  public Order applyCoupon(String code, double discountPct) {
    this.coupon      = code;
    this.discountPct = discountPct;
    return this;
  }

  @Override
  public String toString() {
    return "Order(" + id + ", " + customerId + ", $" + String.format("%.2f", amount) + ","
        + (paid ? " paid" : " unpaid") + ", coupon=" + coupon + ")";
  }
}
