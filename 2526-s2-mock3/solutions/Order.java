// @author A0000000X
import cs2030s.fp.Maybe;

public class Order {
  private final String id;
  private final String customerId;
  private final double amount;
  private final boolean paid;
  private final Maybe<String> coupon;
  private final Maybe<Double> discountPct;

  public Order(String id, String customerId, double amount) {
    this.id          = id;
    this.customerId  = customerId;
    this.amount      = amount;
    this.paid        = false;
    this.coupon      = Maybe.none();
    this.discountPct = Maybe.none();
  }

  private Order(String id, String customerId, double amount,
      boolean paid, Maybe<String> coupon, Maybe<Double> discountPct) {
    this.id          = id;
    this.customerId  = customerId;
    this.amount      = amount;
    this.paid        = paid;
    this.coupon      = coupon;
    this.discountPct = discountPct;
  }

  public String getId()              { return id; }
  public String getCustomerId()      { return customerId; }
  public double getAmount()          { return amount; }
  public boolean isPaid()            { return paid; }
  public Maybe<String> getCoupon()   { return coupon; }
  public Maybe<Double> getDiscountPct() { return discountPct; }

  public double getFinalAmount() {
    return discountPct
        .map(pct -> amount * (1.0 - pct / 100.0))
        .orElse(amount);
  }

  public Order pay() {
    return new Order(id, customerId, amount, true, coupon, discountPct);
  }

  public Order applyCoupon(String code, double pct) {
    return new Order(id, customerId, amount, paid,
        Maybe.some(code), Maybe.some(pct));
  }

  @Override
  public String toString() {
    return "Order(" + id + ", " + customerId + ", $"
        + String.format("%.2f", amount) + ","
        + (paid ? " paid" : " unpaid")
        + ", coupon=" + coupon + ")";
  }
}
