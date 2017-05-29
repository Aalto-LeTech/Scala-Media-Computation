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

package aalto.smcl.colors.rgb


import aalto.smcl.colors.ColorValidator
import aalto.smcl.infrastructure._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object ColorComponentTranslationTable extends InjectablesRegistry {

  /** The CommonValidators instance to be used by this object. */
  private lazy val commonValidators: CommonValidators = {
    injectable(InjectablesRegistry.IIdCommonValidators).asInstanceOf[CommonValidators]
  }

  /** The ColorValidator instance to be used by this object. */
  private lazy val colorValidator: ColorValidator = {
    injectable(InjectablesRegistry.IIdColorValidator).asInstanceOf[ColorValidator]
  }

  /** The RGBATranslationTableValidator instance to be used by this object. */
  private lazy val rgbaTranslationTableValidator: ColorTranslationTableValidator = {
    injectable(InjectablesRegistry.IIdRGBATranslationTableValidator)
        .asInstanceOf[ColorTranslationTableValidator]
  }

  /**
   *
   *
   * @param reds
   * @param greens
   * @param blues
   * @param opacities
   *
   * @return
   */
  def apply(
      reds: Seq[Short],
      greens: Seq[Short],
      blues: Seq[Short],
      opacities: Seq[Short]): ColorComponentTranslationTable = {

    rgbaTranslationTableValidator.validateSeparateDimensions(reds, greens, blues, opacities)

    new ColorComponentTranslationTable(
      Seq(reds, greens, blues, opacities),
      colorValidator)
  }

  /**
   *
   *
   * @param valueProvider
   *
   * @return
   */
  def apply(valueProvider: Short => (Short, Short, Short, Short)): ColorComponentTranslationTable = {
    require(valueProvider != null, "Value provider function argument cannot be null.")

    val tableArray = Array.ofDim[Short](4, ByteRange.length)

    for (rowIndex <- ByteRange) {
      val rgbaRowCandidate: (Short, Short, Short, Short) = valueProvider(rowIndex.toShort)

      rgbaTranslationTableValidator.validateFunctionProvidedComponents(rgbaRowCandidate)

      tableArray(0)(rowIndex) = rgbaRowCandidate._1
      tableArray(1)(rowIndex) = rgbaRowCandidate._2
      tableArray(2)(rowIndex) = rgbaRowCandidate._3
      tableArray(3)(rowIndex) = rgbaRowCandidate._4
    }

    val tableSeq: Seq[Seq[Short]] = tableArray.map(_.toList).toList

    new ColorComponentTranslationTable(tableSeq, colorValidator)
  }

  /** */
  lazy val forNegation: ColorComponentTranslationTable = {
    ColorComponentTranslationTable{index =>
      ((ColorValidator.MaximumRed - index).toShort,
          (ColorValidator.MaximumGreen - index).toShort,
          (ColorValidator.MaximumBlue - index).toShort,
          index)
    }
  }

  /** */
  lazy val forNegatingRed: ColorComponentTranslationTable = {
    ColorComponentTranslationTable{index =>
      ((ColorValidator.MaximumRed - index).toShort,
          index,
          index,
          index)
    }
  }

  /** */
  lazy val forNegatingRedAndGreen: ColorComponentTranslationTable = {
    ColorComponentTranslationTable{index =>
      ((ColorValidator.MaximumRed - index).toShort,
          (ColorValidator.MaximumGreen - index).toShort,
          index,
          index)
    }
  }

  /** */
  lazy val forNegatingGreen: ColorComponentTranslationTable = {
    ColorComponentTranslationTable{index =>
      (index,
          (ColorValidator.MaximumGreen - index).toShort,
          index,
          index)
    }
  }

  /** */
  lazy val forNegatingGreenAndBlue: ColorComponentTranslationTable = {
    ColorComponentTranslationTable{index =>
      (index,
          (ColorValidator.MaximumGreen - index).toShort,
          (ColorValidator.MaximumBlue - index).toShort,
          index)
    }
  }

  /** */
  lazy val forNegatingBlue: ColorComponentTranslationTable = {
    ColorComponentTranslationTable{index =>
      (index,
          index,
          (ColorValidator.MaximumBlue - index).toShort,
          index)
    }
  }

  /** */
  lazy val forNegatingRedAndBlue: ColorComponentTranslationTable = {
    ColorComponentTranslationTable{index =>
      ((ColorValidator.MaximumRed - index).toShort,
          index,
          (ColorValidator.MaximumBlue - index).toShort,
          index)
    }
  }

  /** */
  lazy val forKeepingOnlyRed: ColorComponentTranslationTable = {
    ColorComponentTranslationTable{index =>
      (index, 0, 0, index)
    }
  }

  /** */
  lazy val forKeepingOnlyRedAndGreen: ColorComponentTranslationTable = {
    ColorComponentTranslationTable{index =>
      (index, index, 0, index)
    }
  }

  /** */
  lazy val forKeepingOnlyGreen: ColorComponentTranslationTable = {
    ColorComponentTranslationTable{index =>
      (0, index, 0, index)
    }
  }

  /** */
  lazy val forKeepingOnlyGreenAndBlue: ColorComponentTranslationTable = {
    ColorComponentTranslationTable{index =>
      (0, index, index, index)
    }
  }

  /** */
  lazy val forKeepingOnlyBlue: ColorComponentTranslationTable = {
    ColorComponentTranslationTable{index =>
      (0, 0, index, index)
    }
  }

  /** */
  lazy val forKeepingOnlyRedAndBlue: ColorComponentTranslationTable = {
    ColorComponentTranslationTable{index =>
      (index, 0, index, index)
    }
  }

  /**
   *
   *
   * @param strengthAsPercentage
   *
   * @return
   */
  def forPosterization(strengthAsPercentage: Int): ColorComponentTranslationTable = {
    commonValidators.validatePercentage(strengthAsPercentage, Option("Strength"))

    val divisor = strengthAsPercentage + 1

    ColorComponentTranslationTable{index =>
      val v = (index - index % divisor).toShort
      (v, v, v, index)
    }
  }

}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
case class ColorComponentTranslationTable private(
    table: Seq[Seq[Short]],
    private val colorValidator: ColorValidator)
    extends ColorTranslator
            with Immutable {

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   * @param opacity
   *
   * @return
   */
  def translate(red: Int, green: Int, blue: Int, opacity: Int): (Int, Int, Int, Int) = {
    colorValidator.validateRGBAColor(red, green, blue, opacity)

    (table.head(red.toShort),
        table(1)(green.toShort),
        table(2)(blue.toShort),
        table.last(opacity.toShort))
  }

  /**
   *
   *
   * @return
   */
  def toArray: Array[Array[Short]] = table.map(_.toArray).toArray

}
