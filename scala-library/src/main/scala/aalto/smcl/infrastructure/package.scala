package aalto.smcl


import java.awt.{Color => AwtColor}

import scala.language.implicitConversions

import aalto.smcl.colors.RGBAColor




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object infrastructure
  extends InfrastructureSettingKeys
  with Constants
  with LibraryInitializationInvoker {


  /** */
  type PathString = String


  /** */
  //private[smcl]
  lazy val BaseSettingKeys: BaseSettingKeys = new BaseSettingKeys()

  /** */
  //private[smcl]
  lazy val ClassProvider: ClassProvider = new ClassProvider()

  /** */
  //private[smcl]
  lazy val ClassTokenizer: ClassTokenizer = new ClassTokenizer()

  /** */
  lazy val CommonValidators: CommonValidators = new CommonValidators()

  /** */
  lazy val DateTimeProvider: DateTimeProvider = new DateTimeProvider()

  /** */
  //private[smcl]
  lazy val FileUtils: FileUtils = new FileUtils()

  /** */
  private[smcl]
  lazy val FontProvider: FontProvider = new FontProvider()

  /** Global settings storage. */
  lazy val GS: Settings = new Settings()

  /** */
  private[smcl]
  lazy val ImageProvider: ImageProvider = new ImageProvider()

  /** */
  //private[smcl]
  lazy val LibraryInitializer: LibraryInitializer = new LibraryInitializer()

  /** */
  //private[smcl]
  lazy val ReflectionUtils: ReflectionUtils = new ReflectionUtils()

  /** */
  lazy val Screen: Screen = new Screen()

  /** */
  //private[smcl]
  lazy val SettingValidatorFactory: SettingValidatorFactory = new SettingValidatorFactory()

  /** */
  //private[smcl]
  lazy val StringUtils: StringUtils = new StringUtils()

  /** */
  //private[smcl]
  lazy val SwingUtils: SwingUtils = new SwingUtils()

  /** */
  //private[smcl]
  lazy val UIProvider: UIProvider = new UIProvider()

  /** */
  //private[smcl]
  lazy val UniqueIdProvider: UniqueIdProvider = new UniqueIdProvider()


  /** */
  private[infrastructure]
  implicit def AwtColorWrapper(self: AwtColor): RichAwtColor =
    new RichAwtColor(self)

  /** */
  private[infrastructure]
  implicit def RGBAColorExtendedWrapper(self: RGBAColor): ExtendedRichRGBAColor =
    new ExtendedRichRGBAColor(self)

}
