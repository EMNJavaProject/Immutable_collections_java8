interface ImmutableList<E> implements Iterable<E> {

    // Constructeurs (hors interface)
    ImmutableList<E>();        // Liste vide
    ImmutableList<E>(List<E>); // Conversion
    ImmutableList<E>(E...);    // Construction
    ImmutableList<E>(E[]);     // Conversion

    // Fabriques (ajout d'un élément en tête)
    // Scala: + operator
    ImmutableList<E> cons(E);

    // size + get = point de vue IndexedSeq en Scala

    // We use Java List interface name
    boolean isEmpty();
    int size();
    E get(int);
    int indexOf(E);

    // head + tail + isEmpty = point de vue LinearSeq en Scala
    E head();
    ImmutableList<E> tail();
    E last();

    // Opérations sur les listes
    List<E> subList(int, int); 		 // Java && Guava
    List<E> reverse();         		 // Guava: reverse
    List<E> sort(Comparator<? super E>); // Java: sort, Scala: sorted/sortWith

    // Scala, Java, Guava
    boolean contains(E);
    // Java
    boolean containsAll(Collection<E>);
    boolean containsAll(ImmutableList<E>);
    boolean containsAll(E[]);
    boolean containsAll(E...);

    boolean any(Predicate<? super E>); // Scala: exists/find
    boolean all(Predicate<? super E>); // Scala: forall

    // Java: addAll, Scala: ++ operator
    ImmutableList<E> concat(Collection<E>);
    ImmutableList<E> concat(ImmutableList<E>);
    ImmutableList<E> concat(E[]);
    ImmutableList<E> concat(E...);
    ImmutableList<E> concat(E);

    // Java
    ImmutableList<E> remove(Collection<E>);
    ImmutableList<E> remove(ImmutableList<E>);
    ImmutableList<E> remove(E[]);
    ImmutableList<E> remove(E...);
    ImmutableList<E> remove(E);

    // Scala: union
    ImmutableList<E> union(Collection<E>);
    ImmutableList<E> union(ImmutableList<E>);
    ImmutableList<E> union(E[]);
    ImmutableList<E> union(E...);

    // Scala: intersect, Java: retainsAll
    ImmutableList<E> intersect(Collection<E>);
    ImmutableList<E> intersect(ImmutableList<E>);
    ImmutableList<E> intersect(E[]);
    ImmutableList<E> intersect(E...);

    ImmutableList<F> map(Function<? super E, ? super F>); // Scala: map
    ImmutableList<E> filter(Predicate<? super E>);        // Scala: filter
    F reduce(Function<? super E, ? super E, ? super F>);  // Scala: reduce

    // Intégration : itérateurs + flots
    Iterator<E> iterator();
    Stream<E> stream();
    Stream<E> parallelStream();

    // Object methods
    ImmutableList<E> clone(); // clonage inutile pour des données immutables ?
    boolean equals(ImmutableList<E>);
    int hashCode();

    // Conversions
    E[] toArray();    // Scala && Java: toArray
    List<E> asList(); // Scala: toList, Guava: asList
}

// Autres idées : reverseIterator / reverseStream / concat(int, E..) / remove(int)
