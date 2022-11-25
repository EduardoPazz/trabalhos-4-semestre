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

    public boolean down(){
        if(this.flag == 1){
            this.flag = 0;
            return true;
        }
        else return false;
    }

    public void up(){
        this.flag = 1;
    }

    public int getFlag() {
        return flag;
    }

    public int getProcessCounter() {
        return processCounter;
    }
}
