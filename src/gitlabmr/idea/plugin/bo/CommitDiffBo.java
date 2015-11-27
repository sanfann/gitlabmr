package gitlabmr.idea.plugin.bo;

import gitlabmr.idea.plugin.model.Gitlab;
import org.gitlab.api.models.GitlabCommitDiff;

import java.io.IOException;

public class CommitDiffBo {
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
}
