package aalto.smcl.infrastructure.tests


import org.scalatest.{Args, ConfigMap}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class DefaultArgsUpdater extends ArgsUpdater {

  /**
   *
   *
   * @param args
   * @param data
   * @return
   */
  override def appendToConfigMap(args: Args, data: Map[String, Any]): Args = {
    val newContent = if (args.configMap != null) args.configMap ++ data else data
    val newConfigMap = new ConfigMap(newContent)

    Args(
      reporter = args.reporter,
      stopper = args.stopper,
      filter = args.filter,
      configMap = newConfigMap,
      distributor = args.distributor,
      tracker = args.tracker,
      chosenStyles = args.chosenStyles,
      runTestInNewInstance = args.runTestInNewInstance,
      distributedTestSorter = args.distributedTestSorter,
      distributedSuiteSorter = args.distributedSuiteSorter
    )
  }

}
