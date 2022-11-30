package org.example.helpers;

import java.util.concurrent.Semaphore;
import org.example.database_accessors.Callback;

public class SemaphoreHelper {

  private static final Semaphore db = new Semaphore(1);
  private static final Semaphore mutex = new Semaphore(1);
  private static int rc = 0;

  private static void upRC() throws InterruptedException {
    mutex.acquire();
    rc++;
    if (rc == 1) {
      downDB();
    }
    mutex.release();
  }

  private static void downRC() throws InterruptedException {
    mutex.acquire();
    rc--;
    if (rc == 0) {
      upDB();
    }
    mutex.release();
  }

  private static void downDB() throws InterruptedException {
    db.acquire();
  }

  private static void upDB() {
    db.release();
  }

  public static void useDB(Callback callback) throws InterruptedException {
    downDB();
    callback.run();
    upDB();
  }

  public static void useRC(Callback callback) throws InterruptedException {
    upRC();
    callback.run();
    downRC();
  }

  public enum Mode {
    BLOCKING, READER_WRITER
  }
}
