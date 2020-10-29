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

    public void setProbeCount(int i) {
        probeCount = i;
    }

    @Override
    public boolean equals(Object obj) {
        if(this.getClass().equals(obj.getClass())) {
            return (this.getKey().equals(((HashObject<T>)obj).getKey()));
        }

        return false;
    }




    @Override
    public String toString() {
        return key.toString() + " " + duplicateCount + " " + probeCount;
    }
}