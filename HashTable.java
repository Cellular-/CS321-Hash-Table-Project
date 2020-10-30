import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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
        if(probeType == ProbeType.LINEAR) {
            int i = 0;
            int hashValue = hash1(hashObject.getKey());

            while(i != tableSize) {
                int index = (hashValue + i) % tableSize;

                if(index < 0) {
                    index += tableSize;
                }

                if(hashTable[index] == null) {
                    hashTable[index] = hashObject;
                    totalInserts++;
                    totalProbes += i + 1;
                    hashTable[index].setProbeCount(i + 1);
                    return;
                } else if(hashTable[index].equals(hashObject)) {
                    totalDuplicates++;
                    hashTable[index].increaseDuplicateCount();
                    return;
                }

                i++;
            }
        } else if(probeType == ProbeType.DOUBLEHASH) {
            // int hashValue1 = hash1(hashObject.getKey());
            // int hashValue2 = hash2(hashObject.getKey());
            // int x = 0;
            // int j = 0;

            // while(x != tableSize) {
            //     int index;
            //     if(x > 0) {
            //         index = (hashValue1 + (j * hashValue2)) % tableSize;
            //     } else {
            //         index = (hashValue1 + (j * hashValue2));
            //     }


            //     if(index < 0) {
            //         index += tableSize;
            //     }

            //     if(hashTable[index] == null) {
            //         hashTable[index] = hashObject;
            //         totalInserts++;
            //         totalProbes += x + 1;
            //         hashTable[index].setProbeCount(x + 1);
            //         return;
            //     } else if(hashTable[index].equals(hashObject)) {
            //         totalDuplicates++;
            //         hashTable[index].increaseDuplicateCount();
            //         return;
            //     } else {
            //         j++;
            //     }

            //     x++;
            // }

            int hashValue = hash1(hashObject.getKey());
            int offset = hash2(hashObject.getKey());
            
            while(hashTable[hashValue] != null) {
                /**
                 * If there's a duplicate, increase duplicate count and
                 * stop the +
                 */

                if(hashTable[hashValue].equals(hashObject)) {
                    hashTable[hashValue].increaseDuplicateCount();
                    totalDuplicates++;
                    return;
                }

                totalProbes++;
                hashTable[hashValue].increaseProbeCount();
                hashValue += offset;
                hashValue %= tableSize;
            }

            hashTable[hashValue] = hashObject;
            totalInserts++;
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

    public void printDump() {
        String name = probeType == ProbeType.LINEAR ? "linear-dump" : "double-dump";

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(name));
            for(int i = 0; i < tableSize; i++) {
                if(hashTable[i] != null) {
                    bw.write("table[" + i + "]: " + hashTable[i].toString() + "\n");
                }
            }
            bw.close();
        } catch(IOException e) {
            System.out.println("Could not write to file.");
        }
    }
}