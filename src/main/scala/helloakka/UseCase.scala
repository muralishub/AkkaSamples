package helloakka

import akka.actor.{Actor, ActorRef}
import helloakka.Checker.{BlackUser, CheckUser, WhiteUser}
import helloakka.Recorder.NewUser
import helloakka.Storage.AddUser


// we want to store some user and before we store we have to make sure if that user is blacklisted


// we start by creating 3 actors Recorder, Checker and Storage
//Recorder is responsible for handling a new user message, this will send a message to Checker to check if this user is blacklisted or not, if its in the list checker will send BlackUser/WhiteUser
// to recorder if its WhiteUser it will send AssUser message to storage
// from Recorder to Checker will be Ask and to Storage will be a tell


case class User(userName: String, email: String)



object Recorder {
  sealed trait RecorderMsg
  case class NewUser(user: User) extends RecorderMsg
}



object Checker {
  sealed trait CheckerMsg
  case class CheckUser(user: User) extends CheckerMsg

  sealed trait CheckerResponse
case class BlackUser(user: User) extends CheckerResponse
  case class WhiteUser(user: User) extends CheckerResponse

}


object Storage {
 sealed trait StorageMsg
case class AddUser(user: User) extends StorageMsg
}

class Storage extends Actor {

  var users = List.empty[User]

  override def receive: Receive = {
    case AddUser(user) => {
      println(s"user $user added to list")
      users = user +: users
    }
  }
}

class Checker extends Actor {

  val blackList = List(User("Adam", "adam@gmail.com"))


  override def receive: Receive = {
    case CheckUser(user) if blackList.contains(user) => {
      println(s"user $user is blacklisted")
     sender() ! BlackUser(user)
    }
      case CheckUser(user) => {
        println(s"user $user is not blackListed")
        sender() ! WhiteUser(user)

    }

  }
}


class Recorder(checker: ActorRef,  ) extends Actor {
  override def receive: Receive = {
    case NewUser(user) =>
      checker ?
  }
}



