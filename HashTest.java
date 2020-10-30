import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class HashTest {
    public static void main(String[] args) {
        if(args.length > 3 && args.length < 2) {
            System.out.println(usage());
            throw new IllegalArgumentException();
        }

        run(args);
    }

    public static String usage() {
        return "\nUsage is: java HashTest <datasource> <load_factor>\n" +
                "<datasource> options:\n\t1 for random integers\n\t2 for system milliseconds\n\t3 for word-list\n" +
                "<load_factor> options:\n\tA decimal greater than zero and less than 1\n";
    }

    public static void debugZero(HashTable<?> linearHash, HashTable<?> doubleHash, String dataSourceType, int attempts) {
        System.out.println("A good table size is found: " + linearHash.getTableSize());
        System.out.println("Data source type: " + dataSourceType);
        System.out.println();
        System.out.println("Using Linear Hashing....");
        System.out.println("Input " + attempts + " elements, of which " + linearHash.getTotalDuplicates() + " duplicates");
        System.out.println("load factor = " + linearHash.getLoadFactor() + ", Avg. no of probes " + ((double) linearHash.getTotalProbes() / (double) linearHash.getTotalInserts()));
        System.out.println();
        System.out.println("Using Double Hashing....");
        System.out.println("Input " + attempts + " elements, of which " + doubleHash.getTotalDuplicates() + " duplicates");
        System.out.println("load factor = " + doubleHash.getLoadFactor() + ", Avg. no of probes " + (double) ((double) (doubleHash.getTotalProbes()) / (double) (doubleHash.getTotalInserts())));
    }

    public static void run(String args[]) {
        int dataSource = Integer.parseInt(args[0]);
        String dataSourceType = "";
        double loadFactor = Double.parseDouble(args[1]);
        int debugMode = args.length == 3 ? Integer.parseInt(args[2]) : 0;

        if(dataSource == 1) {
            HashTable<Integer> linearHashTable = new HashTable<Integer>(ProbeType.LINEAR, loadFactor);
            HashTable<Integer> doubleHashTable = new HashTable<Integer>(ProbeType.DOUBLEHASH, loadFactor);

            Random rng = new Random();
            int attempts = 0;
            for(int i = 0; linearHashTable.loadFactor() < loadFactor; i++) {
                int randInt = rng.nextInt();
                linearHashTable.insert(new HashObject<Integer>(randInt));
                doubleHashTable.insert(new HashObject<Integer>(randInt));
                attempts = i;
            }

            dataSourceType = "Random integers";

            debugZero(linearHashTable, doubleHashTable, dataSourceType, attempts);
            if(debugMode == 1) {
                linearHashTable.printDump();
                doubleHashTable.printDump();
            }
        } else if(dataSource == 2) {
            HashTable<Long> linearHashTable = new HashTable<Long>(ProbeType.LINEAR, loadFactor);
            HashTable<Long> doubleHashTable = new HashTable<Long>(ProbeType.DOUBLEHASH, loadFactor);

            int attempts = 0;
            for(int i = 0; linearHashTable.loadFactor() < loadFactor; i++) {
                Long ms = System.currentTimeMillis();
                linearHashTable.insert(new HashObject<Long>(ms));
                doubleHashTable.insert(new HashObject<Long>(ms));
                attempts = i;
            }

            dataSourceType = "System milliseconds";

            debugZero(linearHashTable, doubleHashTable, dataSourceType, attempts);
            if(debugMode == 1) {
                linearHashTable.printDump();
                doubleHashTable.printDump();
            }
        } else if(dataSource == 3) {
            HashTable<String> linearHashTable = new HashTable<String>(ProbeType.LINEAR, loadFactor);
            HashTable<String> doubleHashTable = new HashTable<String>(ProbeType.DOUBLEHASH, loadFactor);
            int attempts = 0;
            try {
                BufferedReader br = new BufferedReader(new FileReader("word-list"));

                String word;
                while(((word = br.readLine()) != null) && linearHashTable.loadFactor() < loadFactor) {
                    linearHashTable.insert(new HashObject<String>(word));
                    doubleHashTable.insert(new HashObject<String>(word));
                    attempts++;
                }

                br.close();
            } catch(IOException e) {
                System.out.println("Could not open file!");
            }

            dataSourceType = "word-list";

            debugZero(linearHashTable, doubleHashTable, dataSourceType, attempts);
            if(debugMode == 1) {
                linearHashTable.printDump();
                doubleHashTable.printDump();
            }
        }
    }
}