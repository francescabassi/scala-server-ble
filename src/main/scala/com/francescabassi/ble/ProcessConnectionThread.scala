package com.francescabassi.ble

import javax.microedition.io.StreamConnection
import java.io.InputStream
import java.io.OutputStream

/**
  * @author francescabassi
  */
class ProcessConnectionThread(val mConnection: StreamConnection)
    extends Runnable {

  val EXIT_CMD = "EXIT"

  def run() = {
    try {
      // prepare to receive data
      val inputStream = mConnection.openInputStream()
      val os = mConnection.openOutputStream()
      var count = 0
      println("waiting for input...")
      while (true) {
        var buffer: Array[Byte] = new Array[Byte](1024)
        val bytes = inputStream.read(buffer)
        val str: String = new String(buffer)
        println("received: " + str)
        var out: Array[Byte] = new Array[Byte](1024)
        val resp: String = "Ciao Android... " + count
        out = resp.toCharArray().map(_.toByte)
        os.write(out)
        count = count + 1
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }

}
