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

package smcl.pictures


import scala.language.implicitConversions

import smcl.colors.rgb._
import smcl.modeling.d2
import smcl.modeling.d2.RatioAnchor
import smcl.modeling.d2.simplified.Anchor.{Center, TopLeft}
import smcl.modeling.d2.simplified.{Anchor, Bounds, Pos}
import smcl.pictures.fullfeatured.{Image, Line}


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object simplified {

  // XXX mietittävä defaultparametrien vaikutus code-completioniin

  /**
   *
   *
   * @param width
   * @param height
   * @param color
   *
   * @return
   */
  def emptyCanvas(
      width: Double,
      height: Double,
      color: Color = White): Pic = {

    rectangle(width, height, color, TopLeft)
  }

  /**
   *
   *
   * @param bounds
   * @param color
   *
   * @return
   */
  def rectangle(
      bounds: Bounds,
      color: Color): Pic = {

    rectangle(
      bounds.width,
      bounds.height,
      color,
      TopLeft)
  }

  /**
   *
   *
   * @param width
   * @param height
   * @param color
   *
   * @return
   */
  def rectangle(
      width: Double,
      height: Double,
      color: Color): Pic = {

    rectangle(
      width.floor.toInt,
      height.floor.toInt,
      color,
      Center)
  }

  /**
   *
   *
   * @param width
   * @param height
   * @param color
   * @param anchor
   *
   * @return
   */
  def rectangle(
      width: Double,
      height: Double,
      color: Color,
      anchor: Anchor = Center): Pic = {

    //Pic(fullfeatured.rectangle(width, height, color), anchor)
    Pic()
  }

  /**
   *
   *
   * @param side
   * @param color
   * @param anchor
   *
   * @return
   */
  def square(
      side: Double,
      color: Color,
      anchor: Anchor = Center): Pic = {

    //Pic(fullfeatured.rectangle(side, side, color), anchor) // XXX pwa
    Pic()
  }

  /**
   *
   *
   * @param diameter
   * @param color
   * @param backgroundColor
   * @param anchor
   *
   * @return
   */
  def circle(
      diameter: Double,
      color: Color,
      backgroundColor: Color = Transparent,
      anchor: Anchor = Center): Pic = {

    //Pic(fullfeatured.circle(diameter, color, backgroundColor), anchor) // XXX pwa
    Pic()
  }

  /**
   *
   *
   * @param width
   * @param color
   *
   * @return
   */
  def star(
      width: Double,
      color: Color): Pic = {

    star(width, color, Transparent, Center)
  }

  /**
   *
   *
   * @param width
   * @param color
   * @param backgroundColor
   * @param anchor
   *
   * @return
   */
  def star(
      width: Double,
      color: Color,
      backgroundColor: Color = Transparent,
      anchor: Anchor = Center): Pic = {

    //Pic(fullfeatured.circle(width, color, backgroundColor), anchor) // XXX circle for now
    Pic()
  }

  /**
   *
   *
   * @param width
   * @param height
   * @param color
   * @param backgroundColor
   * @param anchor
   *
   * @return
   */
  def triangle(
      width: Double,
      height: Double,
      color: Color,
      backgroundColor: Color = Transparent,
      anchor: Anchor = Center): Pic = {

    //Pic(fullfeatured.circle(width, color, backgroundColor), anchor) // XXX circle for now
    Pic()
  }

  /**
   *
   *
   * @param width
   * @param height
   * @param color
   * @param anchor
   *
   * @return
   */
  def ellipse(
      width: Double,
      height: Double,
      color: Color,
      anchor: Anchor = Center): Pic = {

    //Pic(fullfeatured.ellipse(width, height, color), anchor) // XXX pwa
    Pic()
  }

  /**
   *
   *
   * @param from
   * @param to
   * @param color
   * @param backgroundColor
   *
   * @return
   */
  def line(
      from: Pos,
      to: Pos,
      color: Color,
      backgroundColor: Color = Transparent): Pic = {

    val anchor: RatioAnchor =
      (from.x < to.x, from.y < to.y) match {
        case (true, true)   => d2.Anchor.TopLeft
        case (true, false)  => d2.Anchor.BottomLeft
        case (false, true)  => d2.Anchor.TopRight
        case (false, false) => d2.Anchor.BottomRight
      }

    // A simplified anchor will always exist for the anchor presets above
    val simplifiedAnchor = anchor.toSimplifiedAnchor.get

    //Pic(fullfeatured.line(from.xInt, from.yInt, to.xInt, to.yInt, color, backgroundColor).flipVertically(), anchor) // XXX pwa
    Pic(
      Some(Image(Seq(Line(from.x, from.y, to.x, to.y, color)), anchor)),
      simplifiedAnchor,
      viewport = null)
  }

}
