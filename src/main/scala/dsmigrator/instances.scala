/** This file contains default or common "instances" of core typeclasses in
  * dsmigrator.
  */
package dsmigrator

/**
  *
  */
trait Instances extends Classes with Types {
  import scalaz._, Scalaz._

  def actionBinOp[A]: BinOp[Action[A]] =
    (a1, a2) => ActionSeries[A](a1 :: a2 :: Nil)

  def actionIdentity[A]: Action[A] = NoopAction

  def actionInverse[A]: Action[A] => Action[A] = (a: Action[A]) =>
    a match {
      case CreateAction(e)       => DeleteAction(e)
      case DeleteAction(e)       => CreateAction(e)
      case EnableAction(e)       => DisableAction(e)
      case DisableAction(e)      => EnableAction(e)
      case RenameAction(s, t, e) => RenameAction(t, s, e)
      case NoopAction            => NoopAction
      case ActionSeries(actions) => ActionSeries(actions.reverse)
    }

  implicit def ActionSemigroup[A]: Semigroup[Action[A]] =
    Semigroup.instance[Action[A]](actionBinOp[A])

  implicit def ActionMonoid[A: Semigroup]: Monoid[Action[A]] =
    Monoid.instance[Action[A]](actionBinOp[A], actionIdentity[A])

  implicit def ActionGroup[A: Monoid]: Group[Action[A]] =
    Group.instance[Action[A]](
      actionBinOp[A]
    , actionIdentity[A]
    , actionInverse[A])
}

