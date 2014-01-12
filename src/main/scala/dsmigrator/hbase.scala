/** This file contains all HBase related types, instances, and functions (on
  * interim basis until it is extracted into its own plugin).
  */
package dsmigrator

trait HBaseTypes extends Classes with Types with Instances {
  // TODO: Create product type value constructor for Table type
  trait Table
  // TODO: Create product type value constructor for Column type
  trait Column
  // TODO: Create product type value constructor for ColumnGroup type
  trait ColumnGroup

  type TableAction = Action[Table]
  type ColumnAction = Action[Column]
  type ColumnGroupAction = Action[ColumnGroup]
}

