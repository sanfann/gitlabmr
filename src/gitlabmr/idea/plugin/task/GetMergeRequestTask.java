package gitlabmr.idea.plugin.task;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.ui.treeStructure.Tree;
import gitlabmr.idea.plugin.bo.MergeRequestBo;
import gitlabmr.idea.plugin.bo.ProjectBo;
import gitlabmr.idea.plugin.model.Gitlab;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.io.IOException;
import java.util.List;

public class GetMergeRequestTask extends Task.Backgroundable {
    private final Tree tree;
    private final boolean isSelfFilterOn;

    public GetMergeRequestTask(@Nullable Project project, Tree tree, boolean isSelfFilterOn) {
        super(project, "title");
        this.tree = tree;
        this.isSelfFilterOn = isSelfFilterOn;
    }

    private static TreeModel convertToTreeModel(List<ProjectBo> projects) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        if (!projects.isEmpty()) {
            for (ProjectBo project : projects) {
                DefaultMutableTreeNode parent = new DefaultMutableTreeNode(project.name);
                for (MergeRequestBo mr : project.getMergeRequestBos()) {
                    DefaultMutableTreeNode node = new DefaultMutableTreeNode(mr);
                    parent.add(node);
                }
                if (!parent.isLeaf()) {
                    root.add(parent);
                }
            }
        }
        return new DefaultTreeModel(root);
    }

    @Override
    public void run(@NotNull ProgressIndicator indicator) {
        indicator.setText("Loading merge requests");
        indicator.setFraction(0.0);

        try {
            final List<ProjectBo> projects;
            if (isSelfFilterOn) {
                projects = Gitlab.getProjectWithSelfMergeRequests();
            } else {
                projects = Gitlab.getProjectWithMergeRequests();
            }
            indicator.setFraction(0.8);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    tree.setModel(convertToTreeModel(projects));
                    tree.treeDidChange();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        indicator.setFraction(1.0);
    }
}
