package aalto.smcl


import scala.collection.GenTraversable
import scala.language.{higherKinds, implicitConversions}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object infrastructure extends Constants {

  /** Global settings storage. */
  val GS: Settings = new Settings()

  /** Global platform resource factory. */
  val PRF: PlatformResourceFactory = DefaultPlatformResourceFactory


  /** */
  //noinspection TypeParameterShadow
  private[smcl]
  implicit def GenTraversableWrapper[E, C[E] <: GenTraversable[E]](self: C[E]): RichGenTraversable[E, C] =
    new RichGenTraversable[E, C](self)

}
