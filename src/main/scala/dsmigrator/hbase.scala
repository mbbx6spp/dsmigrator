/** This file contains all HBase related types, instances, and functions (on
  * interim basis until it is extracted into its own plugin).
  */
package dsmigrator

import scalaz._, Scalaz._

trait HBaseTypes
  extends Classes
  with Types
  with Instances {

  sealed trait HBaseEntity

  private case class HTable(name: String) extends Table
  sealed trait Table extends HBaseEntity {
    def name: String
  }
  object Table {
    implicit val TableEqual: Equal[Table] = Equal.equal(_ == _)
    implicit val TableOrder: Order[Table] = Order.orderBy(_.name)

    @inline def apply(name: String): Table = HTable(name)
  }

  private case class HSnapshot(name: String) extends Snapshot
  sealed trait Snapshot extends HBaseEntity {
    def name: String
  }
  object Snapshot {
    implicit val SnapshotEqual: Equal[Snapshot] = Equal.equal(_ == _)
    implicit val SnapshotOrder: Order[Snapshot] = Order.orderBy(_.name)

    @inline def apply(name: String): Snapshot = HSnapshot(name)
  }

  // TODO: Create product type value constructor for Column type
  private case class HColumn(name: String) extends Column
  sealed trait Column extends HBaseEntity
  object Column {
    @inline def apply(name: String): Column = HColumn(name)
  }
  // TODO: Create product type value constructor for ColumnGroup type
  private case class HColumnGroup(name: String) extends ColumnGroup
  sealed trait ColumnGroup extends HBaseEntity
  object ColumnGroup {
    @inline def apply(name: String): ColumnGroup = HColumnGroup(name)
  }

  type TableAction = Action[Table]
  type ColumnAction = Action[Column]
  type ColumnGroupAction = Action[ColumnGroup]
}

trait HBaseInstances
  extends Classes
  with Instances
  with HBaseTypes {

}

trait HBaseFunctions
  extends Classes
  with Types
  with Instances
  with HBaseTypes {



}

trait HBaseMain
  extends Classes
  with Types
  with Instances
  with HBaseTypes
  with HBaseFunctions {

  import org.apache.hadoop.conf.Configuration
  import org.apache.hadoop.hbase._
  import org.apache.hadoop.hbase.client.HBaseAdmin

}
