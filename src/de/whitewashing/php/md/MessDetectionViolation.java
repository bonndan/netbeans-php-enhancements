/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.whitewashing.php.md;

import org.openide.text.Annotation;

/**
 *
 * @author daniel
 */
public class MessDetectionViolation extends Annotation
{

    private String violationMessage = null;
    private int lineNum = 0;
    private String rule = null;

    /**
     * Constructor
     * 
     * @param rule
     * @param msg
     * @param lineNum
     */
    public MessDetectionViolation(String rule, String msg, int lineNum)
    {
        this.rule = rule;
        this.violationMessage = msg;
        this.lineNum = lineNum;
    }

    /**
     * get the line number
     * @return int
     */
    public int getLineNum()
    {
        return lineNum;
    }

    public String getRule()
    {
        return rule;
    }

    public String getShortDescription()
    {
        return violationMessage;
    }

    /**
     * get annotation type
     * @return String
     */
    public String getAnnotationType()
    {
        return "de-whitewashing-php-md-annotation-error";
    }
}
