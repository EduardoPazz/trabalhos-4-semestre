//package org.example.reader_writer_solution;
//import org.example.database_accessors.*;
//import java.util.List;
//
//public class Reader_writer {
//
//    public void reader(int index, List<String> database) {
//        Semaphore mutex = new Semaphore();
//        Semaphore db = new Semaphore();
//        Semaphore rc = new Semaphore();
//        Reader reader = new Reader(index, database);
//
//        while(true){
//            mutex.downDB();
//            rc.upRC();
//            if(rc.getProcessCounter() == 1){
//                db.downDB();
//            }
//
//            mutex.upDB();
//            //Acessa os dados
//            reader.run();
//
//            mutex.downDB();
//            rc.downProcessCounter();
//            if(rc.getProcessCounter() == 0){
//                db.upDB();
//            }
//
//            mutex.upDB();
//
//            //Usa os dados -> região não crític
//
//        }
//    }
//
//    public void writer(int index, List<String> database) {
//        Semaphore mutex = new Semaphore();
//        Semaphore db = new Semaphore();
//        Writer writer = new Writer(index, database);
//
//        while(true){
//            //Cria o dado - região não critica
//            db.downDB();
//            //Escreve o dado
//            writer.run();
//            db.upDB();
//        }
//    }
//}
