import org.junit.Test;

import static org.junit.Assert.*;
public class TestPalindrome {
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
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("wow"));
        assertTrue(palindrome.isPalindrome("noon"));
        assertTrue(palindrome.isPalindrome("racecar"));
        assertFalse(palindrome.isPalindrome("aaaaaab"));
        assertFalse(palindrome.isPalindrome("rancor"));
        assertFalse(palindrome.isPalindrome("horce"));
        assertFalse(palindrome.isPalindrome("ab"));
        assertFalse(palindrome.isPalindrome(null));
    }

    @Test
    public void testIsPalindromeCC() {
        OffByOne obo = new OffByOne();
        assertTrue(palindrome.isPalindrome("", obo));
        assertTrue(palindrome.isPalindrome("b", obo));
        assertTrue(palindrome.isPalindrome("ab", obo));
        assertTrue(palindrome.isPalindrome("", obo));
        assertTrue(palindrome.isPalindrome("qhfeir", obo));
        assertTrue(palindrome.isPalindrome("flake", obo));
        assertFalse(palindrome.isPalindrome("ac", obo));
        assertFalse(palindrome.isPalindrome("xxyz", obo));
        assertFalse(palindrome.isPalindrome("hello", obo));
        assertFalse(palindrome.isPalindrome("gucci", obo));
        assertFalse(palindrome.isPalindrome(null, obo));
        assertTrue(palindrome.isPalindrome("noon", null));
        assertFalse(palindrome.isPalindrome("ab", null));
        assertFalse(palindrome.isPalindrome("hello", null));
        assertFalse(palindrome.isPalindrome(null, null));
    }
}
