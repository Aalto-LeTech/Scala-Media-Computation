package aalto.smcl.infrastructure.awt


import aalto.smcl.infrastructure.DefaultPlatformResourceFactory




/**
  *
  *
  * @author Aleksi Lukkarinen
  */
object Init {

  // Initialize platform resource factory
  DefaultPlatformResourceFactory.setImplementation(new DefaultJvmAwtPlatformResourceFactory())

}
