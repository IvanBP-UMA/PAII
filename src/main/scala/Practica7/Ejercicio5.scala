package Practica7

import concurrencia.*
import scala.util.Random

object Barca{
  private var nIPhone = 0
  private var nAndroid = 0
  private var subir = true
  private var bajar = false
  private var puedeIphone = true
  private var puedeAndroid = true

  def paseoIphone(id:Int): Unit = synchronized {
    while (!subir || nIPhone + nAndroid == 4 || !puedeIphone) wait()
    nIPhone += 1

    if (nIPhone == 2 && nAndroid == 1){
      puedeIphone = false
    }else if ((nIPhone == 3) || (nIPhone == 1 && nAndroid == 2)){
      puedeAndroid = false
    }

    log(s"Estudiante IPhone $id se sube a la barca. Hay: iphone=$nIPhone,android=$nAndroid ")
    
    if (nIPhone + nAndroid == 4) {
      subir = false
      log(s"Empieza el viaje....")
      Thread.sleep(Random.nextInt(200))
      log(s"fin del viaje....")
      bajar = true
    }

    while (!bajar) wait()
    nIPhone -= 1
    if (nIPhone + nAndroid == 0) then {subir = true; bajar = false; puedeIphone = true; puedeAndroid = true}
    notifyAll()
    log(s"Estudiante IPhone $id se baja de la barca. Hay: iphone=$nIPhone,android=$nAndroid ")
   
  }

  def paseoAndroid(id:Int): Unit = synchronized {
    while (!subir || nIPhone + nAndroid == 4 || !puedeAndroid) wait()
    nAndroid += 1

    if (nAndroid == 2 && nIPhone == 1){
      puedeAndroid = false
    }else if ((nAndroid == 3) || (nAndroid == 1 && nIPhone == 2)){
      puedeIphone = false
    }

    log(s"Estudiante Android $id se sube a la barca. Hay: iphone=$nIPhone,android=$nAndroid ")

    if (nIPhone + nAndroid == 4) {
      subir = false
      log(s"Empieza el viaje....")
      Thread.sleep(Random.nextInt(200))
      log(s"fin del viaje....")
      bajar = true
    }

    while (!bajar) wait()
    nAndroid -= 1
    if (nIPhone + nAndroid == 0) then {subir = true; bajar = false; puedeIphone = true; puedeAndroid = true}
    notifyAll()
    log(s"Estudiante Android $id se baja de la barca. Hay: iphone=$nIPhone,android=$nAndroid ")
    
  }
}
object Ejercicio5 {

  def main(args:Array[String]): Unit = {
    val NPhones = 10
    val NAndroid = 10
    val iphone = new Array[Thread](NPhones)
    val android = new Array[Thread](NAndroid)
    for (i<-iphone.indices)
      iphone(i) = thread{
     //   while (true){
          Thread.sleep(Random.nextInt(400))
          Barca.paseoIphone(i)
        //    }
      }
    for (i <- android.indices)
      android(i) = thread {
     //   while (true) {
          Thread.sleep(Random.nextInt(400))
          Barca.paseoAndroid(i)
     //   }
      }
  }
}
