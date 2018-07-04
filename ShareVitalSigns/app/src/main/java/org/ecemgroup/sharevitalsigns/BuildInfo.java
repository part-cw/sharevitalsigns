/**
 * @author Walter Karlen
 *
 */
package org.ecemgroup.sharevitalsigns;


/**
 * Static container of information about this library.
 */
public final class BuildInfo {

    /**
     * The current version of this library. Values are of the form
     * "major.minor.micro[-suffix]". A suffix of "-pre" indicates a pre-release
     * of the version preceeding it.
     */
    public static final String VERSION = "0.1.0";

    private BuildInfo() {
        throw new IllegalStateException("Non-instantiable class.");
    }

}