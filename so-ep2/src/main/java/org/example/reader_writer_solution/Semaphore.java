package org.example.reader_writer_solution;

public class Semaphore{
    private int flag;
    private int processCounter;

    public Semaphore() {
        this.flag = 0;
        this.processCounter = 0;
    }

    public void upProcessCounter() {
        this.processCounter++;
    }

    public void downProcessCounter() {
        this.processCounter--;
    }

    public void down(){
        if(this.flag > 0){
            this.flag--;
        }
    }

    public void up(){
        this.flag++;
    }

    public int getFlag() {
        return flag;
    }

    public int getProcessCounter() {
        return processCounter;
    }
}
