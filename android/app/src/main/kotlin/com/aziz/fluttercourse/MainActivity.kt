package com.aziz.fluttercourse

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle

import io.flutter.app.FlutterActivity
import io.flutter.plugins.GeneratedPluginRegistrant
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

class MainActivity: FlutterActivity() {
  private final var CHANNEL = "flutter-course/battery"

  fun getBatteryLevel(): Int {
    var batteryLevel = -1;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
      val batteryManager = getSystemService(Context.BATTERY_SERVICE) as BatteryManager
      batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
    } else {
      val intent = ContextWrapper(applicationContext).registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
      batteryLevel = (intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -2) * 100) /
              intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
    }

    return batteryLevel
  }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    MethodChannel(flutterView, CHANNEL).setMethodCallHandler { call, result ->
      if (call?.method.equals("getBatteryLevel")){
        val batteryLevel = getBatteryLevel()

        if (batteryLevel != -1){
          result?.success(batteryLevel)
        } else {
          result?.error("UNAVAILABLE", "Could not fetch battery level", null)
        }
      } else {
        result?.notImplemented()
      }
    }

    GeneratedPluginRegistrant.registerWith(this)
  }
}
