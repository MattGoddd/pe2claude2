package cs2030s.fp;

/**
 * A functional interface that transforms a value of type T into a value of type R.
 *
 * @param <T> the input type
 * @param <R> the result type
 */
@FunctionalInterface
public interface Transformer<T, R> {
  R transform(T t);
}
