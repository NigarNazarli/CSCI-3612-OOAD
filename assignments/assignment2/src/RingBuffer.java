public class RingBuffer<T> {

    private final Object[] buffer;
    private final int capacity;
    private long writeSequence = 0;

    public RingBuffer(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }
        this.capacity = capacity;
        this.buffer = new Object[capacity];
    }

   
    public synchronized void writeInternal(T value) {
        int index = (int) (writeSequence % capacity);
        buffer[index] = value;
        writeSequence++;
    }

   
    public synchronized T readInternal(long sequence) {
        int index = (int) (sequence % capacity);
        return (T) buffer[index];
    }

    public synchronized long getWriteSequence() {
        return writeSequence;
    }

    public int getCapacity() {
        return capacity;
    }
}