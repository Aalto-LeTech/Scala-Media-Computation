package aalto.smcl.images


/**
 * Tests written to explore the properties of the frameworks used.
 *
 * @author Aleksi Lukkarinen
 */
class FrameworkTests extends ImageSpecBase {

  "java.awt.Color must preserve the original color given for its constructor" in {
    val testColors = Table("c", 0x8F9EADBC, TEST_PIXEL_INT, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    forAll(testColors) {c =>
      val color = new java.awt.Color(c, true)

      color.getRGB shouldEqual c
    }
  }

}
