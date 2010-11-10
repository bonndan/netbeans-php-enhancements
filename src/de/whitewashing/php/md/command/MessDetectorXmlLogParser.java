/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.whitewashing.php.md.command;

import de.whitewashing.php.md.MessDetectionViolation;
import java.io.IOException;
import java.io.Reader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;

/**
 *
 * @author benny
 */
public class MessDetectorXmlLogParser {

    MessDetectorXmlLogResult parse(File fo)
    {
        if(fo == null || fo.exists() == false) {
            return createEmptyResult();
        }

        try {
            return parse(new InputStreamReader(new FileInputStream(fo)));
        } catch(FileNotFoundException e) {
            return createEmptyResult();
        }
    }

    MessDetectorXmlLogResult parse(Reader reader)
    {
        List<MessDetectionViolation> violations = new ArrayList<MessDetectionViolation>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document;
            document = builder.parse(new InputSource(reader));
            NodeList ndList = document.getElementsByTagName("violation");
            int lineNum = 0;
            for (int i = 0; i < ndList.getLength(); i++) {
                String message = ndList.item(i).getTextContent();
                NamedNodeMap nm = ndList.item(i).getAttributes();
                lineNum = Integer.parseInt(nm.getNamedItem("beginline").getTextContent()) - 1;
                String rule = nm.getNamedItem("rule").getTextContent();
                violations.add(new MessDetectionViolation(rule, message, lineNum));
            }
        } catch (IOException ex) {
            
        } catch (ParserConfigurationException ex) {
            
        } catch(SAXParseException ex) {
            
        } catch (SAXException ex) {
            
        }

        return new MessDetectorXmlLogResult(violations);
    }

    private MessDetectorXmlLogResult createEmptyResult()
    {
        List<MessDetectionViolation> violations = new ArrayList<MessDetectionViolation>();

        return new MessDetectorXmlLogResult(violations);
    }
}
