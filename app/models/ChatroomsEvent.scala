package models

sealed trait ChatroomsEvent

case class AccessChatroom(name: String, username: String) extends ChatroomsEvent