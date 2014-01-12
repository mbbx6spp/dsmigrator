/** This file contains core types useful by dsmigrator */
package dsmigrator

trait Types {
  /* Uncomment final when scalac doesn't constantly spew unchecked warnings */
  /*final*/ case class CreateAction[A](entity: A) extends Action[A]
  /*final*/ case class DeleteAction[A](entity: A) extends Action[A]
  /*final*/ case class EnableAction[A](entity: A) extends Action[A]
  /*final*/ case class DisableAction[A](entity: A) extends Action[A]
  /*final*/ case class RenameAction[A](source: String, target: String, entity: A) extends Action[A]
  /*final*/ case object NoopAction extends Action[Nothing]
  /*final*/ case class ActionSeries[A](actions: Seq[Action[A]]) extends Action[A]
  sealed trait Action[+A]
  object Action {
  }
}

