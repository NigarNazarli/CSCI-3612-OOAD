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

```text
index = sequence % capacity

Overwrite Condition

Overwrite occurs when:

writeSequence - readerSequence > capacity

When this happens, the reader updates:

readerSequence = writeSequence - capacity
This ensures:

The writer is never blocked

Readers operate independently

Slow readers skip missed data safely