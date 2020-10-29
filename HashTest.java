import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class HashTest {
    public static void main(String[] args) {
        if(args.length > 3 && args.length < 2) {
            System.out.println(usage());
            throw new IllegalArgumentException();
        }

        int dataSource = Integer.parseInt(args[0]);
        String dataSourceType = "";
        double loadFactor = Double.parseDouble(args[1]);
        int debugMode = args.length == 3 ? Integer.parseInt(args[2]) : 0;

        if(dataSource == 1) {
            HashTable<Integer> linearHashTable = new HashTable<Integer>(ProbeType.LINEAR, loadFactor);
            HashTable<Integer> doubleHashTable = new HashTable<Integer>(ProbeType.DOUBLEHASH, loadFactor);

            Random rng = new Random();
            // Save i to some other variable to print input later.
            for(int i = 0; i < (int) (doubleHashTable.getTableSize() * loadFactor); i++) {
                int randInt = rng.nextInt();
                linearHashTable.insert(new HashObject<Integer>(randInt));
                doubleHashTable.insert(new HashObject<Integer>(randInt));
            }

            dataSourceType = "Random integers";

            if(debugMode == 0) {
                debugZero(linearHashTable, doubleHashTable, dataSourceType);
            } else if(debugMode == 1) {
    
            }
        } else if(dataSource == 2) {
            HashTable<Long> linearHashTable = new HashTable<Long>(ProbeType.LINEAR, loadFactor);
            HashTable<Long> doubleHashTable = new HashTable<Long>(ProbeType.DOUBLEHASH, loadFactor);
            for(int i = 0; i < (int) (doubleHashTable.getTableSize() * loadFactor); i++) {
                Long ms = System.currentTimeMillis();
                linearHashTable.insert(new HashObject<Long>(ms));
                doubleHashTable.insert(new HashObject<Long>(ms));
            }

            dataSourceType = "System milliseconds";

            if(debugMode == 0) {
                debugZero(linearHashTable, doubleHashTable, dataSourceType);
            } else if(debugMode == 1) {
    
            }
        } else if(dataSource == 3) {
            HashTable<String> linearHashTable = new HashTable<String>(ProbeType.LINEAR, loadFactor);
            HashTable<String> doubleHashTable = new HashTable<String>(ProbeType.DOUBLEHASH, loadFactor);

            try {
                BufferedReader br = new BufferedReader(new FileReader("word-list"));

                String word;
                while(((word = br.readLine()) != null) && linearHashTable.loadFactor() < loadFactor) {
                    linearHashTable.insert(new HashObject<String>(word));
                    doubleHashTable.insert(new HashObject<String>(word));
                }

                br.close();
            } catch(IOException e) {
                System.out.println("Could not open file!");
            }

            dataSourceType = "word-list";

            if(debugMode == 0) {
                debugZero(linearHashTable, doubleHashTable, dataSourceType);
            } else if(debugMode == 1) {
    
            }
        }


    }

    public static String usage() {
        return "\nUsage is: java HashTest <datasource> <load_factor>\n" +
                "<datasource> options:\n\t1 for random integers\n\t2 for system milliseconds\n\t3 for file with strings\n" +
                "<load_factor> options:\n\tA decimal greater than zero and less than 1\n";
    }

    public static void debugZero(HashTable<?> linearHash, HashTable<?> doubleHash, String dataSourceType) {
        System.out.println("A good table size is found: 95791");
        System.out.println("Data source type: " + dataSourceType);
        System.out.println();
        System.out.println("Using Linear Hashing....");
        System.out.println("Input " + linearHash.getTotalInserts() + " elements, of which " + linearHash.getTotalDuplicates() + " duplicates");
        System.out.println("load factor = " + linearHash.getLoadFactor() + ", Avg. no of probes " + ((double) linearHash.getTotalProbes() / (double) linearHash.getTotalInserts()));
        System.out.println();
        System.out.println("Using Double Hashing....");
        System.out.println("Input " + doubleHash.getTotalInserts() + " elements, of which " + doubleHash.getTotalDuplicates() + " duplicates");
        System.out.println("load factor = " + doubleHash.getLoadFactor() + ", Avg. no of probes " + ((double) doubleHash.getTotalProbes() / (double) doubleHash.getTotalInserts()));
    }
}