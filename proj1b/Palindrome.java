public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> d = new LinkedListDeque<>();
        for(int i = 0; i < word.length(); i++){
            d.addLast(word.charAt(i));
        }
        return d;
    }
    // in recursive way helper method with one parameter
    /*public boolean isPalindromerecursive(String word) {
        if (word.length() <= 1) {
            return true;
        } else {
            Deque d = this.wordToDeque(word);
            return isPalindromerecursiveHelper(d);
        }
    }
    private boolean isPalindromerecursiveHelper(Deque d) {
        if (d.size() <= 1) {
            return true;
        } else if (d.removeFirst() == d.removeLast()) {
            return isPalindromerecursiveHelper(d);
        } else {
            return false;
        }
    }
    */
    public boolean isPalindrome(String word) {
        if (word.length() <= 1) {
            return true;
        }
        return isPalindromeHelper(word, 0);
    }
    private boolean isPalindromeHelper(String word, int charIndex) {
        int charIndexInReversedWord = word.length() - 1 - charIndex;
        if (word.charAt(charIndex) != word.charAt(charIndexInReversedWord)) {
            return false;
        }
        if (charIndex >= word.length()/2) {
            return  true;
        }
        return isPalindromeHelper(word, charIndex + 1);
    }
    public boolean isPalindrome(String word, CharacterComparator cc) {
        return isPalindromeHelper(word, cc, 0);
    }
    public boolean isPalindromeHelper(String word, CharacterComparator cc, int charIndex) {
        int charIndexInReversedWord = word.length() - 1 - charIndex;
        if (cc.equalChars(word.charAt(charIndex), word.charAt(charIndexInReversedWord))) {
            return false;
        }
        if (charIndex >= word.length() / 2) {
            return true;
        }
        return isPalindromeHelper(word, cc, charIndex + 1);
    }
}
