Ring Buffer – Single Writer, Multiple Independent Readers
Project Overview

This project implements a fixed-capacity Ring Buffer (circular buffer) in Java that supports:

One single writer

Multiple independent readers

Overwrite behavior when the buffer becomes full

Automatic skip behavior for slow readers

Each reader maintains its own reading position. Reading by one reader does not affect any other reader.

When the buffer capacity is exceeded, the writer overwrites the oldest data. If a reader falls too far behind and attempts to read overwritten data, it automatically skips forward to the oldest still-available element.

The design emphasizes clear object-oriented structure and separation of responsibilities.
Design Overview

The solution is implemented using four classes:

RingBuffer

Writer

Reader

Main (driver class)

Each class has a well-defined responsibility.
RingBuffer

The RingBuffer class represents the core storage mechanism.

Responsibilities

Maintains a fixed-size array

Tracks a global write sequence number

Computes indices using modulo arithmetic

Allows overwrite when capacity is exceeded

Provides synchronized internal read and write operations

The buffer does not know about business roles such as writer or reader.
It only manages storage mechanics.
Writer

The Writer class represents the single-writer role.

Responsibilities

Exposes a public write(value) method

Delegates storage to RingBuffer

Enforces single-writer usage at the architectural level

Only one writer instance is used per buffer.

Reader

Each Reader instance represents an independent consumer.

Responsibilities

Maintains its own sequence position (nextSequence)

Reads available items from the buffer

Detects when it has fallen behind

Skips to the oldest available item if overwrite occurred

Readers operate independently and do not interfere with each other.

Overwrite and Skip Logic

The buffer has a fixed capacity N.

When the writer inserts more than N elements:

writeSequence - nextSequence > capacity

This indicates that a reader has fallen behind and the requested data has already been overwritten.

In this case, the reader automatically updates:

nextSequence = writeSequence - capacity

This ensures:

No blocking occurs

The writer continues uninterrupted

Slow readers resume from the oldest valid item

This behavior is demonstrated in the program output.
Demonstrated Behavior

When running the program:

The writer inserts more elements than the buffer capacity.

The buffer overwrites older values.

A slow reader attempts to read outdated data.

The reader detects the overwrite and prints:

Reader skipped overwritten data.

The reader continues reading from the oldest still-available element.

Once caught up, additional reads return null.

This confirms correct overwrite and independent reader behavior.