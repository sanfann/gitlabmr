package gitlabmr.idea.plugin.bo;

import com.intellij.openapi.project.Project;
import com.intellij.ui.treeStructure.Tree;
import gitlabmr.idea.plugin.model.Gitlab;
import org.gitlab.api.models.GitlabMergeRequest;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.IOException;
import java.util.List;

public class MergeRequestBo implements ClickableNode {
    public final ProjectBo projectBo;
    public final String name;
    public final String sourceBranch;
    public final String sourceBranchId;
    public final String targetBranch;
    public final String targetBranchId;
    public final List<CommitDiffBo> commitDiffBos;

    private MergeRequestBo(ProjectBo projectBo,
                           String name,
                           String sourceBranch,
                           String sourceBranchId,
                           String targetBranch,
                           String targetBranchId,
                           List<CommitDiffBo> commitDiffBos) {
        this.projectBo = projectBo;
        this.name = name;
        this.sourceBranchId = sourceBranchId;
        this.sourceBranch = sourceBranch;
        this.targetBranchId = targetBranchId;
        this.targetBranch = targetBranch;
        this.commitDiffBos = commitDiffBos;
    }

    public static MergeRequestBo fromGitlabMergeRequest(GitlabMergeRequest mr, ProjectBo projectBo, List<CommitDiffBo> diffs) {
        String sourceCommitId = null;
        String targetCommitId = null;
        try {
            sourceCommitId = Gitlab.getBranchCommitId(projectBo, mr.getSourceBranch());
            targetCommitId = Gitlab.getBranchCommitId(projectBo, mr.getTargetBranch());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new MergeRequestBo(projectBo, mr.getTitle(), mr.getSourceBranch(), sourceCommitId, mr.getTargetBranch(), targetCommitId, diffs);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void doubleClick(Project project, Tree tree, DefaultMutableTreeNode parent) {
        for (CommitDiffBo diff : this.commitDiffBos) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(diff);
            parent.add(node);
        }
        tree.treeDidChange();
    }
}
