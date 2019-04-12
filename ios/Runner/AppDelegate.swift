import UIKit
import Flutter

@UIApplicationMain
@objc class AppDelegate: FlutterAppDelegate {
  override func application(
    _ application: UIApplication,
    didFinishLaunchingWithOptions launchOptions: [UIApplicationLaunchOptionsKey: Any]?
  ) -> Bool {
    GeneratedPluginRegistrant.register(with: self)
    let controller = window.rootViewController as? FlutterViewController
    let batteryChannel = FlutterMethodChannel(name: "flutter-course.com/battery", binaryMessenger: controller!)
    batteryChannel.setMethodCallHandler { (call, result) in
        if ("getBatteryLevel" == call.method) {
            let batteryLevel: Int = self.getBatteryLevel()
            
            if batteryLevel == -1 {
                result(FlutterError(code: "UNAVAILABLE", message: "Battery info not available.", details: nil))
            } else {
                result(batteryLevel)
            }
        } else {
            result(FlutterMethodNotImplemented)
        }
    }

    return super.application(application, didFinishLaunchingWithOptions: launchOptions)
  }
    
    
    func getBatteryLevel() -> Int {
        let device = UIDevice.current
        device.isBatteryMonitoringEnabled = true
        if device.batteryState == .unknown {
            return -1
        } else {
            return Int(device.batteryLevel * 100)
        }
    }
}
