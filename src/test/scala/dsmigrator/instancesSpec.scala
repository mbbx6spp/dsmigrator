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

object InstancesSpecification extends Properties("Instances") {
  import TestInstances._
  import TestChecks._

  // Necessary to provide peace of mind that the free monad construction holds
  val actionFunctorCheck = functor.laws[Action].check

  // Necessary to provide sanity around inverse, associativity, and identity
  // laws of Action[_].
  val actionGroupIntCheck = group.laws[Action[Int]].check
  val actionGroupStringCheck = group.laws[Action[String]].check
  val actionGroupUnitCheck = group.laws[Action[Unit]].check
}
