public class HashObject<T> {
    private T key;
    private T value;
    private int duplicateCount;
    private int probeCount;

    public HashObject(T key) {
        this.key = key;
        duplicateCount = 0;
        probeCount = 0;
    }

    public HashObject(T key, T value) {
        this(key);
        this.value = value;
    }

    public T getKey() {
        return key;
    }

    public T getValue() {
        return value;
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
        
        return key.equals(otherObj.getKey());
    }

    @Override
    public String toString() {
        return "mystring";
    }
}