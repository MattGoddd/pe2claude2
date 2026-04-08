package cs2030s.fp;

/**
 * A functional interface representing a boolean condition on a value of type T.
 *
 * @param <T> the input type
 */
@FunctionalInterface
public interface BooleanCondition<T> {
  boolean test(T t);
}
