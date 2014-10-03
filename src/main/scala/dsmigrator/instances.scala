/** This file contains default or common "instances" of core typeclasses in
  * dsmigrator.
  */
package dsmigrator

import scalaz._, Scalaz._

/**
  * This trait contains typeclass instances for typeclasses defined in
  * this project and its associated helper functions.
  */
trait Instances extends Classes with Types {
  /** Defines binary operator for use in Action[A]'s semigroup, monoid, and
    * group definitions */
  def actionBinOp[A]: BinOp[Action[A]] = (a1, a2) =>
    actionNormalize[A]((a1, a2) match {
      case (Action.NoopAction, _) =>
        a2
      case (_, Action.NoopAction) =>
        a1
      case (Action.ActionSeries(l1), Action.ActionSeries(l2)) =>
        Action.ActionSeries(l1 ++ l2)
      case (Action.ActionSeries(l1), a2) =>
        Action.ActionSeries(l1 :+ a2)
      case (a1, Action.ActionSeries(l2)) =>
        Action.ActionSeries(a1 +: l2)
      case (a1, a2) =>
        Action.ActionSeries(a1 :: a2 :: Nil)
    })

  /** Defines identity for use in Action[A]'s monoid and group definitions */
  def actionIdentity[A]: Action[A] = Action.NoopAction

  /** Defines inverse for use in Action[A]'s group definition */
  def actionInverse[A]: Action[A] => Action[A] = (a: Action[A]) =>
    actionNormalize[A](a match {
      case Action.CreateAction(e)       => Action.DeleteAction(e)
      case Action.DeleteAction(e)       => Action.CreateAction(e)
      case Action.EnableAction(e)       => Action.DisableAction(e)
      case Action.DisableAction(e)      => Action.EnableAction(e)
      case Action.RenameAction(s, t, e) => Action.RenameAction(t, s, e)
      case Action.ActionSeries(actions) => Action.ActionSeries(actions.reverse)
      case Action.NoopAction            => Action.NoopAction
    })

  /** Defines normalize function on Action[A]s */
  def actionNormalize[A]: Action[A] => Action[A] = (a: Action[A]) =>
    a match {
      case Action.ActionSeries(l) =>
        (l filter (_ =/= Action.NoopAction)) match {
          case Nil        => Action.NoopAction
          case a1 :: Nil  => a1
          case l2         => Action.ActionSeries(l2)
        }
      case _ =>
        a
    }

  implicit def actionSemigroup[A]: Semigroup[Action[A]] =
    Semigroup.instance[Action[A]](actionBinOp[A])

  implicit def actionMonoid[A: Semigroup]: Monoid[Action[A]] =
    Monoid.instance[Action[A]](actionBinOp[A], actionIdentity[A])

  implicit def actionGroup[A: Monoid]: Group[Action[A]] =
    Group.instance[Action[A]](
      actionBinOp[A]
    , actionIdentity[A]
    , actionInverse[A]
    )

  implicit def actionEqual[A]: Equal[Action[A]] =
    Equal.equal[Action[A]] { actionNormalize(_) == actionNormalize(_) }

}
object Instances extends Instances

