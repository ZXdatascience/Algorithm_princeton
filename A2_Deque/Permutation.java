import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }

        int num = Integer.parseInt(args[0]);
        while (num-- > 0) {
            StdOut.println(rq.dequeue());
        }
    }
}
 
/* java shell command
 * java -cp .:/u/b/zhan3650/algs4.jar Permutation 3 < ~/workspace/Queues/Queses/src/queues/duplicates.txt*/
