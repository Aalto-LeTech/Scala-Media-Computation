/* .            .           .                   .                 +             .          +      */
/*         +-----------+  +---+    +  +---+  +-----------+  +---+    Media Programming in Scala   */
/*   *     |           |  |    \     /    |  |           | +|   |            Since 2015           */
/*         |   +-------+  |     \   /     |  |   +-------+  |   |   .                        .    */
/*         |   |          |      \ /      |  |   |          |   |         Aalto University        */
/*       . |   +-------+  |   .   V   .   |  |   |   .      |   |      .   Espoo, Finland       . */
/*  +      |           |  |   |\     /|   |  |   |          |   |                  .    +         */
/*         +------+    |  |   | \   / |   |  |   |          |   |    +        *                   */
/*    *           |    |  |   |  \ /  |   |  |   |      *   |   |                     .      +    */
/*      -- +------+    |  |   |   V  *|   |  |   +-------+  |   +-------+ --    .                 */
/*    ---  |           |  |   | .     |   |  |           |  |           |  ---      +      *      */
/*  ------ +-----------+  +---+       +---+  +-----------+  +-----------+ ------               .  */
/*                                                                                     .          */
/*     T H E   S C A L A   M E D I A   C O M P U T A T I O N   L I B R A R Y      .         +     */
/*                                                                                    *           */

package smcl.settings


import smcl.infrastructure.InjectablesRegistry




/**
 * Constant definitions for companion objects of [[Setting]] subclasses.
 *
 * @author Aleksi Lukkarinen
 */
trait SettingCompanionConstants extends InjectablesRegistry {

  /** Name of the related [[Setting]] subclass. */
  def FullTypeName: String

  /** Singular form of the "layman's name" of the setting's data type in lower case. */
  def TypeNameSingular: String

  /** Plural form of the "layman's name" of the setting's data type in lower case. */
  def TypeNamePlural: String

  /** The [[SettingRegisterer]] instance to be used by this object. */
  protected lazy val settingRegisterer: SettingRegisterer = {
    injectable[SettingRegisterer](InjectablesRegistry.IIdSettingRegisterer)
  }

}
