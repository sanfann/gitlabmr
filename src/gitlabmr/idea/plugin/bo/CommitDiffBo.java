package gitlabmr.idea.plugin.bo;

import com.intellij.diff.DiffContentFactory;
import com.intellij.diff.DiffManager;
import com.intellij.diff.contents.DiffContent;
import com.intellij.diff.contents.EmptyContent;
import com.intellij.diff.requests.SimpleDiffRequest;
import com.intellij.openapi.project.Project;
import com.intellij.ui.treeStructure.Tree;
import gitlabmr.idea.plugin.model.Gitlab;
import org.gitlab.api.models.GitlabCommitDiff;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.IOException;

public class CommitDiffBo implements ClickableNode {
    public final boolean isFileAdded;
    public final boolean isFileDeleted;
    public final boolean isFileRenamed;
    public final String newFilePath;
    public final String oldFilePath;
    public final ProjectBo project;

    public CommitDiffBo(boolean isFileAdded, boolean isFileDeleted, boolean isFileRenamed, String newFilePath, String oldFilePath, ProjectBo project) {
        this.isFileAdded = isFileAdded;
        this.isFileDeleted = isFileDeleted;
        this.isFileRenamed = isFileRenamed;
        this.newFilePath = newFilePath;
        this.oldFilePath = oldFilePath;
        this.project = project;
    }

    public static CommitDiffBo fromGitlabCommitDiff(GitlabCommitDiff diff, ProjectBo project) {
        return new CommitDiffBo(diff.getNewFile(), diff.getDeletedFile(), diff.getRenamedFile(), diff.getNewPath(), diff.getOldPath(), project);
    }

    public byte[] getNewFileContent(String commitId) {
        byte[] content = null;
        try {
            content = Gitlab.getFileContent(project.project, commitId, newFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    public byte[] getOldFileContent(String commitId) {
        byte[] content = null;
        try {
            content = Gitlab.getFileContent(project.project, commitId, oldFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    @Override
    public String toString() {
        return oldFilePath + " ->" + newFilePath;
    }

    @Override
    public void doubleClick(Project project, Tree tree, DefaultMutableTreeNode parent) {
        MergeRequestBo mr = (MergeRequestBo)((DefaultMutableTreeNode) parent.getParent()).getUserObject();

        DiffContent before;
        DiffContent after;
        if (isFileAdded) {
            before = new EmptyContent();
            after = DiffContentFactory.getInstance().create(new String(getNewFileContent(mr.targetBranchId)));
        } else if (isFileDeleted) {
            before = DiffContentFactory.getInstance().create(new String(getOldFileContent(mr.sourceBranchId)));
            after = new EmptyContent();
        } else {
            before = DiffContentFactory.getInstance().create(new String(getOldFileContent(mr.sourceBranchId)));
            after = DiffContentFactory.getInstance().create(new String(getNewFileContent(mr.targetBranchId)));
        }

        String beforeTitle = mr.sourceBranchId + "(" + mr.sourceBranch + ")";
        String afterTitle = mr.targetBranchId + "(" + mr.targetBranch + ")";
        SimpleDiffRequest request = new SimpleDiffRequest("compare", before, after, beforeTitle, afterTitle);
        DiffManager.getInstance().showDiff(project, request);
    }
}
