# Datastore Migrator

[![Build Status](https://travis-ci.org/mbbx6spp/dsmigrator.svg?branch=master)](https://travis-ci.org/mbbx6spp/dsmigrator)

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

## Motivations

Here are some of the reasons not to build the same kind of thing over and
over again for the main different datastores, search engines, message
brokers, etc that require some kind of "schema" (used loosely here).

First of all I think the activities for each of these different types of
stores is overwhelmingly similar from an infrastructure management
perspective.

Secondly separating out the plan from the migrations themselves provides
a way to solve the issue of having different needs in different environments.
Where *environment* might be customer installs for single tenant applications
or different sets of nodes for staging, profiling, preproduction, and
production in multi-tenant configurations.

At the time I started this project I wasn't aware of a tool that provided
such a model to help solve the problems I was seeing at work where we had
HBase, ElasticSearch, Redis, Kafka, etc. which all needed to have their
"schemas" managed upon deployment of new application code (sometimes). This
leaves the application not needing to handle the setup of, say, topics on
message brokers (e.g. Kafka, RabbitMQ, etc.) and keep their focus on the
application logic.

## Roadmap

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

## Code Structure

I use a less common structure to my code than many Scala projects so here is
the code structure outline and explanation:

* **Types:** I use a _more_ functional style of coding in Scala and as a
  result start coding from _*closed*_ algebraic data types (usually sum,
  product, and recursive types), which define the basic elements of the
  domain. These traits (types) and corresponding case classes/objects (value
  constructors) can be found in the `Types` trait for namespacing. See below
  for description of namespace organization.
* **"Classes":**  this does not refer to OO classes but rather typeclass
  definitions. I have a trait named `Classes` which contains typeclass
  definitions used to extend our basic sum, product, and recursive data
  types. One such type I am using to extend my core `Action[A]` type is
  [`Group[A]`](https://github.com/mbbx6spp/dsmigrator/blob/master/src/main/scala/dsmigrator/classes.scala#L21-L36)
* **"Instances":** again this does not refer to "instances" of OO classes,
  rather this refers to implementations of typeclasses for specific types.
  In the trait named `Instances` you will find a number of implicits that
  can be mixed in to different contexts later the allows Scala to find
  the correct instance definition for a specific type of a typeclass based
  on the scope it is introduced. More specific scopes have higher precedence
  which means the default `dsmigrator` package instance definitions can be
  overridden in applicaation/client code at a higher level if necessary.
* **Functions:** I have a trait named `Functions`, which along side the
  interface to our core types and typeclasses provides the public API for
  the `dsmigrator` library/toolkit.
* **Namespacing:** You will note I am using traits for namespacing primitives
  that I then use to mixin to objects used for external package namespacing.
  View the code in [src/main/scala/dsmigrator/package.scala](https://github.com/mbbx6spp/dsmigrator/blob/master/src/main/scala/dsmigrator/package.scala)
  to see how the `dsmigrator.core` package is constructed from traits. You
  might also notice the following inline comments in the `Types` trait
  that hints at a minor issue with scalac supported my preferred `final case`
  style in [src/main/scala/dsmigrator/types.scala](https://github.com/mbbx6spp/dsmigrator/blob/master/src/main/scala/dsmigrator/types.scala#L5)

## FAQ

* **So the building blocks of my code are *closed*, how can we extend them?**
  Great question. Essentially the are only closed on construction, however,
  using ad-hoc polymorphism via Scala's powerful implicits feature we can
  extend these types _interfaces_. The benefit here is twofold. Firstly we
  have the ability to control how values of our basic types are constructed,
  which allows us to ensure they are constructed in valid forms only.
  Secondly we can still extend the effective interface of the types so
  we can use them in well-defined ways later without coupling the definition
  of the type with all the possible ways it can be used. This is contrary
  to mainstream OO techniques using interfaces that need to be "implemented"
  at time of type definition as opposed to later. Note: not all OO languages
  are this limited just the most used ones :(

## License

This software is licensed under the BSD 3-clause license. See LICENSE file
for more information.

## Author(s) / Maintainer(s)

So far just me, [Susan Potter](https://github.com/mbbx6spp).

## Contributor(s)

Coming soon. Submit a single patch and see your name in flashing lights here.
Or something.
