import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] q;
	private int size;
    public RandomizedQueue() {
	   // construct an empty deque
        q = (Item[]) new Object[1];
    }
   
   public boolean isEmpty() {
	   // is the queue empty?
	   return size == 0;
   }
   
   public int size() {
	   // return the number of items on the queue
	   return size;
   }
   
   public void enqueue(Item item) {
	   // add the item
	   if (item == null) throw new NullPointerException();
	   if (size == q.length) resize(2 * q.length);
	   q[size++] = item;
   }
   
   public Item dequeue() {
	   // remove and return a random item
	   if (isEmpty()) throw new NoSuchElementException();
	   int randNumber = randSelect();
	   Item item = q[randNumber];
	   q[randNumber] = q [size-1];
	   q[size-1] = null;
	   if (size > 0 && size == q.length/4) resize(q.length/2);
	   size--;
	   return item;
   }
   
   private void resize(int capacity) {
	   Item[] copy = (Item[]) new Object[capacity];
	   for (int i = 0; i < size; i++) {
		   copy[i] = q[i];
	   }
	   q = copy;
   }
   private int randSelect() {
	   return StdRandom.uniform(size);
   }
   
   public Item sample() {
	   // return (but do not remove) a random item
	   if (isEmpty()) throw new NoSuchElementException();
	   int randNumber = randSelect();
	   return q[randNumber];
   }
   
   public Iterator<Item> iterator() {
	   // return an independent iterator over items in random order
	   return new RandomizedQueueIterator();
   }
   
   private class RandomizedQueueIterator implements Iterator<Item> {
	   private int i = 0;
	   Item[] copy = (Item[]) new Object[size];
	   private RandomizedQueueIterator() {
		   for (int i = 0; i < size; i++) {
			   copy[i] = q[i];
		   }
	   }
	   public boolean hasNext() { return i < size; }
	   public void remove() { throw new UnsupportedOperationException(); }
	   public Item next() {
		   if (!hasNext()) throw new NoSuchElementException();
		   int randNum = StdRandom.uniform(size - i);
		   Item item = copy[randNum];
		   copy[randNum] = copy[size - i -1];
		   i++;
		   return item;
	   }
   }
   
   public static void main(String[] args) {
	   // unit testing (optional)
	   RandomizedQueue<String> randQue = new RandomizedQueue<String>();
	   for (int i = 0; i < 10; i++) {
		   String item = StdIn.readString();
		   randQue.enqueue(item);
		}
   	   Iterator<String> i = randQue.iterator();
	   while (i.hasNext()) {
		   String s = i.next();
		   StdOut.println(s);
	    }
	   StdOut.println("fengexian----");
   	   Iterator<String> j = randQue.iterator();
	   while (j.hasNext()) {
		   String s_ = j.next();
		   StdOut.println(s_);
	    }
   }
}