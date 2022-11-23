package org.example.reader_writer_solution;
import java.util.List;

public class ReaderWriter {
    int activeReaders = 0;
    boolean isOccupied = false;

    boolean writtingCondition() {
        return activeReaders == 0 && !isOccupied;
    }

    boolean readingCondition() {
        return !isOccupied;
    }

    public void read(List<String> database, int index) {
        while (!readingCondition()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        activeReaders++;
        System.out.println("Reader " + index + " started");
        @SuppressWarnings("unused")
        String aLocalVariable = database.get(index);
        System.out.println("Reader " + index + " finished");
        activeReaders--;
    }

    public void stopReading() {
        activeReaders--;
    }

    public void write(List<String> database, int index) {
        while (!writtingCondition()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        isOccupied = true;
        System.out.println("Writer " + index + " started");
        database.set(index, "new value");
        System.out.println("Writer " + index + " finished");
        isOccupied = false;
    }

    public void stopWriting() {
        isOccupied = false;
    }
    
}
