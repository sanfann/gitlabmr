package gitlabmr.idea.plugin.bo;

import com.intellij.openapi.project.Project;
import com.intellij.ui.treeStructure.Tree;

import javax.swing.tree.DefaultMutableTreeNode;

public interface ClickableNode {
    void doubleClick(Project project, Tree tree, DefaultMutableTreeNode parent);
}
