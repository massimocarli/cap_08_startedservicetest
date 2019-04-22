package uk.co.massimocarli.startedservicetest

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

  lateinit var serviceIntent: Intent

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    //serviceIntent = Intent(this, CounterService::class.java)
    //serviceIntent = Intent(this, MultiCounterService::class.java)
    serviceIntent = Intent(this, CounterIntentService::class.java)
  }

  fun startCounter(view: View) {
    startService(serviceIntent)
  }

  fun stopCounter(view: View) {
    stopService(serviceIntent)
  }
}
