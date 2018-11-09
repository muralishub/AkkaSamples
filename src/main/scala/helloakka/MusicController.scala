package helloakka

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import helloakka.MusicController.{Play, Stop}
import helloakka.MusicPlayer.{StartMusic, StopMusic}


// music controller actor
object MusicController {
  //messages
  sealed trait ControllerMsg
  case object Play extends ControllerMsg
  case object Stop extends ControllerMsg

  def props =  Props[MusicController]

}

class MusicController extends Actor {
  override def receive: Receive = {
    case Play => println("music started ....")
    case Stop => println("music stopped ....")
  }
}



//music player actor
object MusicPlayer {
// create messages
  sealed  trait PlayMsg
  case object StopMusic extends PlayMsg
  case object StartMusic extends PlayMsg


}


class MusicPlayer extends Actor {
  override def receive: Receive = {
    case StopMusic =>
      println("I dont want to stop music")
    case StartMusic =>
      // create music controller instance and send message for it
      // declaring 1 actor inside another is very dangerous it breaks actor encapsulation
      val controller = context.actorOf(MusicController.props, "controller")
      controller ! Play
    case _ => println("unknown")
  }
}


// lets create a running instance for music player actor

object Creation extends App {

  // crate actor system
  val system = ActorSystem("creation")


// create music player actor
  val player: ActorRef = system.actorOf(Props[MusicPlayer], "player")

  player ! StartMusic


}