/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.whitewashing.php.md.command;

import de.whitewashing.php.md.command.MessDetectorXmlLogParser;
import de.whitewashing.php.md.command.MessDetectorXmlLogResult;
import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author benny
 */
public class MessDetectorXmlLogParserTest {

    private String testFile = "/home/daniel/workspace/CodeSniffer/test/unit/src/de/whitewashing/php/md/command/testresult1.xml";
    
    public MessDetectorXmlLogParserTest() {
    }

    /**
     * Test of parse method, of class CodeSnifferXmlLogParser.
     */
    @Test
    public void testParse_AlwaysReturnsValidParseResult() {
        File file = null;
        MessDetectorXmlLogParser instance = new MessDetectorXmlLogParser();
        MessDetectorXmlLogResult result = instance.parse(file);

        assertNotNull(result);
        assertNotNull(result.getViolations());
    }

    @Test
    public void testParse_Testresult1_ViolationCountIs13()
    {
        File f = new File(testFile);

        MessDetectorXmlLogParser instance = new MessDetectorXmlLogParser();
        MessDetectorXmlLogResult result = instance.parse(f);

        assertEquals(9, result.getViolations().size());
    }
}
