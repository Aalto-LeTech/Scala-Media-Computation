package aalto.smcl


import aalto.smcl.metadata.MetadataSettingKeys




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object init extends MetadataSettingKeys with InitializableModule {

  /** */
  val ClassInfoProvider: ClassInfoProvider = new ClassInfoProvider()

}
