package io.finch.internal

import com.twitter.finagle.http.Request
import io.finch.FinchSpec
import java.nio.charset.{Charset, StandardCharsets}

class HttpMessageSpec extends FinchSpec {

  def slowCharset(req: Request): Charset = req.charset match {
    case Some(cs) => Charset.forName(cs)
    case None => StandardCharsets.UTF_8
  }

  "HttpMessage" should "charsetOrUtf8" in {
    check { cs: Charset =>
      val req = Request()
      req.contentType = "application/json"
      req.charset = cs.displayName()

      req.charsetOrUtf8 === slowCharset(req)
    }

    assert(Request().charsetOrUtf8 == StandardCharsets.UTF_8)
  }
}