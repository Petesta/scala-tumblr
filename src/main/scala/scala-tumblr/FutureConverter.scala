package io.github.petesta.tumblr

import com.twitter.util.{Future => TwitterF}
import scala.concurrent.{Await, Future => ScalaF, Promise => ScalaP}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object FutureConverter {
  def twitterToScala[T](twitterF: TwitterF[T]): ScalaF[T] = {
    val scalaP = ScalaP[T]
    twitterF.onSuccess { r: T =>
      scalaP.success(r)
    }
    twitterF.onFailure { e: Throwable =>
      scalaP.failure(e)
    }
    scalaP.future
  }
}
