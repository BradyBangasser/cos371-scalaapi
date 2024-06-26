package actors;

import org.apache.pekko.actor._
import scala.collection.mutable.Map

// I wrote this file using whitespace scala instead of the curly bracket syntax that I prefer

object RoomDirector:
    def apply(system: ActorSystem): RoomDirector = new RoomDirector(system)

/**
  * This will manage all of the ChatroomDirectors
  *
  * @note This system is a standard scala class rather than a actor
  * @param system The Actor System that it runs on
  */
class RoomDirector(system: ActorSystem):
    private var rooms = Map[String, ActorRef]()

    // The following functions are to show the scala public private system in comparison to java
    private def getActor(name: String): Option[ActorRef] = 
        return rooms.get(name)

    // Return the option just to be consistent 
    private def createActor(name: String): Option[ActorRef] = 
        var room = rooms.get(name)
        if (room.isDefined) return room
        else
            room = Some(system.actorOf(ChatroomDirectorActor.props(this, name)))
            rooms.put(name, room.get)
            return room
    
    def removeRoomActor(name: String): bool = 
        rooms.remove(name).isDefined
    
    // This is one of the short one liner statements that are valid in scala
    def getRoomActor(name: String): ActorRef = getActor(name).getOrElse(createActor(name).get)