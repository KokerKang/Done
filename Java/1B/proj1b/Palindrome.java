/** Palindrome class.
 *
 *
 *@author Henry Kang 09/15/20
 **/
public class Palindrome {

    /** Method that convert from String to char, then put into array list.
     * @param word the word which is that we
     *             want to put into lst by char one by one.
     * @return return the Deque type of word. */
    public Deque<Character> wordToDeque(String word) {
        ArrayDeque<Character> lst = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++) {
            lst.addLast(word.charAt(i));
        }
        return lst;
    }
    /* https://www.javatpoint.com/java-string-to-char
     * reference from this site for the convert string to char.*/

    /** Method that check the word is whether
     *  palindrome or not. recursive version
     * @param word  the word is what we want to check for palindrome.
     * @return return whether word is palindrome or not*/
    public boolean isPalindrome(String word) {
        if (word == null) {
            return false;
        }
        if (word.length() == 1) {
            return true;
        }
        Deque<Character> temp = wordToDeque(word);
        while (temp.size() > 1) {
            if (temp.removeFirst() != temp.removeLast()) {
                return false;
            }
        }
        return true;
    }

    /** Method that helping to check isPalindrome for recursive version.
     * @param word  the word is what we want to check for palindrome
     *             and it will be deleted after checking.
     * @return return whether word is Palindrome or not by recursion
    private boolean helper(Deque<Character> word) {
        if (word.size() <= 1) {
            return true;
        }
        if (word.removeLast() != word.removeFirst()) {
            return false;
        } else {
            return helper(word);
        }
    }
    */

    /** Method that helping to check isPalindrome for non-recursive version.
     * Also, it has some option that Character Comparator cc.
     * @param word  the word is what we want to check for palindrome
     *             and it will be deleted after checking.
     * @param cc    how we check the palindrome word.
     * @return return whether word is Palindrome or not by cc method.*/
    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word == null) {
            return false;
        }
        if (cc == null) {
            return isPalindrome(word);
        }
        if (word.length() == 1) {
            return true;
        }
        Deque<Character> temp = wordToDeque(word);
        while (temp.size() > 1) {
            char first = temp.removeFirst();
            char last = temp.removeLast();
            if (!(cc.equalChars(first, last))) {
                return false;
            }
        }
        return true;
    }
}
