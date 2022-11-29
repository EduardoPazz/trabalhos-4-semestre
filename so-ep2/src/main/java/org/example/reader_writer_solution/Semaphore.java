package org.example.reader_writer_solution;

public class Semaphore {

  private static int db = 1;
  private static int mutex = 1;
//  private static java.util.concurrent.Semaphore rc = new java.util.concurrent.Semaphore(100);
//
//  public synchronized static void upRC(java.util.concurrent.Semaphore db) {
//    rc.tryAcquire();
//    if (rc.availablePermits() == 1) {
//      db.tryAcquire();
//    }
//  }
//
//  public synchronized static void downRC(java.util.concurrent.Semaphore db) {
//    rc--;
//    if (rc == 0) {
//      db.release();
//    }
//  }

  public synchronized static boolean downDB() {
    if (db == 1) {
      db = 0;
      return true;
    }
    return false;
  }

  public synchronized static void upDB() {
    db = 1;
  }

  public synchronized static boolean downMutex() {
    if (mutex == 1) {
      mutex = 0;
      return true;
    }
    return false;
  }

  public synchronized static void upMutex() {
    mutex = 1;
  }
}
