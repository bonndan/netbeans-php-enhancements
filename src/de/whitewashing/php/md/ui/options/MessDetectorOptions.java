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
 * Model class that holds all options for the CodeSniffer module.
 *
 * @author manu
 */
public class MessDetectorOptions {

    public static final String CODING_STANDARD = "phpcs.codingStandard";
    public static final String SHOW_WARNINGS = "phpcs.showWarnings";
    public static final String SHELL_SCRIPT = "phpcs.shellScript";
    
    public static final String DEFAULT_CODING_STANDARD = "Zend";
    public static final boolean DEFAULT_SHOW_WARNINGS = true;

    private List<String> codingStandards;

    public MessDetectorOptions() {
        this(new ArrayList<String>());
    }

    public MessDetectorOptions(List<String> codingStandards) {
        this.codingStandards = codingStandards;
    }

    public List<String> getCodingStandards() {
        return this.codingStandards;
    }

    public String getCodingStandard() {
        return NbPreferences.forModule(MessDetector.class).get(CODING_STANDARD, DEFAULT_CODING_STANDARD);
    }

    public void setCodingStandard(String codingStandard) {
        NbPreferences.forModule(MessDetector.class).put(CODING_STANDARD, codingStandard);
    }

    public boolean hasShowWarnings() {
        return NbPreferences.forModule(MessDetector.class).getBoolean(SHOW_WARNINGS, DEFAULT_SHOW_WARNINGS);
    }

    public void setShowWarnings(boolean showWarnings) {
        NbPreferences.forModule(MessDetector.class).putBoolean(SHOW_WARNINGS, showWarnings);
    }

    public String getShellScript() {
        return NbPreferences.forModule(MessDetector.class).get(SHELL_SCRIPT, this.findDefaultShellScript());
    }

    public void setShellScript(String shellScript) {
        NbPreferences.forModule(MessDetector.class).put(SHELL_SCRIPT, shellScript);
    }

    private String findDefaultShellScript() {
        return new MessDetectorBinary().getPath();
    }
}
