package org.example.locking_system_solution;

import org.example.database_accessors.Reader;
import org.example.database_accessors.Writer;
import org.example.reader_writer_solution.Semaphore;
import java.util.List;

public class Reader_lock_solution {

  public synchronized void reader(int index, List<String> database){
    Semaphore mutex = new Semaphore();
    Reader reader = new Reader(index, database);

    while (true){
      if(mutex.down()){
        reader.run();
        mutex.up();
        break;
      }
    }
  }

  public synchronized void writer(int index, List<String> database){
    Semaphore mutex = new Semaphore();
    Writer writer = new Writer(index, database);

    while (true){
      if(mutex.down()){
        writer.run();
        mutex.up();
        break;
      }
    }
  }



}
