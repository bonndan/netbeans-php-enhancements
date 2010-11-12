/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.whitewashing.php.md.command;

import de.whitewashing.php.md.ui.options.MessDetectorOptions;
import java.util.concurrent.ExecutionException;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import org.netbeans.api.extexecution.ExecutionDescriptor;
import org.netbeans.api.extexecution.ExecutionService;
import org.netbeans.api.extexecution.ExternalProcessBuilder;
import org.openide.cookies.LineCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.util.Exceptions;
import org.openide.loaders.DataObject;
import org.openide.text.Line;

/**
 *
 * @author benny
 */
public class MessDetector
{
    /**
     * report type must be xml
     */
    public static final String PARAM_REPORT = "xml";
    /**
     * shell script to execute
     */
    private String shellScript;
    /**
     * by default all rulesets are used
     */
    private String rulesets = "codesize,design,naming,unusedcode";

    /**
     * Constructor
     * 
     * @param shellScript
     * @param rulesets (unused)
     * @todo  use rulesets param
     */
    public MessDetector(String shellScript, String rulesets)
    {
        this.shellScript = shellScript;
        //this.rulesets = rulesets;
    }

    public boolean isEnabled()
    {
        return this.shellScript != null && new File(this.shellScript).exists();
    }

    public String getAvailableRulesets()
    {
        return MessDetectorOptions.DEFAULT_RULESETS;
    }

    public MessDetectorXmlLogResult execute(FileObject fo)
    {
        return execute(fo, false);
    }

    public MessDetectorXmlLogResult execute(FileObject fo, boolean annotateLines)
    {
        final File parent = FileUtil.toFile(fo.getParent());

        if (parent == null || this.isEnabled() == false) {
            return MessDetectorXmlLogResult.empty();
        }

        ExternalProcessBuilder externalProcessBuilder;

        externalProcessBuilder = new ExternalProcessBuilder(this.shellScript)
                .workingDirectory(parent)
                .addArgument(fo.getNameExt())
                .addArgument(PARAM_REPORT)
                .addArgument(this.rulesets);


        MessDetectorXmlLogParser parser = new MessDetectorXmlLogParser();
        MessDetectorXmlLogResult rs = parser.parse(new ProcessExecutor().execute(externalProcessBuilder));

        if (annotateLines) {
            annotateWithCodingStandardHints(fo, rs);
        }
        return rs;
    }

    private void annotateWithCodingStandardHints(FileObject fo, MessDetectorXmlLogResult rs)
    {
        MessDetectorFileListener l = new MessDetectorFileListener();
        l.setLogResult(rs);
        fo.addFileChangeListener(l);

        try {
            DataObject d = DataObject.find(fo);
            LineCookie cookie = d.getCookie(LineCookie.class);

            Line.Set lineSet = null;
            Line line = null;
            for (int i = 0; i < rs.getViolations().size(); i++) {
                lineSet = cookie.getLineSet();
                line = lineSet.getOriginal(rs.getViolations().get(i).getLineNum());
                rs.getViolations().get(i).attach(line);
            }
        } catch (DataObjectNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    /**
     * Small utility class that is used to execute an external MessDetector process.
     */
    class ProcessExecutor
    {

        /**
         * Executes the given process and returns a reader instance with the
         * STDOUT result of the process.
         *
         * @param builder A configured process builder instance.
         *
         * @return Reader
         */
        public Reader execute(ExternalProcessBuilder builder)
        {
            MessDetectorOutput output = new MessDetectorOutput();

            ExecutionDescriptor descriptor = new ExecutionDescriptor().frontWindow(false).controllable(false).outProcessorFactory(output);

            ExecutionService service = ExecutionService.newService(builder, descriptor, "PHP Coding Standards");
            Future<Integer> task = service.run();
            try {
                task.get();
            } catch (InterruptedException ex) {
                Exceptions.printStackTrace(ex);
            } catch (ExecutionException ex) {
                Exceptions.printStackTrace(ex);
            }

            return output.getReader();
        }
    }
}
