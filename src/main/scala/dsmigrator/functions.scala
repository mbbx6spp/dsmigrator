/** This file contains core functions used in dsmigrator and are exported as
  * a public API for users of dsmigrator.
  */
package dsmigrator

trait Functions extends Classes with Types with Instances {
  /* TODO Plan methods */

  /* TODO Migration methods */

  /** Returns new action that will create entity of type A */
  def create[A](entity: A): Action[A] = Action.CreateAction(entity)

  /** Returns new action that will delete entity of type A */
  def delete[A](entity: A): Action[A] = Action.DeleteAction(entity)

  /** Returns new action that will enable entity of type A */
  def enable[A](entity: A): Action[A] = Action.EnableAction(entity)

  /** Returns new action that will disable entity of type A */
  def disable[A](entity: A): Action[A] = Action.DisableAction(entity)

  /** Returns new action that does absolutely nothing :) */
  def noop[A]: Action[A] = Action.identity[A]

  /** Returns new action that will execute sequence of actions in order of type A */
  def series[A](actions: Seq[Action[A]]): Action[A] = Action.ActionSeries(actions)

  /** Returns new action that will rename source to target for a given entity of type A */
  def rename[A](s: String, t: String, e: A): Action[A] = Action.RenameAction(s, t, e)
}
