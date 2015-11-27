package gitlabmr.idea.plugin.view;

import com.intellij.diff.DiffContentFactory;
import com.intellij.diff.DiffManager;
import com.intellij.diff.contents.DiffContent;
import com.intellij.diff.contents.EmptyContent;
import com.intellij.diff.requests.SimpleDiffRequest;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.treeStructure.Tree;
import gitlabmr.idea.plugin.bo.CommitDiffBo;
import gitlabmr.idea.plugin.bo.MergeRequestBo;
import gitlabmr.idea.plugin.task.GetMergeRequestTask;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MergeRequestListView extends JPanel {
    public MergeRequestListView(final Project project) {
        final Tree tree = new Tree(new DefaultTreeModel(new DefaultMutableTreeNode()));
        tree.setRootVisible(false);
        tree.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    TreePath tp = tree.getPathForLocation(e.getX(), e.getY());
                    DefaultMutableTreeNode parent = (DefaultMutableTreeNode) tp.getLastPathComponent();
                    Object component = parent.getUserObject();

                    if (component instanceof MergeRequestBo) {
                        MergeRequestBo mr = (MergeRequestBo) component;
                        for (CommitDiffBo diff : mr.commitDiffBos) {
                            DefaultMutableTreeNode node = new DefaultMutableTreeNode(diff);
                            parent.add(node);
                        }
                        tree.treeDidChange();
                    } else if (component instanceof CommitDiffBo) {
                        MergeRequestBo mr = (MergeRequestBo)((DefaultMutableTreeNode) parent.getParent()).getUserObject();
                        CommitDiffBo diff = (CommitDiffBo) component;

                        DiffContent before = null;
                        DiffContent after = null;
                        if (diff.isFileAdded) {
                            before = new EmptyContent();
                            after = DiffContentFactory.getInstance().create(new String(diff.getNewFileContent(mr.targetBranchId)));
                        } else if (diff.isFileDeleted) {
                            before = DiffContentFactory.getInstance().create(new String(diff.getOldFileContent(mr.sourceBranchId)));
                            after = new EmptyContent();
                        } else {
                            before = DiffContentFactory.getInstance().create(new String(diff.getOldFileContent(mr.sourceBranchId)));
                            after = DiffContentFactory.getInstance().create(new String(diff.getNewFileContent(mr.targetBranchId)));
                        }

                        SimpleDiffRequest request = new SimpleDiffRequest("compare", before, after, mr.sourceBranchId + "(" + mr.sourceBranch + ")", mr.targetBranchId + "(" + mr.targetBranch + ")");
                        DiffManager.getInstance().showDiff(project, request);
                    }
                }
            }
        });

        ProgressManager.getInstance().run(new GetMergeRequestTask(project, tree));

        this.setLayout(new BorderLayout());
        final JBScrollPane scroller = new JBScrollPane(tree, JBScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JBScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scroller, BorderLayout.CENTER);

    }
}
