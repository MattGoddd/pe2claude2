package cs2030s.fp;

/**
 * A functional interface that produces a value of type T.
 *
 * @param <T> the type of value produced
 */
@FunctionalInterface
public interface Producer<T> {
  T produce();
}
