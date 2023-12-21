package org.arraylistandhashmap.arraylist;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * This is a custom implementation of the LinkedList in Java Collections Framework.
 * Its resizable-array implementation.
 *
 * <p>This implementation provides methods for the basic
 * operations, like add, get, set, remove etc.
 *
 * <p>Each ArrayList instance has a <i>capacity</i>.  The capacity is
 * the size of the array used to store the elements in the list.  It is always
 * at least as large as the list size.  As elements are added to an ArrayList,
 * its capacity grows automatically.
 *
 * <p>Also, this implementation of ArrayList has an elements iterator
 * that allows to iterate through the ArrayList
 * and quickSort method that allows to sort the elements in this ArrayList
 * using a quicksort algorithm that runs in O(n log n) time.
 *
 * @param <T> the type of elements in this list
 *
 * @author  rassandev
 */
public class ArrayList<T> {

    private static final int DEFAULT_CAPACITY = 10;
    private static final int MULTIPLIER = 2;

    private Object[] array;

    private int size;

    /**
     * Constructs an empty ArrayList with an initial capacity of ten.
     */
    public ArrayList() {
        array = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Constructs an empty ArrayList with the specified initial capacity.
     *
     * @param  initialCapacity  the initial capacity of the ArrayList
     * @throws IllegalArgumentException if the specified initial capacity
     *         is negative
     */
    public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            array = new Object[initialCapacity];
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "
                    + initialCapacity);
        }
    }

    /**
     * Returns the number of elements in this ArrayList.
     *
     * @return the number of elements in this ArrayList
     */
    public final int size() {
        return this.size;
    }

    /**
     * Returns true if this ArrayList contains no elements.
     *
     * @return true if this ArrayList contains no elements
     */
    public final boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Returns true if this ArrayList contains the specified element.
     *
     * @param o element whose presence in this ArrayList is to be tested
     * @return true if this ArrayList contains the specified element
     */
    public final boolean contains(final Object o) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns an iterator over the elements in this ArrayList in a proper sequence.
     *
     * @return an iterator over the elements in this ArrayList in a proper sequence
     */
    public final Iterator<T> iterator() {
        return new ElementsIterator();
    }

    /**
     * Returns an array containing all the elements in this ArrayList
     * in a proper sequence (from first to a last element).
     *
     * @return an array containing all the elements in this ArrayList in
     *        a proper sequence
     */
    public final Object[] toArray() {
        final Object[] newM = new Object[this.size()];
        System.arraycopy(array, 0, newM, 0, this.size());
        return newM;
    }


    /**
     * Appends the specified element to the end of this ArrayList.
     * If the array is full, then it is doubled.
     *
     * @param element element to be appended to this list
     * @return true after successful addition
     */
    public final boolean add(final T element) {
        if (array.length == size) {
            final Object[] oldArray = array;
            array = new Object[this.size() * MULTIPLIER];
            System.arraycopy(oldArray, 0, array, 0, oldArray.length);
        }
        array[size++] = element;
        return true;
    }

    /**
     * Inserts the specified element at the specified position in this
     * ArrayList. Shifts the element currently at that position (if any) and
     * any subsequent elements to the right (adds one to their indices).
     *
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public final void add(final int index, final T element) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (size  == 0 || index == size) {
            add(element);
        } else if (array.length == size) {
            final Object[] tempArray = array;
            array = new Object[this.size() * MULTIPLIER];

            System.arraycopy(tempArray, 0, array, 0,  index);
            System.arraycopy(tempArray, index, array, index + 1, size() - index);

            set(index, element);
            size++;
        } else {
            final Object[] tempArray = array;
            System.arraycopy(tempArray, 0, array, 0, index + 1);
            System.arraycopy(tempArray, index, array, index + 1, size() - index);
            set(index, element);
            size++;
        }
    }

    /**
     * Appends all the elements in the specified collection to the end of
     * this ArrayList, in the order that they are returned by the
     * specified collection's Iterator.  The behavior of this operation is
     * undefined if the specified collection is modified while the operation
     * is in progress.
     *
     * @param arrayList arrayList containing elements to be added to a current ArrayList
     * @return true if this list changed as a result of the call
     */
    public final boolean addAll(ArrayList<T> arrayList) {
        for (Iterator<T> it = arrayList.iterator(); it.hasNext();) {
            T item = it.next();
            add(item);
        }
        return true;
    }

    /**
     * Removes the first occurrence of the specified element from this ArrayList
     * if it is present.  If the list does not contain the element, it is
     * unchanged.
     *
     * @param element element to be removed from this list, if ArrayList
     * @return true if this list contained the specified element
     */
    public final boolean remove(final Object element) {
        for (int i = 0; i < size(); i++) {
            if (array[i].equals(element)) {
                this.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the element at the specified position in this ArrayList.
     * Shifts any subsequent elements to the left (subtracts one from their
     * indices).
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the ArrayList
     */
    public final T remove(final int index) {
        final T element = (T) array[index];
        if (index != this.size() - 1) {
            System.arraycopy(array, index + 1, array, index, this.size() - index - 1);
        }
        size--;
        return element;
    }

    /**
     * Removes from this ArrayList all of its elements that are contained in the
     * specified collection.
     *
     * @param arrayList arrayList containing elements to be removed from the current ArrayList
     * @return true if this ArrayList changed as a result of the call
     */
    public final boolean removeAll(ArrayList<T> arrayList) {
        for (Iterator<T> it = arrayList.iterator(); it.hasNext();) {
            T item = it.next();
            remove(item);
        }
        return true;
    }

    /**
     * Retains only the elements in this ArrayList that are contained in
     * another ArrayList.
     *
     * @param arrayList collection containing elements to be retained in the current ArrayList
     * @return true if this list changed as a result of the call
     */
    public final boolean retainAll(ArrayList<T> arrayList) {
        for (Iterator<T> it = this.iterator(); it.hasNext();) {
            T item = it.next();
            if (!arrayList.contains(item)) {
                this.remove(item);
            }
        }
        return true;
    }

    /**
     * Removes all the elements from this ArrayList.  The list will
     * be empty after this call returns.
     */
    public final void clear() {
        array = (T[]) new Object[1];
        size = 0;
    }

    /**
     * Replaces the element at the specified position in this ArrayList with
     * the specified element.
     *
     * @param index index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     */
    public final T set(final int index, final T element) {
        array[index] = element;
        return element;
    }

    /**
     * Returns the element at the specified position in this ArrayList.
     *
     * @param  index index of the element to return
     * @return the element at the specified position in this ArrayList
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public final T get(final int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return (T) array[index];
    }

    public final void quickSort() {
        quickSort(array, 0, size - 1);
    }

    /**
     * Sorts the elements in this ArrayList using a quicksort algorithm that runs in O(n log n) time.
     *
     * @param arr the array to be sorted by the quick sort method
     * @param low the first index of the array to be sorted
     * @param high the last index of the array to be sorted
     */
    private static <T extends Comparable<T>> void quickSort(Object[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = divide(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    /**
     * Divides the elements in this ArrayList.
     * If a current element is smaller than the pivot, increment the border and do a swap.
     * In the end, put the pivot element to the place of border and return the index pivot element.
     *
     * @param arr is the array to be separated by pivot element
     * @param low is the first index of the array to be separated
     * @param high is the last index of the array to be separated
     */
    private static <T extends Comparable<T>> int divide(Object[] arr, int low, int high) {
        T pivot = (T) arr[high];
        int border = low - 1;
        for (int j = low; j < high; j++) {
            if (((T) arr[j]).compareTo(pivot) <= 0) {
                border++;
                swap(arr, border, j);
            }
        }
        swap(arr, border + 1, high);
        return border + 1;
    }

    private static <T> void swap(Object[] arr, int i, int j) {
        Object temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

//    public <T extends Comparable<T>> void anotherQuickSort(Object[] sortArr, int low, int high) {
//        //terminate if the array is empty or there is nothing left to divide
//        if (sortArr.length == 0 || low >= high) return;
//
//        //select the pivot element
//        int middle = low + (high - low) / 2;
//        T border = (T) sortArr[middle];
//
//        //divide into subarrays and swap places
//        int i = low;
//        int j = high;
//
//        while (i <= j) {
//            while (((T) sortArr[i]).compareTo(border) < 0) {
//                i++;
//            }
//            while (((T) sortArr[j]).compareTo(border) > 0) {
//                j--;
//            }
//            if (i <= j) {
//                T swap = (T) sortArr[i];
//                sortArr[i] = sortArr[j];
//                sortArr[j] = swap;
//                i++;
//                j--;
//            }
//        }
//
//        //recursion to sort left and right side
//        if (low < j) {
//            anotherQuickSort(sortArr, low, j);
//        }
//        if (high > i) {
//            anotherQuickSort(sortArr, i, high);
//        }
//    }

    private class ElementsIterator implements ListIterator<T> {

        private static final int LAST_IS_NOT_SET = -1;
        private int index;
        private int lastIndex = LAST_IS_NOT_SET;

        ElementsIterator() {
            this(0);
        }

        ElementsIterator(final int index) {
            this.index = index;
        }

        @Override
        public boolean hasNext() {
            return ArrayList.this.size() > index;
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastIndex = index++; // or lastIndex = nextIndex(); index++;
            return ArrayList.this.get(lastIndex);
        }

        public int nextIndex() {
            return index;
        }

        public boolean hasPrevious() {
            return index > 0;
        }

        public T previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            lastIndex = (--index); // or lastIndex = previousIndex(); index--;
            return ArrayList.this.get(lastIndex);
        }

        public int previousIndex() {
            return index == 0 ? LAST_IS_NOT_SET : index - 1;
        }

        public void add(final T element) {
            ArrayList.this.add(index, element);
            index++;
            lastIndex = LAST_IS_NOT_SET;
        }

        public void set(final T element) {
            if (lastIndex == LAST_IS_NOT_SET) {
                throw new IllegalStateException();
            }
            ArrayList.this.set(lastIndex,  element);
        }

        public void remove() {
            if (lastIndex == LAST_IS_NOT_SET) {
                throw new IllegalStateException();
            }
            ArrayList.this.remove(lastIndex);
            index--;
            lastIndex = LAST_IS_NOT_SET;
        }
    }
}
