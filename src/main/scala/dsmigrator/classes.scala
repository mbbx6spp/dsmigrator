/* This file contains "typeclass" definitions used in dsmigrator packages */
package dsmigrator

/**
  * CoreClasses trait contains "typeclasses" and related type aliases for the
  * purposes of the dsmigrator package and related code.
  */
trait Classes {
  import scalaz._, Scalaz._

  // Used for improved code readability
  @inline type BinOp[A] = (A, => A) => A

  /**
    * Represents an algebraic Group, which has two operations, an identity
    * value, and is closed (conforms to closure property) over type A. The
    * first operation is an associative binary operator. The second operation
    * represents inversion. Group[A] extends Monoid[A], which already provides
    * identity, associative binary operator, and closure.
    */
  trait Group[A] extends Monoid[A] {
    def inverse(a: A): A
  }
  object Group {
    @inline def apply[A](implicit A: Group[A]): Group[A] = A

    /**
      * Used to construct a new Group[A] definition
      */
    def instance[A](binOp: BinOp[A], id: A, inv: A => A): Group[A] =
      new Group[A] {
        def append(x: A, y: => A) = binOp(x, y)
        def zero: A               = id
        def inverse(a: A): A      = inv(a)
      }
  }
}

