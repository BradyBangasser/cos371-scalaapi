package models

import org.apache.pekko.actor.ActorRef

sealed trait SessionEvent

final case class SessionAccess(actor: ActorRef, username: String) extends SessionEvent
final case class SessionMessage(message: UserMessage) extends SessionEvent
final case class SessionDestroy(actor: ActorRef) extends SessionEvent