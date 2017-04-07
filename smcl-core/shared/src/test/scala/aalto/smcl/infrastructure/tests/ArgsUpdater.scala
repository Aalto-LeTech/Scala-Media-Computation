package aalto.smcl.infrastructure.tests


import org.scalatest.Args




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait ArgsUpdater {

  /**
   *
   *
   * @param args
   * @param data
   * @return
   */
  def appendToConfigMap(args: Args, data: Map[String, Any]): Args

}
