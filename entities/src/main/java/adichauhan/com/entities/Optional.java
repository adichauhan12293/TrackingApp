package adichauhan.com.entities;

import java.util.NoSuchElementException;

/**
 * Created by adityachauhan on 23/06/18.
 *
 */

public final class Optional<T> {

    private final T value;

    private Optional(T value) {
        this.value = value;
    }

    public static <T> Optional<T> of(T value) {
        return new Optional<>(value);
    }

    public T get() {
        if(value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    public Boolean isPresent() {
        return value != null;
    }

    @Override
    public String toString() {
        return value != null
                ? String.format("Optional<%s>",value)
                :"Optional.EMPTY";
    }
}
