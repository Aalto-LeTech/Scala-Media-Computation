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

package aalto.smcl.colors


import aalto.smcl.infrastructure._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object RGBAComponentTranslationTable extends InjectablesRegistry {

  /** The CommonValidators instance to be used by this object. */
  private lazy val commonValidators: CommonValidators = {
    injectable(InjectablesRegistry.IIdCommonValidators).asInstanceOf[CommonValidators]
  }

  /** The ColorValidator instance to be used by this object. */
  private lazy val colorValidator: ColorValidator = {
    injectable(InjectablesRegistry.IIdColorValidator).asInstanceOf[ColorValidator]
  }

  /** The RGBATranslationTableValidator instance to be used by this object. */
  private lazy val rgbaTranslationTableValidator: RGBATranslationTableValidator = {
    injectable(InjectablesRegistry.IIdRGBATranslationTableValidator)
        .asInstanceOf[RGBATranslationTableValidator]
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
      opacities: Seq[Short]): RGBAComponentTranslationTable = {

    rgbaTranslationTableValidator.validateSeparateDimensions(reds, greens, blues, opacities)

    new RGBAComponentTranslationTable(
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
  def apply(valueProvider: Short => (Short, Short, Short, Short)): RGBAComponentTranslationTable = {
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

    new RGBAComponentTranslationTable(tableSeq, colorValidator)
  }

  /** */
  lazy val forNegation: RGBAComponentTranslationTable = {
    RGBAComponentTranslationTable{index =>
      ((ColorValidator.MaximumRGBRed - index).toShort,
          (ColorValidator.MaximumRGBGreen - index).toShort,
          (ColorValidator.MaximumRGBBlue - index).toShort,
          index)
    }
  }

  /** */
  lazy val forNegatingRed: RGBAComponentTranslationTable = {
    RGBAComponentTranslationTable{index =>
      ((ColorValidator.MaximumRGBRed - index).toShort,
          index,
          index,
          index)
    }
  }

  /** */
  lazy val forNegatingRedAndGreen: RGBAComponentTranslationTable = {
    RGBAComponentTranslationTable{index =>
      ((ColorValidator.MaximumRGBRed - index).toShort,
          (ColorValidator.MaximumRGBGreen - index).toShort,
          index,
          index)
    }
  }

  /** */
  lazy val forNegatingGreen: RGBAComponentTranslationTable = {
    RGBAComponentTranslationTable{index =>
      (index,
          (ColorValidator.MaximumRGBGreen - index).toShort,
          index,
          index)
    }
  }

  /** */
  lazy val forNegatingGreenAndBlue: RGBAComponentTranslationTable = {
    RGBAComponentTranslationTable{index =>
      (index,
          (ColorValidator.MaximumRGBGreen - index).toShort,
          (ColorValidator.MaximumRGBBlue - index).toShort,
          index)
    }
  }

  /** */
  lazy val forNegatingBlue: RGBAComponentTranslationTable = {
    RGBAComponentTranslationTable{index =>
      (index,
          index,
          (ColorValidator.MaximumRGBBlue - index).toShort,
          index)
    }
  }

  /** */
  lazy val forNegatingRedAndBlue: RGBAComponentTranslationTable = {
    RGBAComponentTranslationTable{index =>
      ((ColorValidator.MaximumRGBRed - index).toShort,
          index,
          (ColorValidator.MaximumRGBBlue - index).toShort,
          index)
    }
  }

  /** */
  lazy val forKeepingOnlyRed: RGBAComponentTranslationTable = {
    RGBAComponentTranslationTable{index =>
      (index, 0, 0, index)
    }
  }

  /** */
  lazy val forKeepingOnlyRedAndGreen: RGBAComponentTranslationTable = {
    RGBAComponentTranslationTable{index =>
      (index, index, 0, index)
    }
  }

  /** */
  lazy val forKeepingOnlyGreen: RGBAComponentTranslationTable = {
    RGBAComponentTranslationTable{index =>
      (0, index, 0, index)
    }
  }

  /** */
  lazy val forKeepingOnlyGreenAndBlue: RGBAComponentTranslationTable = {
    RGBAComponentTranslationTable{index =>
      (0, index, index, index)
    }
  }

  /** */
  lazy val forKeepingOnlyBlue: RGBAComponentTranslationTable = {
    RGBAComponentTranslationTable{index =>
      (0, 0, index, index)
    }
  }

  /** */
  lazy val forKeepingOnlyRedAndBlue: RGBAComponentTranslationTable = {
    RGBAComponentTranslationTable{index =>
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
  def forPosterization(strengthAsPercentage: Int): RGBAComponentTranslationTable = {
    commonValidators.validatePercentage(strengthAsPercentage, Option("Strength"))

    val divisor = strengthAsPercentage + 1

    RGBAComponentTranslationTable{index =>
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
case class RGBAComponentTranslationTable private(
    table: Seq[Seq[Short]],
    private val colorValidator: ColorValidator)
    extends RGBAColorTranslator
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
