import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    private StudentArrayDeque<Integer> student = new StudentArrayDeque<>();
    private ArrayDequeSolution<Integer> solution = new ArrayDequeSolution<>();
    private int loopVal = 10;

    @Test
    public void TestDeque1() {
        for (int i = 0; i <= 10; i++) {
            int first = StdRandom.uniform(10);
            student.addFirst(first);
            solution.addFirst(first);
        }
        for (int i = 0; i <= 10; i++) {
            assertEquals(student.removeFirst(), solution.removeFirst());
        }
        for (int i = 0; i <= 10; i++) {
            int last = StdRandom.uniform(11, 20);
            student.addLast(last);
            solution.addLast(last);
        }
        for (int i = 0; i <= 10; i++) {
            assertEquals(student.removeLast(), solution.removeLast());
        }
    }

    @Test
    public void TestDeque2() {
        String msg = "\n";
        for (int i = 0; i <= 10; i++) {
            int first = StdRandom.uniform(10);
            student.addFirst(first);
            solution.addFirst(first);
            msg = msg + "addFirst(" + first + ")\n";
        }
        for (int i = 0; i <= 10; i++) {
            msg = msg + "removeFirst()\n";
            assertEquals(msg, student.removeFirst(), solution.removeFirst());
        }
        for (int i = 0; i <= 10; i++) {
            int last = StdRandom.uniform(11, 20);
            student.addLast(last);
            solution.addLast(last);
            msg = msg + "addLast(" + last + ")\n";
        }
        for (int i = 0; i <= 10; i++) {
            msg = msg + "removeLast()\n";
            assertEquals(msg, student.removeLast(), solution.removeLast());
        }
    }
}