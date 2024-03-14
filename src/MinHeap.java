/***************************************************************************************
 *    Title: IntegerMinHeap.java
 *    Author: Colorado State
 *    Date: Unknown
 *    Availability: https://www.cs.colostate.edu/~cs200/Spring17/slides/examples/IntegerMinHeap.java
 *
 ***************************************************************************************/

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class MinHeap {
    private ArrayList<Interval> Heap;
    private int index;

    public MinHeap(){
        Heap = new ArrayList<Interval>();
        index = 0;
    }

    // zero based index
    private int leftChild(int i){
        return 2*i+1;
    }

    // zero based index
    private int rightChild(int i){
        return  2*(i+1);
    }

    // zero based index
    private int parent(int i){
        return (i-1)/2;
    }


    public boolean isEmpty(){
        return Heap.isEmpty();
    }


    public String toString(){
        return "the heap: " + Heap;
    }

    public void printHeap() {
        for (int i = 0; i < (index / 2); i++) {
            System.out.print("Parent : " + Heap.get(i));
            if (leftChild(i) < index)
                System.out.print(" Left : " + Heap.get(leftChild(i)));
            if (rightChild(i) < index)
                System.out.print(" Right :" + Heap.get(rightChild(i)));
            System.out.println();
        }
    }

    public void insert(Interval v){
        // add at end
        Heap.add(v);
        // bubble in place bottom up
        int i = Heap.size()-1;
        while(i>0 && v.getStart().compareTo(Heap.get(parent(i)).getStart()) < 0){
            Heap.set(i,Heap.get(parent(i)));
            Heap.set(parent(i), v);
            i = parent(i);
        }
        index++;
    }

    private void heapify(int i){
        int size = Heap.size();
        if (size>0){
            // left and right subtrees are already heaps
            // need to bubble element i in place top down
            int l = leftChild(i);
            int r = rightChild(i);
            int min;
            // min gets index of minimum of i and children
            if(l<size && Heap.get(i).getStart().compareTo(Heap.get(l).getStart()) > 0)
                min = l;
            else
                min = i;
            if(r<size && Heap.get(min).getStart().compareTo(Heap.get(r).getStart()) > 0)
                min = r;
            // need to bubble down?
            if(min!=i){
                // bubble down
                // swap and recur
                Interval t = Heap.get(i);
                Heap.set(i,Heap.get(min));
                Heap.set(min, t);
                heapify(min);
            }
        }
    }

    public void buildHeap(ArrayList<Interval> in){
        Heap = in;
        for(int i=(Heap.size()-2)/2; i>=0; i--)
            heapify(i);
    }

    public void minHeap() {
        for (int i = (index - 1 / 2); i >= 1; i--) {
            heapify(i);
        }
    }

    public ArrayList<Interval> sort(){
        ArrayList<Interval> res = new ArrayList<Interval>();
        while(!isEmpty()){
            res.add(extract());
        }
        return res;
    }
    public Interval extract(){
        if (Heap.isEmpty()) {
            index--;
            return null;
        }
        else {
            Interval res = Heap.get(0);
            Interval newRoot = Heap.get(Heap.size()-1);
            Heap.set(0, newRoot);
            Heap.remove(Heap.size()-1);
            heapify(0);
            index--;
            return res;
        }
    }

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
        } else if(i1.getStart().isBefore(i2.getStart()) && i1.getEnd().isAfter(i2.getEnd())) {
            return i1;
        } else if(i1.getStart().isAfter(i2.getStart()) && i1.getEnd().isBefore(i2.getEnd())) {
            return i2;
        } else {
            return Interval.genericInterval;
        }
    }

    public static void main(String[] arg) {
        // LocalDateTime.of(year, month, day, hour, minute, second)
        MinHeap minHeap = new MinHeap();
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

        System.out.println("The Min Heap is : " + minHeap.Heap);
        minHeap.printHeap();

        Interval genericInterval = new Interval(
                LocalDateTime.of(0, Month.JANUARY, 1, 0, 0),
                LocalDateTime.of(0, Month.JANUARY, 1, 0, 0));
        List<Interval> nonoverlapping = new ArrayList<>();

        Interval i1 = minHeap.extract();
        Interval i2 = minHeap.extract();
        while (minHeap.Heap.size() >= 1) {
            System.out.println(minHeap.Heap.size());
            System.out.println("The Min Heap is : " + minHeap.Heap);
            if (!minHeap.overlapping(i1, i2).equals(Interval.genericInterval)) { // they overlap
                System.out.println("i1: " + i1.toString() + " i2: " + i2.toString() + " overlap");
                i1 = minHeap.overlapping(i1, i2);
                System.out.println("i1: " + i1.toString());
                i2 = minHeap.extract();
                System.out.println("i2: " + i2.toString());
            } else { // don't overlap
                System.out.println("i1: " + i1.toString() + " i2: " + i2.toString() + " don't overlap");
                nonoverlapping.add(i1);
                System.out.println("nonoverlapping: " + nonoverlapping);
                i1 = i2;
                System.out.println("i1: " + i1.toString());
                i2 = minHeap.extract();
                System.out.println("i2: " + i2.toString());
            }
        }
        if (!minHeap.overlapping(i1, i2).equals(Interval.genericInterval)) { //they overlap
            System.out.println("i1: " + i1.toString() + " i2: " + i2.toString() + " overlap");
            i1 = minHeap.overlapping(i1, i2);
            System.out.println("i1: " + i1.toString());
            minHeap.insert(i1);
        } else { // don't overlap
            System.out.println(i1 + " " + i2 + " don't overlap");
            nonoverlapping.add(i1);
            nonoverlapping.add(i2);
            System.out.println("nonoverlapping: " + nonoverlapping);
        }
        for (Interval interval : nonoverlapping) {
            minHeap.insert(interval);
        }

        System.out.println("\nheap with no overlapping :" + minHeap);
        minHeap.printHeap();
    }
}