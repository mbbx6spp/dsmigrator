# Datastore Migrator

Migration tool for non-RDBMS datastores. Support for different datastores
will be offered via plugin capability.

## Goals

To provide a way to:

* declaratively define schema *migrations*
* define *plan* which can be per environment
* detect *status* of *plan* for a *target*
* run *plan* of *migrations* against a *target*

## Terminology

* *Schema:* construct in a datastore providing structure for data, e.g. index
  with mappings in a search engine like ElasticSearch, table with columns and/or
  column families in HBase, etc.
* *Migration:* atomic unit of change
* *Plan:* sequence of migrations
* *Status:* report identifying what migrations have been applied and what are pending
* *Target:* specific datastore typically represented by a URL

## TODO

This is a project basically at it's inception so there is a lot to do before
we get the first working version shipped. Namely:

Version 0.1 should have the ability to do the following:

* Provide Scala API to declaratively define migrations
* Provide Scala API to declaratively define plans
* Report status of a plan for the given target
* Run plan of migrations against a target
* Implement migration runner for HBase

Version 0.2 should have the following capabilities in addition to 0.1:

* Implement migration runner for ElasticSearch 0.9.x/1.x
* Provide Java-compatible API to define migrations
* Provide Java-compatible API to defined plans

TODO define next version milestones.

## License

This software is licensed under the BSD 3-clause license. See LICENSE file
for more information.

## Author(s) / Maintainer(s)

So far just me, [Susan Potter](https://github.com/mbbx6spp).

## Contributor(s)

Coming soon. Submit a single patch and see your name in flashing lights here.
Or something.
