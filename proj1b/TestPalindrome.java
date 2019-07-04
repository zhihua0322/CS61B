import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testisPalindrome() {
        assertTrue(palindrome.isPalindrome("noon"));
        assertTrue(palindrome.isPalindrome("A"));
        assertFalse(palindrome.isPalindrome("aA"));
        assertFalse(palindrome.isPalindrome("persiflage"));
    }

    @Test
    public void testisPalindrome1() {
        OffByOne obo = new OffByOne();
        assertTrue(palindrome.isPalindrome("noon", obo));
        assertTrue(palindrome.isPalindrome("A", obo));
        assertFalse(palindrome.isPalindrome("aA", obo));
        assertFalse(palindrome.isPalindrome("persiflage", obo));
    }
} //    Uncomment this class once you've created your Palindrome class.