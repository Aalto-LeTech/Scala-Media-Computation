package aalto.smcl


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object Help {

  SMCL.performInitialization()


  /** */
  // @formatter:off
  val HelpMsgGeneral =
    s"""
      |Welcome to explore the ${SMCL.FullName}!
      |
      |A default bitmap can be created with Bitmap class as follows:
      |
      |  scala> Bitmap()
      |
      |Bitmap can also be given width and height:
      |
      |  scala> Bitmap(500, 400)
      |
      |What is more, it is possible to define bitmap's background color, like below:
      |
      |  scala> Bitmap(300, 300, PresetColors('lightBlue))
      |
      |
      |Happy coding!
    """.stripMargin
  // @formatter:on

  /**
   *
   *
   * @param terms
   */
  def onTopic(terms: Seq[String]) = {
    println(HelpMsgGeneral)
  }

}
