package auth

import org.apache.pekko
import scala.collection.immutable.Map
import spray.json.DefaultJsonProtocol._
import spray.json._
import javax.crypto.spec.SecretKeySpec
import javax.crypto.Mac
import java.util._

object JWT {
    private val hmacKey = "MySuperSecretKey"
    private val algorithm = "HmacSHA256"

    private def getHMACAlgorithm: Mac = {
        val keySpec = new SecretKeySpec(hmacKey.getBytes(), algorithm)
        val mac = Mac.getInstance(algorithm)
        mac.init(keySpec)
        mac
    }

}

class JWT(header: Map[String, spray.json.JsValue], body: Map[String, spray.json.JsValue]) {
    def getHeader = header
    def getBody = body


    override def toString(): String = {
        val encoder = Base64.getUrlEncoder()
        val strVal = encoder.encodeToString(header.toJson.toString.getBytes()) + "." + Base64.getUrlEncoder().encodeToString(body.toJson.toString.getBytes())
        strVal + "." + encoder.encodeToString(JWT.getHMACAlgorithm.doFinal(strVal.getBytes()))
    }
}