# Ring Buffer – Multiple Readers, Single Writer

## 1. Project Overview

This project implements a **fixed-capacity circular buffer (Ring Buffer)** in Java that supports:

- A **single writer**
- **Multiple independent readers**
- Automatic **overwrite** when the buffer becomes full
- Automatic **skip behavior** for slow readers

Each reader maintains its own reading position. Reading by one reader does not affect any other reader.

When the buffer capacity is exceeded, the oldest entries are overwritten. If a reader attempts to access overwritten data, it automatically advances to the oldest still-available item.

---

## 2. Design Architecture

The implementation follows clean object-oriented principles and separates responsibilities across four classes:

| Class         | Responsibility |
|--------------|----------------|
| `RingBuffer` | Core circular storage structure |
| `Writer`     | Single writer role |
| `Reader`     | Independent reader role |
| `Main`       | Driver and test logic |

This avoids a monolithic (“god class”) design.

---

## 3. Core Design Concepts

### Fixed Capacity

The buffer has a constant size **N**.  
Index calculation uses modulo arithmetic:

index = sequence % capacity

### Overwrite Condition

Overwrite occurs when:

writeSequence - readerSequence > capacity

When this happens, the reader updates:

readerSequence = writeSequence - capacity
This ensures:

The writer is never blocked

Readers operate independently

Slow readers skip missed data safely

---

## UML Class Diagram

```text
+----------------------------------------------------+
|                RingBuffer<T>                       |
+----------------------------------------------------+
| - buffer: Object[]                                 |
| - capacity: int                                    |
| - writeSequence: long                              |
+----------------------------------------------------+
| + RingBuffer(capacity: int)                        |
| + writeInternal(value: T): void                    |
| + readInternal(sequence: long): T                  |
| + getWriteSequence(): long                         |
| + getCapacity(): long                              |
+----------------------------------------------------+

                ▲
                |
     --------------------------------
     |                              |
+--------------------+     +--------------------+
|     Writer<T>      |     |     Reader<T>      |
+--------------------+     +--------------------+
| - buffer:          |     | - buffer:          |
|   RingBuffer<T>    |     |   RingBuffer<T>    |
|                    |     | - nextSequence:    |
|                    |     |   long             |
+--------------------+     +--------------------+
| + Writer(buffer:   |     | + Reader(buffer:   |
|   RingBuffer<T>)   |     |   RingBuffer<T>)   |
| + write(value: T): |     | + read(): T        |
|   void             |     |                    |
+--------------------+     +--------------------+
```

## UML Sequence Diagram – `write()`

```text
Main            Writer<T>          RingBuffer<T>
 |                  |                    |
 | write(value)     |                    |
 |----------------->|                    |
 |                  | writeInternal(value)
 |                  |------------------->|
 |                  |                    | store value
 |                  |                    | update writeSequence
 |                  |<-------------------|
 |                  | print "Writer wrote"
 |<-----------------|                    |
```
### UML Sequence Diagram – `read()`

```text
Main            Reader<T>          RingBuffer<T>
 |                  |                    |
 | read()           |                    |
 |----------------->|                    |
 |                  | getWriteSequence() |
 |                  |------------------->|
 |                  |<-------------------|
 |                  | getCapacity()      |
 |                  |------------------->|
 |                  |<-------------------|
 |                  | check overwrite    |
 |                  | (writeSeq - nextSeq > capacity)
 |                  |
 |                  | readInternal(nextSeq)
 |                  |------------------->|
 |                  |<-------------------| return value
 |                  | nextSequence++
 |<-----------------|                    |
```
## How to Compile and Run

### Prerequisites

Before running the project, ensure that:

- Java **11 or higher** is installed
- `javac` and `java` commands are available in your terminal

You can verify your Java installation by running:

java -version
javac -version

### Compile

Navigate to the source directory:

cd assignments/assignment2/src

Compile all Java files:

javac *.java

### Run

Execute the main class:

java Main