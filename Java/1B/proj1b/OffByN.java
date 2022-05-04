import static java.lang.Math.abs;

/** The class ofr the OffBy N.
 *
 * @author Henry Kang 09/15/20
 * */
public class OffByN implements CharacterComparator {

    /** int type to store what number is difference with.*/
    private int num_N;

    /** Setting the number of difference.
     * @param N to get difference num*/
    public OffByN(int N) {
        num_N = N;
    }

    /** Method that helping to check is difference between two words
     * are N or not.
     * @param x  the char is what we want to check for palindrome
     * @param y  the char is what we want to check for palindrome
     *
     * @return return whether word difference is N or not*/
    @Override
    public boolean equalChars(char x, char y) {
        return abs(x - y) == num_N;
    }
}
