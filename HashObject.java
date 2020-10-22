public class HashObject<T> {
    private T key;
    private int duplicateCount;
    private int probeCount;

    public HashObject(T key) {
        this.key = key;
        duplicateCount = 0;
        probeCount = 0;
    }

    public T getKey() {
        return key;
    }

    public void increaseDuplicateCount() {
        duplicateCount++;
    }

    public void increaseProbeCount() {
        probeCount++;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof HashObject<?>)) {
            return false;
        }

        HashObject<T> otherObj = (HashObject<T>) obj;

        return value.equals(otherObj.getValue());
    }

    @Override
    public String toString() {
        return "mystring";
    }
}