package com.francescabassi.ble

import javax.bluetooth.BluetoothStateException
import javax.bluetooth.DiscoveryAgent
import javax.bluetooth.LocalDevice
import javax.bluetooth.UUID
import javax.microedition.io.Connector
import javax.microedition.io.StreamConnection
import javax.microedition.io.StreamConnectionNotifier;
import java.io.IOException

/**
  * @author francescabassi
  */
class WaitThread extends Runnable {

  def run() = {
    waitForConnection()
  }

  /*
   * Waiting for connection from devices
   */
  def waitForConnection() = {

    // setup the server to listen for connection
    try {
      val local: LocalDevice = LocalDevice.getLocalDevice
      local.setDiscoverable(DiscoveryAgent.GIAC)
      val uuid = new UUID("04c6093b00001000800000805f9b34fb", false)
      val url = "btspp://localhost:" + uuid
          .toString() + ";name=RemoteBluetooth"
      val notifier = Connector.open(url).asInstanceOf[StreamConnectionNotifier]
      // waiting for connection
      while (true) {
        try {
          println("waiting for connection...")

          val connection = notifier.acceptAndOpen()
          val processThread = new Thread(
            new ProcessConnectionThread(connection))
          processThread.start()
        } catch {
          case e: Exception => println(e.printStackTrace())
          case _: Throwable => println("I don't know...")
        }
      }

    } catch {
      case e: BluetoothStateException =>
        println("Bluetooth is not turned on. \n" + e.printStackTrace())
      case e: IOException => println(e.printStackTrace())
      case _: Throwable => println("I don't know...")
    }

  }

}
