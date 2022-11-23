package org.example.reader_writer_solution;
import org.example.reader_writer_solution.Semaphore;
import org.example.database_accessors.*;
import java.util.List;

public class Reader_writer {
    
    public void reader(int index, List<String> database) {
        Semaphore mutex = new Semaphore();
        Semaphore db = new Semaphore();
        Semaphore rc = new Semaphore();
        Reader reader = new Reader(index, database);

        while(true){
            mutex.down();
            rc.upProcessCounter();
            if(rc.getProcessCounter() == 1){
                db.down();
            }

            mutex.up();
            //Acessa os dados
            reader.run();

            mutex.down();
            rc.downProcessCounter();
            if(rc.getProcessCounter() == 0){
                db.up();
            }

            mutex.up();

            //Usa os dados -> região não crític

        }
    }

    public void writer(int index, List<String> database) {
        Semaphore mutex = new Semaphore();
        Semaphore db = new Semaphore();
        Writer writer = new Writer(index, database);

        while(true){
            //Cria o dado - região não critica
            db.down();
            //Escreve o dado
            writer.run();
            db.up();
        }
    }
}
