/** This file contains core types useful by dsmigrator */
package dsmigrator

trait Types {
  import scalaz._, Scalaz._

  sealed trait Action[+A]
  object Action {
    case class CreateAction[A](entity: A) extends Action[A]
    case class DeleteAction[A](entity: A) extends Action[A]
    case class EnableAction[A](entity: A) extends Action[A]
    case class DisableAction[A](entity: A) extends Action[A]
    case class RenameAction[A](source: String, target: String, entity: A) extends Action[A]
    case class ActionSeries[A](actions: Seq[Action[A]]) extends Action[A]
    case object NoopAction extends Action[Nothing]

    def identity[A]: Action[A] = ActionSeries(Seq.empty[Action[A]])

    // TODO Move this into Instances when I figure out how to preserve proper separation?
    implicit val actionFunctor: Functor[Action] = new Functor[Action] {
      def map[A, B](fa: Action[A])(f: A => B): Action[B] = fa match {
        case c: CreateAction[A]   => CreateAction(f(c.entity))
        case d: DeleteAction[A]   => DeleteAction(f(d.entity))
        case e: EnableAction[A]   => EnableAction(f(e.entity))
        case d: DisableAction[A]  => DisableAction(f(d.entity))
        case r: RenameAction[A]   => RenameAction(r.source, r.target, f(r.entity))
        case a: ActionSeries[A]   => ActionSeries(Seq.empty[Action[B]])
        case NoopAction           => NoopAction
      }
    }
  }
}
