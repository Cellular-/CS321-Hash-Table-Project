public class HashObject<T> {
    private T key;
    private int duplicateCount;
    private int probeCount;

    public HashObject(T key) {
        this.key = key;
        this.duplicateCount = 0;
        this.probeCount = 0;
    }

    public T getKey() {
        return key;
    }

    public void increaseDuplicateCount() {
        duplicateCount += 1;
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
            return (this.getKey().equals(((HashObject<?>)obj).getKey()));
        }

        return false;
    }

    @Override
    public String toString() {
        return key.toString() + " " + duplicateCount + " " + probeCount;
    }
}