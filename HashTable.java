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
        return 1 + (key % tableSize - 2);
    }

    public void insert(HashObject<T> hashObject) {
        int hashValue = hash1(hashObject.getKey().hashCode());
        if(hashValue < 0) {
            hashValue += tableSize;
        }
        
        if(probeType == ProbeType.LINEAR) {
            for(int i = 1; hashTable[hashValue] != null; i++) {
                if(hashTable[hashValue].equals(hashObject)) {
                    totalDuplicates++;
                }
                hashValue = hash1(hashValue + i);
                totalProbes++;
            }

            hashTable[hashValue] = hashObject;
        } else if(probeType == ProbeType.DOUBLEHASH) {
            for(int i = 1; hashTable[hashValue] != null; i++) {
                if(hashTable[hashValue].equals(hashObject)) {
                    totalDuplicates++;
                }
                hashValue = hashValue + i * hash2(hashValue);
                totalProbes++;
            }

            hashTable[hashValue] = hashObject;
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