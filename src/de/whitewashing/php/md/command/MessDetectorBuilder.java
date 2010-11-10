/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.whitewashing.php.md.command;

import de.whitewashing.php.md.ui.options.MessDetectorOptions;
import java.util.ArrayList;

/**
 * Simple factory/singleton that creates a unique MessDetector instance based
 * on the user's preferences and the underlying system settings.
 *
 * @author manu
 */
public class MessDetectorBuilder
{

    private static MessDetector messDetector = null;

    public static MessDetector createOrReturn()
    {
        if (messDetector == null) {
            return create();
        }
        return messDetector;
    }

    public static MessDetector create()
    {
        MessDetectorOptions options = new MessDetectorOptions();

        return (messDetector = new MessDetector(options.getShellScript(), options.getCodingStandard()));
    }
}
