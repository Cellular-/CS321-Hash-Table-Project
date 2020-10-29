public class HashTable<T> {
    ProbeType probeType;
    HashObject<T>[] hashTable;
    private int tableSize;
    private double loadFactor;
    private int totalDuplicates;
    private int totalProbes;
    private int totalInputs;
    private int totalInserts;

    public HashTable(ProbeType probeType, double loadFactor) {
        tableSize = 95791;
        hashTable = new HashObject[tableSize];
        this.probeType = probeType;
        this.loadFactor = loadFactor;
        this.totalDuplicates = 0;
        this.totalProbes = 0;
        this.totalInputs = 0;
        this.totalInserts = 0;
    }

    private int hash1(T k) {
        int h = k.hashCode() % tableSize;
        if(h < 0) {
            h += tableSize;
        }

        return h;
    }

    private int hash2(T k) {
        int h = 1 + (k.hashCode() % (tableSize - 2));

        if(h < 0) {
            h += tableSize;
        }

        return h;
    }

    public void insert(HashObject<T> hashObject) {
        // totalInputs++;
        // totalProbes++;
        if(probeType == ProbeType.LINEAR) {
            int i = 0;
            int hashValue = hash1(hashObject.getKey());
            // int index = (hashValue + i) % tableSize;

            // if(index < 0) {
            //     index += tableSize;
            // }
            
            // for(int j = 0; hashTable[index] != null; j++) {
            //     if(hashTable[index].equals(hashObject)) {
            //         totalDuplicates++;
            //         hashObject.increaseDuplicateCount();
            //         return;
            //     } else {
            //         totalProbes++;
            //         hashObject.increaseProbeCount();
            //     }

            //     i++;
            //     index = (hashValue + i) % tableSize;

            //     if(index < 0) {
            //         index += tableSize;
            //     }
            // }

            while(i != tableSize) {
                int index = (hashValue + i) % tableSize;

                if(index < 0) {
                    index += tableSize;
                }

                if(hashTable[index] == null) {
                    hashTable[index] = hashObject;
                    totalInserts++;
                    totalProbes += i + 1;
                    hashObject.setProbeCount(i + 1);
                    return;
                } else if(hashTable[index].equals(hashObject)) {
                    totalDuplicates++;
                    hashObject.increaseDuplicateCount();
                    return;
                }

                i++;
            }

            // hashTable[index] = hashObject;
            // totalInserts++;
        } else if(probeType == ProbeType.DOUBLEHASH) {
            int hashValue1 = hash1(hashObject.getKey());
            int hashValue2 = hash2(hashObject.getKey());
            int i = 0;
            // int index = (hashValue1 + (i * hashValue2)) % tableSize;

            // if(index < 0) {
            //     index += tableSize;
            // }

            // while(i != tableSize) {
            //     int index = (hashValue1 + (i * hashValue2)) % tableSize;

            //     if(index < 0) {
            //         index += tableSize;
            //     }

            //     if(hashTable[index] == null) {
            //         hashTable[index] = hashObject;
            //         totalInserts++;
            //         totalProbes += i + 1;
            //         hashObject.setProbeCount(i + 1);
            //         return;
            //     } else if(hashTable[index].equals(hashObject)) {
            //         totalDuplicates++;
            //         hashObject.increaseDuplicateCount();
            //         return;
            //     }

            //     i++;
            // }

            while(i != tableSize) {
                int index = (hashValue1 + (i * hashValue2)) % tableSize;

                if(index < 0) {
                    index += tableSize;
                }

                if(hashTable[index] == null) {
                    hashTable[index] = hashObject;
                    totalInserts++;
                    totalProbes += i + 1;
                    hashObject.setProbeCount(i + 1);
                    return;
                } else if(hashTable[index].equals(hashObject)) {
                    totalDuplicates++;
                    hashObject.increaseDuplicateCount();
                    return;
                }

                i++;
            }

            // while(hashTable[index] != null) {
            //     if(hashTable[index].equals(hashObject)) {
            //         totalDuplicates++;
            //         hashObject.increaseDuplicateCount();
            //         return;
            //     } else {
            //         totalProbes++;
            //         hashObject.increaseProbeCount();
            //     }

            //     i++;
            //     index = (hashValue1 + (i * hashValue2)) % tableSize;

            //     if(index < 0) {
            //         index += tableSize;
            //     }
            // }

            // hashTable[index] = hashObject;
            // totalInserts++;

            // return;
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

    public int getTotalInputs() {
        return totalInputs;
    }

    public int getTotalInserts() {
        return totalInserts;
    }

    public double getLoadFactor() {
        return loadFactor;
    }

    public double loadFactor() {
        return (double) totalInserts / (double) tableSize;
    }
}