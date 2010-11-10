/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.whitewashing.php.md.command;

import de.whitewashing.php.md.MessDetectionViolation;
import org.openide.filesystems.FileAttributeEvent;
import org.openide.filesystems.FileChangeListener;

/**
 *
 * @author benny
 */
import org.openide.filesystems.FileEvent;
import org.openide.filesystems.FileRenameEvent;

/**
 *
 * @author benny
 */
public class MessDetectorFileListener implements FileChangeListener
{
    private MessDetectorXmlLogResult rs = null;

    void setLogResult(MessDetectorXmlLogResult rs)
    {
        this.rs = rs;
    }

    public void fileFolderCreated(FileEvent fileEvent) {
        
    }

    public void fileDataCreated(FileEvent fileEvent) {
        
    }

    public void fileChanged(FileEvent fileEvent) {
        for(MessDetectionViolation e: rs.getViolations()) {
            e.detach();
        }
        fileEvent.getFile().removeFileChangeListener(this);
    }

    public void fileDeleted(FileEvent fileEvent) {
        
    }

    public void fileRenamed(FileRenameEvent fileEvent) {
        
    }

    public void fileAttributeChanged(FileAttributeEvent fileEvent) {
        
    }

}
