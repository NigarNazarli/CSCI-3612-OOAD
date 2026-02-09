/*
 * Name: YOUR NAME
 * Project: Random Number Generator Statistics
 * Class: Object-Oriented Analysis & Design
 * Date: YYYY-MM-DD
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/*
 * CLASS DEFINITION
 * ----------------
 * The Generator class generates random numbers using different
 * built-in Java random number generators and analyzes the data
 * using descriptive statistics.
 */
public class Generator {

    /*
     * CLASS ATTRIBUTES
     * ----------------
     * Constants used to select which random number generator
     * will be used in the populate method.
     * Demonstrates accessibility using the private modifier.
     */
    private static final int RAND_JAVA_UTIL = 0;      // java.util.Random
    private static final int RAND_MATH = 1;           // Math.random()
    private static final int RAND_THREAD_LOCAL = 2;   // ThreadLocalRandom

    /*
     * METHOD DEFINITION
     * -----------------
     * populate
     * Generates n random double values in the range [0, 1)
     * using the specified random number generator.
     */
    public ArrayList<Double> populate(int n, int randNumGen) {

        // OBJECT INSTANTIATION
        ArrayList<Double> values = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            double value;

            if (randNumGen == RAND_JAVA_UTIL) {
                value = rand.nextDouble();
            } else if (randNumGen == RAND_MATH) {
                value = Math.random();
            } else if (randNumGen == RAND_THREAD_LOCAL) {
                value = ThreadLocalRandom.current().nextDouble();
            } else {
                value = 0.0; // safety fallback
            }

            values.add(value);
        }

        return values;
    }

    /*
     * METHOD DEFINITION
     * -----------------
     * statistics
     * Computes the number of elements, mean, sample standard deviation,
     * minimum, and maximum values.
     * Returns results in the order:
     * [n, mean, stddev, min, max]
     */
    public ArrayList<Double> statistics(ArrayList<Double> randomValues) {

        int n = randomValues.size();
        double sum = 0.0;
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;

        for (double v : randomValues) {
            sum += v;
            min = Math.min(min, v);
            max = Math.max(max, v);
        }

        double mean = sum / n;

        // Sample standard deviation
        double varianceSum = 0.0;
        for (double v : randomValues) {
            varianceSum += Math.pow(v - mean, 2);
        }
        double stddev = Math.sqrt(varianceSum / (n - 1));

        ArrayList<Double> results = new ArrayList<>();
        results.add((double) n);
        results.add(mean);
        results.add(stddev);
        results.add(min);
        results.add(max);

        return results;
    }

    /*
     * METHOD DEFINITION
     * -----------------
     * display
     * Displays the statistical results in a formatted table.
     * The header is printed only once when requested.
     */
    public void display(ArrayList<Double> results, boolean headerOn) {

        if (headerOn) {
            System.out.printf("%-10s %-10s %-15s %-10s %-10s%n",
                    "n", "Mean", "Std Dev", "Min", "Max");
            System.out.println("---------------------------------------------------------");
        }

        System.out.printf("%-10.0f %-10.4f %-15.4f %-10.4f %-10.4f%n",
                results.get(0),
                results.get(1),
                results.get(2),
                results.get(3),
                results.get(4));
    }

    /*
     * METHOD DEFINITION
     * -----------------
     * execute
     * Calls populate, statistics, and display methods for all
     * combinations of sample sizes and random number generators.
     */
    public void execute() {

        int[] sampleSizes = {10, 100, 1000};
        boolean headerPrinted = false;

        for (int n : sampleSizes) {
            for (int gen = RAND_JAVA_UTIL; gen <= RAND_THREAD_LOCAL; gen++) {
                ArrayList<Double> data = populate(n, gen);
                ArrayList<Double> stats = statistics(data);
                display(stats, !headerPrinted);
                headerPrinted = true;
            }
        }
    }

    /*
     * MAIN METHOD
     * -----------
     * Minimal main method as required by the assignment.
     * Demonstrates object instantiation.
     */
    public static void main(String[] args) {

        // OBJECT INSTANTIATION
        Generator g = new Generator();
        g.execute();
    }
}
