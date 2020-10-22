public class HashObject<T> {
    private int key;
    private T value;
    private int duplicateCount;
    private int probeCount;

    public HashObject(int key, T value) {
        this.key = key;
        this.value = value;
        duplicateCount = 0;
        probeCount = 0;
    }

    public int getKey() {
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

        return value.equals(otherObj.getValue());
    }

    @Override
    public String toString() {
        return "mystring";
    }
}