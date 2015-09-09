package aalto.smcl


import aalto.smcl.metadata.MetadataSettingKeys




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object init extends MetadataSettingKeys with InitializableModule {

  /** */
  type PathString = String


  /** */
  val ClassInfoProvider: ClassInfoProvider = new ClassInfoProvider()

  /** */
  val LibraryInitializer: LibraryInitializer = new LibraryInitializer()

}
