public class HashTable<T> {
    ProbeType probeType;
    HashObject<T>[] hashTable;
    private int tableSize;
    private double loadFactor;
    private int totalDuplicates;
    private int totalProbes;

    public HashTable(ProbeType probeType, double loadFactor) {
        tableSize = 95791;
        hashTable = new HashObject[tableSize];

        this.probeType = probeType;
        this.loadFactor = loadFactor;
        this.totalDuplicates = 0;
        this.totalProbes = 0;
    }

    private int hash1(int key) {
        return key % tableSize;
    }

    private int hash2(int key) {
        return 1 + (key % (tableSize - 2));
    }

    public void insert(HashObject<T> hashObject) {
        int hashValue = hash1(hashObject.getKey().hashCode());
        if(hashValue < 0) {
            hashValue += tableSize;
        }
        
        if(probeType == ProbeType.LINEAR) {
            int linearProbeValue = hashValue;
            for(int i = 1; hashTable[linearProbeValue] != null; i++) {
                if(hashTable[linearProbeValue].equals(hashObject)) {
                    totalDuplicates++;
                }
                linearProbeValue = hash1(hashValue + i);
                totalProbes++;
            }

            hashTable[linearProbeValue] = hashObject;
        } else if(probeType == ProbeType.DOUBLEHASH) {
            int doubleHashValue = hashValue;
            for(int i = 1; hashTable[doubleHashValue] != null; i++) {
                if(hashTable[doubleHashValue].equals(hashObject)) {
                    totalDuplicates++;
                }
                doubleHashValue = (hashValue + (i * hash2(hashValue))) % tableSize;
                totalProbes++;
            }

            hashTable[doubleHashValue] = hashObject;
        }
    }

    public int getTotalDuplicates() {
        return totalDuplicates;
    }

    public int getTotalProbes() {
        return totalProbes;
    }

    public int getTableSize() {
        return tableSize;
    }
}