package pet.discogs.data.common;

import org.jspecify.annotations.NonNull;

import java.util.Comparator;
import java.util.SortedSet;

public abstract class Printable implements Comparable<Printable> {

    public abstract String getShortName();

    private final static Comparator<Printable> NATURAL_ORDER = Comparator.comparing(Printable::getShortName);

    @Override
    public int compareTo(@NonNull final Printable other) {
        return NATURAL_ORDER.compare(this, other);
    }

    public static String print(SortedSet<? extends Printable> printables) {
        return printables.stream().map(Printable::getShortName).toList().toString();
    }
}

