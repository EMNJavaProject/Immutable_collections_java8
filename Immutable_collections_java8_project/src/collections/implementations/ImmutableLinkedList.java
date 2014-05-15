package collections.implementations;

import java.util.Collection;
import java.util.Optional;
import java.util.List;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.function.BinaryOperator;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.Function;

import collections.interfaces.ImmutableList;

class Node<E> {
	private final E element;
	private final Node<E> next;
	
	public Node(E element, Node<E> next){
		this.element=element;
		this.next=next;
	}
	
	public Node(E element){
		this(element, null);
	}
	
	public E getElement(){
		return this.element;
	}
	
	public Node<E> getNext(){
		return next;
	}
	
	public boolean hasNext(){
		return getNext()!=null;
	}
}

public class ImmutableLinkedList<E> /* implements ImmutableList<E> */ 
{

	private final Node<E> head;
	
	
    // Constructors
    // public ImmutableList<E>();
    // public ImmutableList<E>(Collection<E> elems);
     @SafeVarargs
	public ImmutableLinkedList (E... elems){
    	 head = null;
     }

    // Operations

    // public boolean isEmpty();
    // public int size();
     public E get(int index){ //TODO Perhaps put a return type ?
    	 Node<E> node = head;
    	 int i=0;
    	 while (node != null) {
    		 if (i == index)
    			 return node.getElement();
    		 else {
    			 if (!node.hasNext())
    				 throw new IndexOutOfBoundsException();
    			 else{
    				 node = node.getNext();
    				 i++;
    			 }
    		 }
    	 }	
    	 return null;//TODO change this.
    }
     
    // public int indexOf(E elem);

    // public E head();
    // public ImmutableList<E> tail();
    // public E last();

    // public List<E> subList(int from, int size);
    // public List<E> reverse();
    // public List<E> sort(Comparator<? super E> comparator);

    // public boolean contains(E elem);
    // public boolean containsAll(Collection<E> elems);
    // public boolean containsAll(ImmutableList<E> elems);
    // public @SuppressWarnings({"unchecked", "varags"})
    // public boolean containsAll(E... elems);

    // public boolean any(Predicate<? super E> predicate);
    // public boolean all(Predicate<? super E> predicate);

    // Factories

    // public ImmutableList<E> cons(E elem);

    // public ImmutableList<E> concat(Collection<E> elems);
    // public ImmutableList<E> concat(ImmutableList<E> elems);
    // public @SuppressWarnings({"unchecked", "varags"})
    // public ImmutableList<E> concat(E... elems);
   /* public ImmutableList<E> concat(E elem) {
    	Node<E> node = head;
    	int i=0;
   	 	while (node != null) {
   	 			if (node.hasNext())
   	 				
   			 }
   		 
   	 }	 
    }*/

    // public ImmutableList<E> remove(Collection<E> elems);
    // public ImmutableList<E> remove(ImmutableList<E> elems);
    // public @SuppressWarnings({"unchecked", "varags"})
    // public ImmutableList<E> remove(E... elems);
    // public ImmutableList<E> remove(E elem);
    // public ImmutableList<E> remove(int index);

    // public ImmutableList<E> union(Collection<E> elems);
    // public ImmutableList<E> union(ImmutableList<E> elems);
    // public @SuppressWarnings({"unchecked", "varags"})
    // public ImmutableList<E> union(E... elems);

    // public ImmutableList<E> intersect(Collection<E> elems);
    // public ImmutableList<E> intersect(ImmutableList<E> elems);
    // public @SuppressWarnings({"unchecked", "varags"})
    // public ImmutableList<E> intersect(E... elems);

    // public <F> ImmutableList<F> map(Function<? super E, ? super F> mapper);
    // public <F> ImmutableList<E> filter(Predicate<? super E> predicate);
    // public Optional<E> reduce(BinaryOperator<E> accumulator);
    // public E reduce(E identity, BinaryOperator<E> accumulator);
    // public <F> F reduce(F identity, BiFunction<F, ? super E, F> accumulator, BinaryOperator<F> combiner);

    // Iterators & streams
    // public Iterator<E> iterator();
    // public Stream<E> stream();
    // public Stream<E> parallelStream();

    // Object methods
    // public ImmutableList<E> clone();
    // public boolean equals(Object o);
    // public int hashCode();

    // Conversions
    // public E[] toArray();
    // public List<E> asList();
}
