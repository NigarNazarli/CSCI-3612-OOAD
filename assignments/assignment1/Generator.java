/*
 * Name: Nigar Nazarli
 * Project: Random Number Generator Statistics
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/*
 * =========================
 * CLASS DEFINITION
 * =========================
 * This class generates random numbers using different built-in
 * Java random number generators and analyzes the generated data
 * using descriptive statistics.
 */
public class Generator {

    /*
     * =========================
     * CLASS ATTRIBUTES
     * =========================
     * These constants are used to identify which random number
     * generator should be used. They also demonstrate the use
     * of private accessibility and class-level attributes.
     */
    private static final int RAND_JAVA_UTIL = 0;
    private static final int RAND_MATH = 1;
    private static final int RAND_THREAD_LOCAL = 2;

    /*
     * =========================
     * METHOD DEFINITION
     * =========================
     * populate
     * --------
     * This method generates n random double values in the range [0, 1).
     * The specific random number generator is selected using the
     * randNumGen parameter.
     *
     * @param n          number of random values to generate
     * @param randNumGen identifier of the random number generator
     * @return ArrayList containing n random double values
     */
    public ArrayList<Double> populate(int n, int randNumGen) {

        /* OBJECT INSTANTIATION */
        ArrayList<Double> values = new ArrayList<>(n);
        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            double value;

            // Select the appropriate random number generator
            if (randNumGen == RAND_JAVA_UTIL) {
                value = rand.nextDouble();
            } else if (randNumGen == RAND_MATH) {
                value = Math.random();
            } else if (randNumGen == RAND_THREAD_LOCAL) {
                value = ThreadLocalRandom.current().nextDouble();
            } else {
                value = 0.0; // fallback case (should not occur)
            }

            values.add(value);
        }

        return values;
    }

    /*
     * =========================
     * METHOD DEFINITION
     * =========================
     * statistics
     * ----------
     * This method calculates descriptive statistics for the given
     * list of random values. The returned results follow this order:
     * [n, mean, sample standard deviation, minimum, maximum].
     *
     * @param randomValues list of generated random values
     * @return ArrayList containing calculated statistics
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

        // Sample standard deviation using Bessel’s correction (n - 1)
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
     * =========================
     * METHOD DEFINITION
     * =========================
     * display
     * -------
     * This method prints the statistical results in a tabular
     * format to the system console. The header is printed only
     * once, based on the headerOn flag.
     *
     * @param results  list containing statistical values
     * @param headerOn indicates whether the table header is printed
     */
    public void display(ArrayList<Double> results, boolean headerOn) {

        if (headerOn) {
            System.out.printf("%-12s %-12s %-15s %-12s %-12s%n",
                    "n", "Mean", "Std Dev", "Min", "Max");
            System.out.println("---------------------------------------------------------------");
        }

        System.out.printf("%-12.0f %-12.6f %-15.6f %-12.6f %-12.6f%n",
                results.get(0),
                results.get(1),
                results.get(2),
                results.get(3),
                results.get(4));
    }

    /*
     * =========================
     * METHOD DEFINITION
     * =========================
     * execute
     * -------
     * This method runs the entire experiment by calling populate,
     * statistics, and display for all combinations of sample sizes
     * and random number generators. A total of nine results are produced.
     */
    public void execute() {

        // Includes a very large sample size to clearly show convergence
        int[] sampleSizes = {10, 1000, 1_000_000};
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
     * =========================
     * MAIN METHOD
     * =========================
     * The main method is intentionally minimal. It only creates
     * an instance of the Generator class and calls the execute method,
     * as required by the assignment.
     */
    public static void main(String[] args) {

        /* OBJECT INSTANTIATION */
        Generator g = new Generator();
        g.execute();
    }
}
