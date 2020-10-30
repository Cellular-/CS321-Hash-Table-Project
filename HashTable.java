import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Array implementation of hash table with linear probing and double
 * hashing collision resolution techniques.
 */
public class HashTable<T> {
    ProbeType probeType;
    HashObject<T>[] hashTable;
    private int tableSize;
    private double loadFactor;
    private int totalDuplicates;
    private int totalProbes;
    private int totalInserts;

    public HashTable(ProbeType probeType, double loadFactor) {
        tableSize = 95791;
        hashTable = new HashObject[tableSize];
        this.probeType = probeType;
        this.loadFactor = loadFactor;
        this.totalDuplicates = 0;
        this.totalProbes = 0;
        this.totalInserts = 0;
    }

    /**
     * Generates hash value for HashObject key.
     * Used in the linear probing process.
     * 
     * @param k - The key of the HashObject instance
     * @return hashCode value for key
     */
    private int hash1(T k) {
        int h = k.hashCode() % tableSize;
        if(h < 0) {
            h += tableSize;
        }

        return h;
    }

    /**
     * Generates hash value for HashObject key.
     * Used in the double hashing process.
     * 
     * @param k - The key of the HashObject instance
     * @return hashCode value for key
     */
    private int hash2(T k) {
        int h = 1 + (k.hashCode() % (tableSize - 2));
        if(h < 0) {
            h += (tableSize - 2);
        }

        return h;
    }

    /**
     * Performs either linear probing or double hashing.
     */
    public void insert(HashObject<T> hashObject) {
        int i = 0; // Used to stop while loop
        int hashValue1 = hash1(hashObject.getKey());
        int hashValue2 = hash2(hashObject.getKey());
        int index = 0; // Index of the hash object to be inserted

        while(i < tableSize) {
            /**
             * Index is recalculated differently based on the probe type.
             */
            if(probeType == ProbeType.LINEAR) {
                index = (hashValue1 + i) % tableSize;
            } else if(probeType == ProbeType.DOUBLEHASH) {
                index = (hashValue1 + (i * hashValue2)) % (tableSize);
            }

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

    public int getTotalInserts() {
        return totalInserts;
    }

    public double getLoadFactor() {
        return loadFactor;
    }

    public double loadFactor() {
        return (double) totalInserts / (double) tableSize;
    }

    /**
     * Writes the hash tables keys, duplicate count and probe count
     * to a file.
     */
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