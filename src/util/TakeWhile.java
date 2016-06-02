package util;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TakeWhile<T> implements Iterator<T> {

    private final Iterator<T> iterator;
    private final Predicate<T> predicate;
    private volatile T next;
    private volatile boolean keepGoing = true;

    public TakeWhile(Stream<T> s, Predicate<T> p) {
        this.iterator = s.iterator();
        this.predicate = p;
    }

    @Override
    public boolean hasNext() {
        if (!keepGoing) {
            return false;
        }
        if (next != null) {
            return true;
        }
        if (iterator.hasNext()) {
            next = iterator.next();
            keepGoing = predicate.test(next);
            if (!keepGoing) {
                next = null;
            }
        }
        return next != null;
    }

    @Override
    public T next() {
        if (next == null) {
            if (!hasNext()) {
                throw new NoSuchElementException("Sorry. Nothing for you.");
            }
        }
        T temp = next;
        next = null;
        return temp;
    }

    public static <T> Stream<T> stream(Stream<T> s, Predicate<T> p) {
        TakeWhile<T> tw = new TakeWhile<>(s, p);
        Spliterator<T> split = Spliterators.spliterator(tw, Integer.MAX_VALUE, Spliterator.ORDERED);
        return StreamSupport.stream(split, false);
    }

}
