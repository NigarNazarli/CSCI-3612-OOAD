public class Main {

    public static void main(String[] args) {

        RingBuffer<Integer> buffer = new RingBuffer<>(5);

        Writer<Integer> writer = new Writer<>(buffer);
        Reader<Integer> fastReader = new Reader<>(buffer);
        Reader<Integer> slowReader = new Reader<>(buffer);

        System.out.println("=== Writer writes first 8 values ===");

        for (int i = 1; i <= 8; i++) {
            writer.write(i);
        }

        System.out.println("\n=== Fast reader reads immediately ===");

        for (int i = 0; i < 5; i++) {
            System.out.println("FastReader read: " + fastReader.read());
        }

        System.out.println("\n=== Writer writes more values (causes overwrite) ===");

        for (int i = 9; i <= 15; i++) {
            writer.write(i);
        }

        System.out.println("\n=== Slow reader tries to read after delay ===");

        for (int i = 0; i < 10; i++) {
            System.out.println("SlowReader read: " + slowReader.read());
        }
    }
}