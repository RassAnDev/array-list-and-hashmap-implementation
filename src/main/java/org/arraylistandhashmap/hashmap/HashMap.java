package org.arraylistandhashmap.hashmap;

import java.util.Set;
import java.util.HashSet;
import java.util.Objects;

/**
 * This is a custom implementation of the HashMap in Java Collections Framework.
 * This implementation makes no guarantees as to
 * the order of the map; in particular, it does not guarantee that the order
 * will remain constant over time.
 *
 * <p>This implementation provides methods for the basic
 * operations, like get, put etc.
 *
 * <p>An instance of HashMap has two parameters that affect its
 * performance: <i>initial capacity</i> and <i>load factor</i>.  The
 * <i>capacity</i> is the number of buckets in the hash table, and the initial
 * capacity is simply the capacity at the time the hash table is created.  The
 * <i>load factor</i> is a measure of how full the hash table is allowed to
 * get before its capacity is automatically increased.  When the number of
 * entries in the hash table exceeds the product of the load factor and the
 * current capacity, the hash table is <i>rehashed</i> (that is, internal data
 * structures are rebuilt) so that the hash table has approximately twice the
 * number of buckets.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 *
 * @author  rassandev
 */
public class HashMap<K, V> {
    /**
     * The default initial capacity - MUST be a power of two.
     */
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    /**
     * The load factor used when none specified in constructor.
     */
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * The table, initialized on first use, and resized as
     * necessary.
     */
    private Entry<K, V>[] table;

    /**
     * The number of key-value mappings contained in this HashMap.
     */
    private int size;

    /**
     * The load factor for this HashMap.
     */
    private final float loadFactor;

    /**
     * Constructs an empty HashMap with the default initial capacity
     * (16) and the default load factor (0.75).
     */
    public HashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Constructs an empty HashMap with the specified initial
     * capacity and load factor.
     *
     * @param  initialCapacity the initial capacity
     * @param  loadFactor the load factor
     * @throws IllegalArgumentException if the initial capacity is negative
     *         or the load factor is nonpositive or not a number
     */
    public HashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }

        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        }

        this.table = new Entry[initialCapacity];
        this.loadFactor = loadFactor;
        this.size = 0;
    }

    /**
     * Constructs an empty HashMap with the specified initial
     * capacity and the default load factor (0.75).
     *
     * @param  initialCapacity the initial capacity.
     * @throws IllegalArgumentException if the initial capacity is negative.
     */
    public HashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Computes the hashcode for key of this HashMap.
     */
    private static int keyHash(Object key) {
        return key == null ? 0 : key.hashCode();
    }

    /**
     * Returns the number of key-value mappings in this HashMap.
     *
     * @return the number of key-value mappings in this HashMap
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if this HashMap contains no key-value mappings.
     *
     * @return true, if this HashMap contains no key-value mappings, otherwise returns false
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Removes all of the mappings from this HashMap.
     * The HashMap will be empty after this call returns.
     */
    public void clear() {
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        size = 0;
    }

    /**
     * Associates the specified value with the specified key in this HashMap.
     * If the HashMap previously contained a mapping for the key, the old
     * value is replaced.
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with a key, or
     *         null if there was no mapping for a key.
     */
    public V put(K key, V value) {
        int index = keyHash(key) % table.length;
        Entry<K, V> entry = table[index];

        while (entry != null) {
            if (hasCorrectKey(entry, key)) {
                V currentValue = entry.getValue();
                entry.setValue(value);
                return currentValue;
            }

            entry = entry.next;
        }

        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = table[index];
        table[index] = newEntry;

        size++;

        if ((float) size / table.length > DEFAULT_LOAD_FACTOR) {
            resize();
        }

        return null;
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or null if this HashMap contains no mapping for the key.
     *
     * @param key key with which the returning value is to be associated
     */
    public V get(Object key) {
        int index = keyHash(key) % table.length;
        Entry<K, V> entry = table[index];

        while (entry != null) {
            if (hasCorrectKey(entry, key)) {
                return entry.getValue();
            }

            entry = entry.next;
        }
        return null;
    }

    /**
     * Returns the value to which the specified key is mapped, or
     * defaultValue if this HashMap contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @param defaultValue the default mapping of the key
     * @return the value to which the specified key is mapped, or
     * defaultValue if this HashMap contains no mapping for the key
     */
    public V getOrDefault(Object key, V defaultValue) {
        V value = get(key);
        return ((value != null) || containsKey(key)) ? value : defaultValue;
    }

    /**
     * Removes the mapping for the specified key from this HashMap if present.
     *
     * @param  key key whose mapping is to be removed from the HashMap
     * @return the previous value associated with a key, or
     *         null if there was no mapping for key.
     */
    public V remove(Object key) {
        int index = keyHash(key) % table.length;
        Entry<K, V> entry = table[index];
        Entry<K, V> previous = null;

        while (entry != null) {
            if (hasCorrectKey(entry, key)) {
                V currentValue = entry.getValue();

                if (previous == null) {
                    table[index] = entry.next;
                } else {
                    previous.next = entry.next;
                }

                size--;

                return currentValue;
            }

            previous = entry;
            entry = entry.next;
        }

        return null;
    }

    /**
     * Returns true if this HashMap contains a mapping for the
     * specified key.
     *
     * @param   key   The key whose presence in this HashMap is to be tested
     * @return true if this HashMap contains a mapping for the specified
     * key.
     */
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    /**
     * Returns true if this HashMap maps one or more keys to the
     * specified value.
     *
     * @param value value whose presence in this HashMap is to be tested
     * @return true if this HashMap maps one or more keys to the
     *         specified value
     */
    public boolean containsValue(Object value) {
        for (Entry<K, V> entry : table) {
            while (entry != null) {
                if ((entry.getValue() == value) || (entry.getValue() != null && entry.getValue().equals(value))) {
                    return true;
                }

                entry = entry.next;
            }
        }

        return false;
    }

    /**
     * Initializes or doubles table size.
     *
     * @return the table
     */
    private Entry<K, V>[] resize() {
        int newSize = table.length * 2;
        Entry<K, V>[] newTable = new Entry[newSize];

        for (Entry<K, V> entry : table) {
            while (entry != null) {
                Entry<K, V> next = entry.next;
                int index = keyHash(entry.getKey()) % newSize;
                entry.next = newTable[index];
                newTable[index] = entry;
                entry = next;
            }
        }

        table = newTable;

        return newTable;
    }

    /**
     * Returns a {@link Set} view of the keys contained in this HashMap.
     *
     * @return a set view of the keys contained in this HashMap
     */
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();

        for (Entry<K, V> entry : table) {
            while (entry != null) {
                keys.add(entry.getKey());
                entry = entry.next;
            }
        }

        return keys;
    }

    private boolean hasCorrectKey(Entry<K, V> entry, Object key) {
        return (entry.getKey() == key) || (entry.getKey() != null && entry.getKey().equals(key));
    }

    /**
     * This class represents a basic entity(entry) of this HashMap.
     *
     * @param  <K> the type of the key.
     * @param  <V> the type of the value.
     */
    private static final class Entry<K, V> {
        private final K key;
        private V value;
        private Entry<K, V> next;

        private Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Entry<?, ?> entry = (Entry<?, ?>) o;

            return Objects.equals(this.getKey(), entry.getKey()) && Objects.equals(this.getValue(), entry.getValue());
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.getKey()) ^ Objects.hashCode(this.getValue());
        }

        public String toString() {
            return key + "=" + value;
        }
    }
}
