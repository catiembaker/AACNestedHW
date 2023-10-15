package structures;

import java.lang.reflect.Array;

/**
 * A basic implementation of Associative Arrays with keys of type K and values of type V.
 * Associative Arrays store key/value pairs and permit you to look up values by key.
 *
 * @author Rene Urias Jr.
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default capacity of the initial array.
   */
  static final int DEFAULT_CAPACITY = 16;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The size of the associative array (the number of key/value pairs).
   */
  int size;

  /**
   * The array of key/value pairs.
   */
  KVPair<K, V> pairs[];

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new, empty associative array.
   */
  @SuppressWarnings("unchecked")
  public AssociativeArray() {
    this.pairs =
        (KVPair<K, V>[]) Array.newInstance(new KVPair<K, V>().getClass(), DEFAULT_CAPACITY);
    this.size = 0;
  } // AssociativeArray()

  // +------------------+--------------------------------------------
  // | Standard Methods |
  // +------------------+

  /**
   * Create a copy of this AssociativeArray.
   */
  public AssociativeArray<K, V> clone() {
    AssociativeArray<K, V> clonedArray = new AssociativeArray<>();
    clonedArray.size = this.size;
    clonedArray.pairs = java.util.Arrays.copyOf(this.pairs, this.size);
    return clonedArray;
  } // clone()

  /**
   * Convert the array to a string.
   */
  public String toString() {
    StringBuilder result = new StringBuilder("{ ");
    for (int i = 0; i < size; i++) {
      result.append(pairs[i].key).append(": ").append(pairs[i].value);
      if (i < size - 1) {
        result.append(", ");
      }
    }
    result.append(" }");
    return result.toString();
  } // toString()

  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+

  /**
   * Set the value associated with key to value. Future calls to get(key) will return value.
   */
  public void set(K key, V value) {
    int index;
    try {
      index = find(key);
      // Key found, replace the value
      pairs[index].value = value;
    } catch (KeyNotFoundException e) {
      // Key not found, add a new key-value pair
      if (size == pairs.length) {
        expand();
      }
      pairs[size++] = new KVPair<>(key, value);
    }
  } // set(K,V)

  /**
   * Get the value associated with key.
   *
   * @throws KeyNotFoundException when the key does not appear in the associative array.
   */
  public V get(K key) throws KeyNotFoundException {
    int index = find(key);
    return pairs[index].value;
  } // get(K)

  /**
   * Determine if key appears in the associative array.
   */
  public boolean hasKey(K key) {
    try {
      find(key);
      return true;
    } catch (KeyNotFoundException e) {
      return false;
    }
  } // hasKey(K)

  /**
   * Remove the key/value pair associated with a key. Future calls to get(key) will throw an
   * exception. If the key does not appear in the associative array, does nothing.
   */
  public void remove(K key) {
    try {
      int index = find(key);
      // Shift elements to the left to remove the key-value pair
      System.arraycopy(pairs, index + 1, pairs, index, size - index - 1);
      size--;
    } catch (KeyNotFoundException e) {
      // Key not found, do nothing
    }
  } // remove(K)

  /**
   * Determine how many values are in the associative array.
   */
  public int size() {
    return this.size;
  } // size()

  /**
   * Get all keys in the associative array
   * 
   * @return an array of all keys
   */
  @SuppressWarnings("unchecked")
  public K[] getAllKeys() {
    Object keysObjectArray =
        Array.newInstance(pairs.getClass().getComponentType().getComponentType(), size);

    for (int i = 0; i < size; i++) {
      Array.set(keysObjectArray, i, pairs[i].key);
    }

    return (K[]) keysObjectArray;
  } // getAllKeys()

  // +-----------------+---------------------------------------------
  // | Private Methods |
  // +-----------------+

  /**
   * Expand the underlying array.
   */
  private void expand() {
    pairs = java.util.Arrays.copyOf(pairs, this.pairs.length * 2);
  } // expand()

  /**
   * Find the index of the first entry in `pairs` that contains key. If no such entry is found,
   * throws an exception.
   */
  private int find(K key) throws KeyNotFoundException {
    for (int i = 0; i < size; i++) {
      if (pairs[i].key.equals(key)) {
        return i;
      }
    }
    throw new KeyNotFoundException();
  } // find(K)
} // class AssociativeArray
