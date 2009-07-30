/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.whitewashing.php.cs;

import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.openide.util.Exceptions;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;

/**
 *
 * @author benny
 */
class CodeSnifferXmlLogParser {
    
    CodeSnifferXmlLogResult parse(StringBuilder sb)
    {
        List<CodingStandardWarning> csWarnings = new ArrayList<CodingStandardWarning>();
        List<CodingStandardError> csErrors = new ArrayList<CodingStandardError>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {

            InputSource is = new InputSource(
                new CharArrayReader(sb.toString().toCharArray())
            );

            builder = factory.newDocumentBuilder();
            Document document;
            document = builder.parse(is);
            NodeList ndList = document.getElementsByTagName("warning");
            int lineNum = 0;
            for (int i = 0; i < ndList.getLength(); i++) {
                String message = ndList.item(i).getTextContent();
                NamedNodeMap nm = ndList.item(i).getAttributes();
                lineNum = Integer.parseInt(nm.getNamedItem("line").getTextContent()) - 1;
                csWarnings.add(new CodingStandardWarning(message, lineNum));
            }
            ndList = document.getElementsByTagName("error");
            for (int i = 0; i < ndList.getLength(); i++) {
                String message = ndList.item(i).getTextContent();
                NamedNodeMap nm = ndList.item(i).getAttributes();
                lineNum = Integer.parseInt(nm.getNamedItem("line").getTextContent()) - 1;
                csErrors.add(new CodingStandardError(message, lineNum));
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (ParserConfigurationException ex) {
            Exceptions.printStackTrace(ex);
        } catch (SAXException ex) {
            Exceptions.printStackTrace(ex);
        }

        return new CodeSnifferXmlLogResult(csErrors, csWarnings);
    }
}