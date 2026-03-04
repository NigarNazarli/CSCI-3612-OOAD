public class Writer<T> {

    private final RingBuffer<T> buffer;

    public Writer(RingBuffer<T> buffer) {
        this.buffer = buffer;
    }
    
    public void write(T value) {
        buffer.writeInternal(value);
        System.out.println("Writer wrote: " + value);
    }
}