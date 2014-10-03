/** This file contains specifications for the instances defined for
  * dsmigrator types.
  */
package dsmigrator

import org.scalacheck.Properties
import org.scalacheck.Prop
import org.scalacheck.Arbitrary

import scalaz._
import Scalaz._
import scalacheck.ScalazProperties._
import scalacheck.ScalazArbitrary._
import scalacheck.ScalaCheckBinding._

import dsmigrator.core._

import scala.language.higherKinds

object TestChecks {
  import TestInstances._

  object group {
    def inverseInverseIdentity[A](implicit g: Group[A], e: Equal[A], arb: Arbitrary[A]) =
      Prop.forAll { (a: A) =>
        g.inverse(g.inverse(a)) === a
      }

    def laws[A](implicit g: Group[A], e: Equal[A], arb: Arbitrary[A]) =
      new Properties("group") {
        include(monoid.laws[A])
        property("inverse of the inverse is identity") = inverseInverseIdentity[A]
      }
  }
}
