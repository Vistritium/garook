package io.github.garook;

import java.io.Closeable;
import java.util.Iterator;

public interface CloseableIterator<T> extends Iterator<T>, AutoCloseable, Closeable {
}
