package gitlabmr.idea.plugin.view;

import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.treeStructure.Tree;
import gitlabmr.idea.plugin.bo.ClickableNode;
import gitlabmr.idea.plugin.task.GetMergeRequestTask;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MergeRequestListView extends JPanel {
    private Project project;
    private Tree tree;
    private boolean isSelfFilterOn = true;

    public MergeRequestListView(final Project project) {
        this.project = project;
        tree = new Tree(new DefaultTreeModel(new DefaultMutableTreeNode()));
        tree.setRootVisible(false);
        tree.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    TreePath tp = tree.getPathForLocation(e.getX(), e.getY());
                    DefaultMutableTreeNode parent = (DefaultMutableTreeNode) tp.getLastPathComponent();
                    Object component = parent.getUserObject();

                    if (component instanceof ClickableNode) {
                        ((ClickableNode) component).doubleClick(project, tree, parent);
                    }
                }
            }
        });

        this.setLayout(new BorderLayout());
        final JBScrollPane scroller = new JBScrollPane(tree, JBScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JBScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scroller, BorderLayout.CENTER);

        refresh();
    }

    public void refresh() {
        ProgressManager.getInstance().run(new GetMergeRequestTask(project, tree, isSelfFilterOn));
    }

    public void setSelfFilterOn(boolean selfFilterOn) {
        isSelfFilterOn = selfFilterOn;
    }

    public boolean isSelfFilterOn() {
        return isSelfFilterOn;
    }
}
