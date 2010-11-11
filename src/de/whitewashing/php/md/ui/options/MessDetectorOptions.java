/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.whitewashing.php.md.ui.options;

import de.whitewashing.php.md.command.MessDetector;
import de.whitewashing.php.md.command.MessDetectorBinary;
import java.util.ArrayList;
import java.util.List;
import org.openide.util.NbPreferences;

/**
 * Model class that holds all options for the MessDetector module.
 *
 * @author manu
 * @author daniel
 * @todo use priority
 */
public class MessDetectorOptions
{

    public static final String RULESETS = "phpmd.rulesets";
    public static final String PRIORITY = "phpmd.priority";
    public static final String SHELL_SCRIPT = "phpmd.shellScript";

    public static final String DEFAULT_RULESETS = "codesize,design,naming,unusedcode";
    public static final int    DEFAULT_PRIORITY = 3;
    private String rulesets;

    public MessDetectorOptions()
    {
        this(new String());
    }

    public MessDetectorOptions(String rulesets)
    {
        if(rulesets.length() == 0) {
            this.rulesets = DEFAULT_RULESETS;
        } else {
            this.rulesets = rulesets;
        }
    }

    /**
     * get the used rulesets
     * @return String
     */
    public String getRulesets()
    {
        return NbPreferences.forModule(MessDetector.class)
                .get(RULESETS, rulesets);
    }

    public void setRulesets(String rulesets)
    {
        NbPreferences.forModule(MessDetector.class)
                .put(RULESETS, rulesets);
    }

    public int getPriority()
    {
        return NbPreferences.forModule(MessDetector.class)
                .getInt(PRIORITY, DEFAULT_PRIORITY);
    }

    public void setPriority(int priority)
    {
        NbPreferences.forModule(MessDetector.class)
                .putInt(PRIORITY, priority);
    }

    public String getShellScript()
    {
        return NbPreferences.forModule(MessDetector.class)
                .get(SHELL_SCRIPT, this.findDefaultShellScript());
    }

    /**
     * set shell script
     * @param shellScript
     */
    public void setShellScript(String shellScript)
    {
        NbPreferences.forModule(MessDetector.class)
                .put(SHELL_SCRIPT, shellScript);
    }

    /**
     * get path of shell script
     * @return String
     */
    private String findDefaultShellScript()
    {
        return new MessDetectorBinary().getPath();
    }
}
