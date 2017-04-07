package aalto.smcl.infrastructure.tests


import org.scalatest.{Args, FreeSpec, Status}

import aalto.smcl.infrastructure.SMCLInitializer




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait SMCLInitializationViaConfigMap extends FreeSpec {

  /**
   *
   *
   * @param testName
   * @param args
   * @return
   */
  override def runTests(testName: Option[String], args: Args): Status = {
    runSMCLInitializersPassedViaConfigMapIn(args)

    super.runTests(testName, args)
  }

  /**
   *
   *
   * @param args
   */
  protected def runSMCLInitializersPassedViaConfigMapIn(args: Args): Unit = {
    if (args == null)
      return
      //fail("Tried to run SMCL shared tests without an Args instance for passing SMCL initializers")

    val configMap = Option(args.configMap).getOrElse {
      fail("Tried to run SMCL shared tests without a config map for passing SMCL initializers")
    }
    println(configMap)
    val initializers = configMap.getOrElse("smcl-initializers", {
      fail("Tried to run SMCL shared tests with no SMCL initializers in the config map")
    }).asInstanceOf[List[SMCLInitializer]]

    initializers foreach {initializer => initializer()}
  }

}
