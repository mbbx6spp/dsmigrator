package dsmigrator

import org.scalacheck.{Gen, Arbitrary}

import dsmigrator.core._

object TestInstances {
  sealed trait TestEntity
  final case class Bucket(name: String) extends TestEntity
  final case class Index(name: String) extends TestEntity

  implicit def actionArbitrary[A](implicit a: Arbitrary[A]): Arbitrary[Action[A]] =
    Arbitrary {
      for {
        a <- Arbitrary.arbitrary[A]
        c <- Gen.oneOf(actionConstructors[A])
      } yield c(a)
    }

  implicit def testEntityArbitrary(implicit a: Arbitrary[String]): Arbitrary[TestEntity] =
    Arbitrary {
      for {
        n <- Arbitrary.arbitrary[String]
        c <- Gen.oneOf(testEntityConstructors)
      } yield c(n)
    }

  private def testEntityConstructors: Seq[String => TestEntity] = Seq(
    s => Bucket(s)
  , s => Index(s)
  )

  private def actionConstructors[A]: Seq[A => Action[A]] = Seq(
    Action.CreateAction[A](_)
  , Action.DeleteAction[A](_)
  , Action.EnableAction[A](_)
  , Action.DisableAction[A](_)
  , Action.RenameAction[A]("source", "target", _)
  , a => Action.identity[A]
  //, a => Action.ActionSeries(Action.CreateAction[A](a) :: Action.EnableAction[A](a) :: Nil)
  //, a => Action.ActionSeries(Action.DeleteAction[A](a) :: Action.NoopAction :: Nil)
  )
}
