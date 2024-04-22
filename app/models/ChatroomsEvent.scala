package models

// Basically saying interface ChatroomsEvent{}
// Needed for polymorphism for the actors
sealed trait ChatroomsEvent

case class AccessChatroom(name: String, username: String) extends ChatroomsEvent