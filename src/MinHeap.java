/***************************************************************************************
        *    Title: Min Heap in Java
        *    Author: Oleksandr Miadelets
        *    Date: 9 September 2020
        *    Availability: https://codegym.cc/groups/posts/min-heap-in-java
        *
 ***************************************************************************************/

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// change primitive array to arraylist
public class MinHeap {
    private Interval[] Heap;
    private int index;
    private int length;
    private int size;

    public MinHeap(int size) {
        this.index = 0;
        this.size = size;
        Heap = new Interval[size];
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int leftChild(int i) {
        return (i * 2) + 1;
    }

    private int rightChild(int i) {
        return (i * 2) + 2;
    }

    private boolean isLeaf(int i) {
        if (rightChild(i) >= size || leftChild(i) >= size) {
            return true;
        }
        return false;
    }

    public void insert(Interval element) {
        if (index >= size) {
            return;
        }
        Heap[index] = element;
        int current = index;

        while (Heap[current].getDifference().compareTo(Heap[parent(current)].getDifference()) < 0) {
            swap(current, parent(current));
            current = parent(current);
        }
        index++;
        length++;
    }

    // removes and returns the minimum element from the heap
    public Interval remove() {
        // since its a min heap, so root = minimum
        Interval popped = Heap[0];
        Heap[0] = Heap[--index];
        minHeapifyStart(0);
        length--;
        return popped;
    }

    // heapify the node at i
    // min heap based on the interval size
    private void minHeapify(int i) {
        // If the node is a non-leaf node and any of its child is smaller
        if (!isLeaf(i)) {
            if (Heap[i].getDifference().compareTo(Heap[leftChild(i)].getDifference()) > 0 ||
                    Heap[i].getDifference().compareTo(Heap[rightChild(i)].getDifference()) > 0) {
                if (Heap[leftChild(i)].getDifference().compareTo(Heap[rightChild(i)].getDifference()) < 0) {
                    swap(i, leftChild(i));
                    minHeapify(leftChild(i));
                } else {
                    swap(i, rightChild(i));
                    minHeapify(rightChild(i));
                }
            }
        }
    }

    //min heap based on start date
    private void minHeapifyStart(int i) {
        if (!isLeaf(i)) {
            if (Heap[i].getStart().isAfter(Heap[leftChild(i)].getStart()) || Heap[i].getStart().isAfter(Heap[rightChild(i)].getStart())) {
                if (Heap[leftChild(i)].getStart().isBefore(Heap[rightChild(i)].getStart())) {
                    swap(i, leftChild(i));
                    minHeapifyStart(leftChild(i));
                } else {
                    swap(i, rightChild(i));
                    minHeapifyStart(rightChild(i));
                }
            }
        }
    }

    //min heap based on end date
    private void minHeapifyEnd(int i) {
        if (!isLeaf(i)) {
            if (Heap[i].getEnd().isAfter(Heap[leftChild(i)].getEnd()) || Heap[i].getEnd().isAfter(Heap[rightChild(i)].getEnd())) {
                if (Heap[leftChild(i)].getEnd().isBefore(Heap[rightChild(i)].getEnd())) {
                    swap(i, leftChild(i));
                    minHeapifyEnd(leftChild(i));
                } else {
                    swap(i, rightChild(i));
                    minHeapifyEnd(rightChild(i));
                }
            }
        }
    }

    // builds the min-heap using the minHeapify
    public void minHeap() {
        for (int i = (index - 1 / 2); i >= 1; i--) {
            minHeapifyStart(i);
        }
    }

    // Function to print the contents of the heap
    public void printHeap() {
        for (int i = 0; i < (index / 2); i++) {
            System.out.print("Parent : " + Heap[i]);
            if (leftChild(i) < index)
                System.out.print(" Left : " + Heap[leftChild(i)]);
            if (rightChild(i) < index)
                System.out.print(" Right :" + Heap[rightChild(i)]);
            System.out.println();
        }
    }

    // swaps two nodes of the heap
    private void swap(int x, int y) {
        Interval tmp;
        tmp = Heap[x];
        Heap[x] = Heap[y];
        Heap[y] = tmp;
    }

    // method to see if an interval overlaps with another interval
    public Interval overlapping(Interval i1, Interval i2) {
        // first, see if the start date of I1 is within I2
            // is start of I1 after the start date of I2 AND is start of I1 before end of I2
        if (i1.getStart().isAfter(i2.getStart()) && i1.getStart().isBefore(i2.getEnd()) || i1.getStart().equals(i2.getStart())) {
            // var newStart = I2.getStart()
            LocalDateTime newStart = i2.getStart();
            LocalDateTime newEnd;
            // then determine the end date
                // if end of I1 == I2 then end date is I1.getEnd
            if (i1.getEnd().equals(i2.getEnd())) {
                newEnd = i1.getEnd();
            } else if (i1.getEnd().isBefore(i2.getEnd())) { // if end of I1 is before I2 then end date is I2.getEnd
                newEnd = i2.getEnd();
            } else { // else end date is I1.getEnd
                newEnd = i1.getEnd();
            }
            return new Interval(newStart, newEnd);
            // finally, remove I1 and I2 from the heap and add the new interval
            // next, see if the end date of I1 is within I2
            // is end of I1 before the end of I2 AND if the end of I1 after the end of I2
        } else if (i1.getEnd().isBefore(i2.getEnd()) && (i1.getEnd().isAfter(i2.getStart()) || i1.getEnd().equals(i2.getStart())) || i1.getEnd().equals(i2.getEnd())) {
            // var newEnd = I2.getEnd
            LocalDateTime newEnd = i2.getEnd();
            LocalDateTime newStart;
            // then determine start date
            // if start I1 == start I2 then start date is I1.getStart
            if (i1.getStart().equals(i2.getStart())) {
                newStart = i1.getStart();
            } else if (i1.getStart().isBefore(i2.getStart())) { // if start I1 is before start I2 then start date is I1.start
                newStart = i1.getStart();
            } else { // else start date is I2.getStart
                newStart = i2.getStart();
            }
            return new Interval(newStart, newEnd);
            // finally, remove I1 and I2 from the heap and add the new interval
        } else {
            return Interval.genericInterval;
        }
    }

    public static void main(String[] arg) {
        // LocalDateTime.of(year, month, day, hour, minute, second)
        MinHeap minHeap = new MinHeap(7);
        minHeap.insert(new Interval(
                LocalDateTime.of(2024, Month.MARCH, 1, 10, 3),
                LocalDateTime.of(2024, Month.MARCH, 1, 10, 7)));
        minHeap.insert(new Interval(
                LocalDateTime.of(2024, Month.MARCH, 1, 10, 5),
                LocalDateTime.of(2024, Month.MARCH, 1, 10, 9)));
        minHeap.insert(new Interval(
                LocalDateTime.of(2024, Month.MARCH, 1, 10, 9),
                LocalDateTime.of(2024, Month.MARCH, 1, 10, 11)));
        minHeap.insert(new Interval(
                LocalDateTime.of(2024, Month.MARCH, 1, 10, 1),
                LocalDateTime.of(2024, Month.MARCH, 1, 10, 2)));
        minHeap.insert(new Interval(
                LocalDateTime.of(2024, Month.MARCH, 1, 10, 13),
                LocalDateTime.of(2024, Month.MARCH, 1, 10, 16)));
        minHeap.insert(new Interval(
                LocalDateTime.of(2024, Month.MARCH, 1, 10, 17),
                LocalDateTime.of(2024, Month.MARCH, 1, 10, 22)));
        minHeap.insert(new Interval(
                LocalDateTime.of(2024, Month.MARCH, 1, 10, 20),
                LocalDateTime.of(2024, Month.MARCH, 1, 10, 21)));
        minHeap.minHeap();

        System.out.println("The Min Heap is : " + Arrays.toString(minHeap.Heap));
        minHeap.printHeap();

        Interval genericInterval = new Interval(
                LocalDateTime.of(0, Month.JANUARY, 1, 0, 0),
                LocalDateTime.of(0, Month.JANUARY, 1, 0, 0));
        List<Interval> nonoverlapping = new ArrayList<>();

        Interval i1 = minHeap.remove();
        Interval i2 = minHeap.remove();
        while (minHeap.length >= 1) {
            System.out.println(minHeap.length);
            System.out.println("The Min Heap is : " + Arrays.toString(minHeap.Heap));
            if (!minHeap.overlapping(i1, i2).equals(Interval.genericInterval)) { // they overlap
                System.out.println("i1: " + i1.toString() + " i2: " + i2.toString() + " overlap");
                i1 = minHeap.overlapping(i1, i2);
                System.out.println("i1: " + i1.toString());
                i2 = minHeap.remove();
                System.out.println("i2: " + i2.toString());
            } else { // don't overlap
                System.out.println("i1: " + i1.toString() + " i2: " + i2.toString() + " don't overlap");
                nonoverlapping.add(i1);
                System.out.println("nonoverlapping: " + nonoverlapping);
                i1 = i2;
                System.out.println("i1: " + i1.toString());
                i2 = minHeap.remove();
                System.out.println("i2: " + i2.toString());
            }
        }
        for (Interval interval : nonoverlapping) {
            minHeap.insert(interval);
        }

        System.out.println("\nheap with no overlapping :" + Arrays.toString(minHeap.Heap));
        minHeap.printHeap();
    }
}