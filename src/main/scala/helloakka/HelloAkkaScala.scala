package helloakka

import akka.actor.{Actor, ActorRef, ActorSystem, Props}



//create message

case class WhoToGreet(str: String)

//create Greater actor

class Greeter extends Actor {
  override def receive: Receive = {
    case WhoToGreet(who) => println(s"hello $who")
  }
}






object HelloAkkaScala extends App{

  //crate actor system , we cant just create Actor instance

  val actorSystem = ActorSystem("hello-akka")

  // create instance of greeter actor, we do this by ActorRef. this means we have running instance of greeter actor

  val greeter: ActorRef = actorSystem.actorOf(Props[Greeter], "greeter")

    greeter ! WhoToGreet("murali")



  //send WhoToGreet message to actor





}
