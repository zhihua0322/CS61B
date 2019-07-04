public class OffByN implements CharacterComparator{
    private int N;
    public OffByN(int N) {
        this.N = N;
    }
    public boolean equalChars(char x, char y) {
        int diff = x - y;
        return Math.abs(diff) == this.N;
    }
}
