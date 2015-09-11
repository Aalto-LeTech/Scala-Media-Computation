package aalto.smcl.infrastructure;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;




/**
 * Acts as a metadata-containing marker interface for packages and their initializer classes.
 *
 * @author Aleksi Lukkarinen
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface InitializablePackage {

    /** Full names of packages the contents of which this package depends on, */
    String[] dependsOnPackages();

}
