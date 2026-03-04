public class Reader<T> {

    private final RingBuffer<T> buffer;
    private long nextSequence;

    public Reader(RingBuffer<T> buffer) {
        this.buffer = buffer;

        // Start from the oldest available item
        long writeSeq = buffer.getWriteSequence();
        long capacity = buffer.getCapacity();

        if (writeSeq > capacity) {
            this.nextSequence = writeSeq - capacity;
        } else {
            this.nextSequence = 0;
        }
    }

    /**
     * Reads next available value.
     * If reader is too slow and data was overwritten,
     * it skips forward to the oldest valid sequence.
     */
    public T read() {

        long writeSeq = buffer.getWriteSequence();
        long capacity = buffer.getCapacity();

        // Check if reader has fallen behind
        if (writeSeq - nextSequence > capacity) {
            System.out.println("Reader skipped overwritten data.");
            nextSequence = writeSeq - capacity;
        }

        if (nextSequence >= writeSeq) {
            return null; // nothing new to read
        }

        T value = buffer.readInternal(nextSequence);
        nextSequence++;

        return value;
    }
}