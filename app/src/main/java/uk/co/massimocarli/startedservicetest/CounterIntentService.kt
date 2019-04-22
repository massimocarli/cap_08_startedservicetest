package uk.co.massimocarli.startedservicetest

import android.app.IntentService
import android.content.Intent
import android.util.Log

class CounterIntentService : IntentService("CounterIntentService") {

  @Volatile
  var running: Boolean = false

  override fun onCreate() {
    super.onCreate()
    log("onCreate")
    running = true
  }

  override fun onHandleIntent(intent: Intent?) {
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
  }

  override fun onDestroy() {
    running = false
    super.onDestroy()
    log("onDestroy")
  }

  private fun log(msg: String) {
    Log.d(CounterService.TAG, "\t->   $msg")
  }
}