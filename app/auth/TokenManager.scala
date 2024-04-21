package auth

import org.apache.pekko
import scala.collection.immutable.Map
import spray.json.DefaultJsonProtocol._
import spray.json._
import javax.crypto.spec.SecretKeySpec
import javax.crypto.Mac
import java.util._
import java.time._
import java.util.Date

type JsType = JsValue

object JWT {
    private val hmacKey = "MySuperSecretKey"
    private val algorithm = "HmacSHA256"

    private def getHMACAlgorithm: Mac = {
        val keySpec = new SecretKeySpec(hmacKey.getBytes(), algorithm)
        val mac = Mac.getInstance(algorithm)
        mac.init(keySpec)
        mac
    }

    def apply(header: Map[String, JsType], body: Map[String, JsType]): JWT = {
        val time = new Date().getTime()
        var headers = Map( // Default Headers
            "iat" -> JsNumber(time),
            "exp" -> JsNumber(time + (24 * 60 * 60 * 1000)),
            "iss" -> JsString("ME!")
        )
        headers ++= header
        new JWT(
            headers,
            body
        )
    }

    def decodeJWT(token: String): Option[JWT] = {
        val sections = token.split('.')
        val decoders = Base64.getUrlDecoder

        val body = sections(0) + "." + sections(1)

        val hash = Base64.getUrlEncoder.encodeToString(getHMACAlgorithm.doFinal(body.getBytes()))

        if (hash != sections(2)) {
            return None
        }

        val headerJson = String(decoders.decode(sections(0))).parseJson.convertTo[Map[String, JsType]]

        val exptime = headerJson.get("exp")

        if (!exptime.isDefined || new Date().getTime() - exptime.get.convertTo[Int] >= 0) {
            return None
        }

        Some(
            JWT(
                headerJson,
                String(decoders.decode(sections(1))).parseJson.convertTo[Map[String, JsType]],
            )
        )
    }
}

class JWT(header: Map[String, JsType], body: Map[String, JsType]) {
    def getHeader = header
    def getBody = body


    override def toString(): String = {
        val encoder = Base64.getUrlEncoder()
        val strVal = encoder.encodeToString(header.toJson.toString.getBytes()) + "." + Base64.getUrlEncoder().encodeToString(body.toJson.toString.getBytes())
        strVal + "." + encoder.encodeToString(JWT.getHMACAlgorithm.doFinal(strVal.getBytes()))
    }

    def getHeader = header
    def getBody = body
}