package aalto.smcl.infrastructure.awt


import aalto.smcl.infrastructure.{DefaultPlatformResourceFactory, JvmUniqueIdProvider}




/**
  *
  *
  * @author Aleksi Lukkarinen
  */
object Init {

  // Initialize platform resource factory
  val uniqueIdProvider = new JvmUniqueIdProvider()
  val factory = new DefaultJvmAwtPlatformResourceFactory(uniqueIdProvider)
  DefaultPlatformResourceFactory.setImplementation(factory)

}
