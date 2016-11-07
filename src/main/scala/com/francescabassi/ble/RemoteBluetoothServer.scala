package com.francescabassi.ble

/**
  * @author francescabassi
  */
object RemoteBluetoothServer extends App {

  println("BLE SERVER .... ")
  println("... create thread ...")
  val waitThread = new Thread(new WaitThread())
  waitThread.start()

}
