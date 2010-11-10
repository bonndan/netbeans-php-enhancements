/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.whitewashing.php.md.command;

import de.whitewashing.php.md.MessDetectionViolation;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daniel
 */
public class MessDetectorXmlLogResult
{

    static public MessDetectorXmlLogResult empty()
    {
        return new MessDetectorXmlLogResult(new ArrayList<MessDetectionViolation>());
    }
    
    private List<MessDetectionViolation> violations = null;

    public MessDetectorXmlLogResult(List<MessDetectionViolation> violations)
    {
        this.violations = violations;
    }

    /**
     * get the violations list
     * 
     * @return List
     */
    public List<MessDetectionViolation> getViolations()
    {
        return violations;
    }
}
