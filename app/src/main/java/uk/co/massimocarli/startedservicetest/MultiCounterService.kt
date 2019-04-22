package uk.co.massimocarli.startedservicetest

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MultiCounterService : Service() {

  companion object {
    const val TAG = "CounterService"
  }

  lateinit var executorService: ExecutorService
  lateinit var taskMap: MutableMap<Int, Runnable>

  override fun onCreate() {
    super.onCreate()
    taskMap = ConcurrentHashMap()
    executorService = Executors.newFixedThreadPool(5)
    log("onCreate")
  }


  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    log("onStartCommand with startId: $startId")
    val runnable = Runnable {
      for (i in (1 until 10)) {
        if (Thread.interrupted()) {
          manageStopped(startId)
          break;
        }
        try {
          Thread.sleep(1000)
        } catch (ex: InterruptedException) {
          manageStopped(startId)
          break;
        }
        if (Thread.interrupted()) {
          manageStopped(startId)
          break;
        }
        log("Count $i for $startId")
      }
      manageCompleted(startId)
    }
    taskMap.put(startId, runnable)
    executorService.submit(runnable)
    return super.onStartCommand(intent, flags, startId)
  }

  private fun manageStopped(id: Int) {
    taskMap.remove(id)
    log("Task with startId: ${id} stopped")
  }

  private fun manageCompleted(id: Int) {
    taskMap.remove(id)
    if (taskMap.isEmpty()) {
      stopSelf()
    }
    log("Task with startId: ${id} completed")
  }

  override fun onDestroy() {
    executorService.shutdownNow()
    super.onDestroy()
    log("onDestroy")
  }

  override fun onBind(intent: Intent?): IBinder? = null

  private fun log(msg: String) {
    Log.d(TAG, "\t->   $msg")
  }
}