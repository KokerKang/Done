import static java.lang.Math.abs;

/** The class ofr the OffBy one.
 *
 * @author Henry Kang 09/15/20
 * */
public class OffByOne implements CharacterComparator {

    /** Method that helping to check is difference between two words
     * are 1 or not.
     * @param x  the char is what we want to check for palindrome
     * @param y  the char is what we want to check for palindrome
     *
     * @return return whether word difference is one or not*/
    @Override
    public boolean equalChars(char x, char y) {
        return abs(x - y) == 1;
    }
}
