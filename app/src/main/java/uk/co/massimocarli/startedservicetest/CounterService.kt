package uk.co.massimocarli.startedservicetest

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.util.Log

class CounterService : Service() {

  companion object {
    const val TAG = "CounterService"
  }

  lateinit var handlerThread: HandlerThread
  lateinit var handler: Handler
  @Volatile
  var running: Boolean = false

  override fun onCreate() {
    super.onCreate()
    log("onCreate")
    handlerThread = HandlerThread("CounterThread").apply {
      start()
      handler = Handler(looper)
    }
    running = true
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    log("onStartCommand with startId: $startId")
    handler.post {
      for (i in (1 until 10)) {
        if (!running) {
          break
        }
        Thread.sleep(1000)
        if (!running) {
          break
        }
        log("Count $i")
      }
      log("stopSelf with startId: $startId")
      stopSelfResult(startId)
    }
    return super.onStartCommand(intent, flags, startId)
  }

  override fun onDestroy() {
    running = false
    handlerThread.quit()
    super.onDestroy()
    log("onDestroy")
  }

  override fun onBind(intent: Intent?): IBinder? = null


  private fun log(msg: String) {
    Log.d(TAG, "\t->   $msg")
  }
}