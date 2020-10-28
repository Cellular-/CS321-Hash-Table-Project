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

        HashTable<Integer> arr = new HashTable<Integer>(ProbeType.LINEAR, .5);

        Random rng = new Random();
        for(int i = 0; i < (int) (arr.getTableSize() * loadFactor); i++) {
            int randInt = rng.nextInt();
            arr.insert(new HashObject<Integer>(randInt, randInt));
        }

        System.out.println(arr.getTotalDuplicates());
        System.out.println(arr.getTotalProbes());
    }

    public static String usage() {
        return "\nUsage is: java HashTest <datasource> <load_factor>\n" +
                "<datasource> options:\n\t1 for random integers\n\t2 for system milliseconds\n\t3 for file with strings\n" +
                "<load_factor> options:\n\tA decimal greater than zero and less than 1\n";
    }
}