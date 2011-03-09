/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.whitewashing.php.md;

import de.whitewashing.php.md.command.MessDetectorBuilder;
import de.whitewashing.php.md.command.MessDetector;
import javax.swing.JMenuItem;
import org.openide.cookies.EditorCookie;
import org.openide.nodes.Node;
import org.openide.loaders.DataObject;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;
import org.openide.filesystems.FileObject;

public final class MessDetectorAction extends CookieAction {

    private MessDetector messDetector = MessDetectorBuilder.createOrReturn();

    protected void performAction(Node[] activatedNodes) {
        if(activatedNodes.length != 1) {
            return;
        }

        FileObject fo = getFileObject(activatedNodes[0]);
        this.messDetector.execute(fo, true);
    }

    /**
     * Copied from org.netbeans.modules.php.project.ui.actions.support.CommandUtils
     * 
     * @param node
     * @return
     */
    private FileObject getFileObject(Node node)
    {
        assert node != null;

        FileObject fileObj = node.getLookup().lookup(FileObject.class);
        if (fileObj != null && fileObj.isValid()) {
            return fileObj;
        }
        DataObject dataObj = node.getCookie(DataObject.class);
        if (dataObj == null) {
            return null;
        }
        fileObj = dataObj.getPrimaryFile();
        if (fileObj != null && fileObj.isValid()) {
            return fileObj;
        }
        return null;
    }

    protected int mode() {
        return CookieAction.MODE_EXACTLY_ONE;
    }

    public String getName() {
        return NbBundle.getMessage(
            MessDetectorAction.class,
            "CTL_MessDetectorAction"
        );
    }

    protected Class[] cookieClasses() {
        return new Class[]{EditorCookie.class};
    }

    @Override
    protected void initialize() {
        super.initialize();
        // see org.openide.util.actions.SystemAction.iconResource() Javadoc for more details
        putValue("noIconInMenu", Boolean.TRUE);
    }

    @Override
    public JMenuItem getPopupPresenter()
    {
        return this.setEnabledForExistingBinary(super.getPopupPresenter());
    }

    @Override
    public JMenuItem getMenuPresenter()
    {
        return this.setEnabledForExistingBinary(super.getMenuPresenter());
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return true;
    }

    protected JMenuItem setEnabledForExistingBinary(JMenuItem item)
    {
        item.setEnabled(this.messDetector.isEnabled());
        return item;
    }
}

