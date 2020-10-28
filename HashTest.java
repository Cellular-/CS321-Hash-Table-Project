import java.util.Random;

public class HashTest {
    public static void main(String[] args) {
        if(args.length > 3 && args.length < 2) {
            System.out.println(usage());
            throw new IllegalArgumentException();
        }

        int dataSource = Integer.parseInt(args[0]);
        double loadFactor = Double.parseDouble(args[1]);
        int debugMode = args.length == 3 ? Integer.parseInt(args[2]) : null;

        HashTable<Integer> linearHashTable = new HashTable<Integer>(ProbeType.LINEAR, loadFactor);
        HashTable<Integer> doubleHashTable = new HashTable<Integer>(ProbeType.DOUBLEHASH, loadFactor);

        Random rng = new Random();
        for(int i = 0; i < (int) (doubleHashTable.getTableSize() * loadFactor); i++) {
            int randInt = rng.nextInt();
            linearHashTable.insert(new HashObject<Integer>(randInt, randInt));
            doubleHashTable.insert(new HashObject<Integer>(randInt, randInt));
        }
        
        HashTable<Integer> l = new HashTable<Integer>(ProbeType.LINEAR, loadFactor);
        HashTable<Integer> d = new HashTable<Integer>(ProbeType.DOUBLEHASH, loadFactor);
        for(int i = 0; i < (int) (doubleHashTable.getTableSize() * loadFactor); i++) {
            linearHashTable.insert(new HashObject<Long>(System.currentTimeMillis(), System.currentTimeMillis()));
            doubleHashTable.insert(new HashObject<Long>(System.currentTimeMillis(), System.currentTimeMillis()));
        }
        

        System.out.println(linearHashTable.getTotalDuplicates());
        System.out.println(linearHashTable.getTotalProbes());

        System.out.println(doubleHashTable.getTotalDuplicates());
        System.out.println(doubleHashTable.getTotalProbes());
    }

    public static String usage() {
        return "\nUsage is: java HashTest <datasource> <load_factor>\n" +
                "<datasource> options:\n\t1 for random integers\n\t2 for system milliseconds\n\t3 for file with strings\n" +
                "<load_factor> options:\n\tA decimal greater than zero and less than 1\n";
    }
}