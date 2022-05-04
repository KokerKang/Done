import org.junit.Test;

import static org.junit.Assert.*;

public class TestOffByOne {
    static CharacterComparator offByOne = new OffByOne();
    @Test
    public void testOffByOne() {
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('@', 'A'));
        assertTrue(offByOne.equalChars('q', 'r'));
        assertTrue(offByOne.equalChars('x', 'y'));
        assertTrue(offByOne.equalChars('y', 'z'));


        assertFalse(offByOne.equalChars('a', 'B'));

        assertFalse(offByOne.equalChars('a', 'd'));
        assertFalse(offByOne.equalChars('b', 'd'));
        assertFalse(offByOne.equalChars('+', '-'));
        assertFalse(offByOne.equalChars('n', 'p'));
        assertFalse(offByOne.equalChars('a', 'z'));
    }
}
