/** This file contains (on an interim basis until it is extracted into a
  * separate plugin) all ElasticSearch types, functions, and instances. */
package dsmigrator

trait ElasticSearchTypes extends Classes with Types with Instances {
  // TODO: Create product type value constructor for Index type
  trait Index

  type IndexAction = Action[Index]
}

