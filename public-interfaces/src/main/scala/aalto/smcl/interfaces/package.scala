package aalto.smcl


/**
 * Public interfaces defined for the Scala Media Computation Library.
 *
 * @author Aleksi Lukkarinen
 */
package object interfaces {

  /** Global registry for metadata providers. */
  val GlobalMetadataInterfaceSourceRegistry =
    new MetadataInterfaceSourceProviderRegistry()

}
