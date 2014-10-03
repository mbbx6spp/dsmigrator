/** This file contains specifications for the typeclass definitions in
  * dsmigrator.
  */

package dsmigrator

import org.scalacheck.Properties
import org.scalacheck.Prop.forAll

import scalaz._
import Scalaz._
import scalacheck.ScalazProperties._
import scalacheck.ScalazArbitrary._
import scalacheck.ScalaCheckBinding._

import dsmigrator.core._

object TypeclassesSpecification extends Properties("Typeclasses") {
  import TestInstances._

  // TODO
}
