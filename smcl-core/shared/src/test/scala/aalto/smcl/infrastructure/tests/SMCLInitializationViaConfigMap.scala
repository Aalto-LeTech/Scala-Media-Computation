package aalto.smcl.infrastructure.tests


import org.scalatest.{Args, FreeSpec, Status}

import aalto.smcl.infrastructure.{SMCLInitializer, isScalaJSPlatform}




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
    runSMCLInitializersPassedViaConfigMapIn(args, isScalaJSPlatform)

    super.runTests(testName, args)
  }

  /**
   *
   *
   * @param args
   */
  protected def runSMCLInitializersPassedViaConfigMapIn(
      args: Args,
      runningOnScalaJSPlatform: Boolean): Unit = {

    if (args == null) {
      if (runningOnScalaJSPlatform)
        return

      fail("Tried to run SMCL shared tests without an Args instance for passing SMCL initializers")
    }

    val configMap = Option(args.configMap).getOrElse {
      if (runningOnScalaJSPlatform)
        return

      fail("Tried to run SMCL shared tests without a config map for passing SMCL initializers")
    }

    val initializers = configMap.getOrElse("smcl-initializers", {
      if (runningOnScalaJSPlatform)
        return

      fail("Tried to run SMCL shared tests with no SMCL initializers in the config map")
    }).asInstanceOf[List[SMCLInitializer]]

    initializers foreach {initializer => initializer()}
  }

}
