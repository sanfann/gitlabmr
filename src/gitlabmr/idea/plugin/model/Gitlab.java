package gitlabmr.idea.plugin.model;

import gitlabmr.idea.plugin.bo.CommitDiffBo;
import gitlabmr.idea.plugin.bo.MergeRequestBo;
import gitlabmr.idea.plugin.bo.ProjectBo;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabCommitDiff;
import org.gitlab.api.models.GitlabMergeRequest;
import org.gitlab.api.models.GitlabProject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Gitlab {
    final static private GitlabAPI api = GitlabAPI.connect("http://192.168.100.5:7777/", "EnszYZHvqnpmyiAGDUa1");

    public static byte[] getFileContent(GitlabProject project, String commitId, String filePath) throws IOException {
        return api.getRawFileContent(project, commitId, filePath);
    }

    private static List<ProjectBo> getProjects() throws IOException {
        LinkedList<ProjectBo> projectBos = new LinkedList<ProjectBo>();

        for (GitlabProject project : api.getProjects()) {
            projectBos.add(ProjectBo.fromGitlabProject(project));
        }

        return projectBos;
    }

    public static List<ProjectBo> getProjectWithMergeRequests() throws IOException {
        List<ProjectBo> projectBos = getProjects();

        for (ProjectBo projectBo : projectBos) {
            LinkedList<MergeRequestBo> mergeRequestBos = new LinkedList<MergeRequestBo>();
            for (GitlabMergeRequest mr : api.getOpenMergeRequests(projectBo.id)) {
                mergeRequestBos.add(getMergeRequestBoWithChanges(mr, projectBo));
            }
            projectBo.setMergeRequestBos(mergeRequestBos);
        }

        return projectBos;
    }

    public static List<ProjectBo> getProjectWithSelfMergeRequests() throws IOException {
        List<ProjectBo> projectBos = getProjects();

        Integer userId = api.getUser().getId();
        for (ProjectBo projectBo : projectBos) {
            LinkedList<MergeRequestBo> mergeRequestBos = new LinkedList<MergeRequestBo>();
            for (GitlabMergeRequest mr : api.getOpenMergeRequests(projectBo.id)) {
                if (userId.equals(mr.getAssignee().getId())) {
                    mergeRequestBos.add(getMergeRequestBoWithChanges(mr, projectBo));
                }
            }
            projectBo.setMergeRequestBos(mergeRequestBos);
        }

        return projectBos;
    }

    private static MergeRequestBo getMergeRequestBoWithChanges(GitlabMergeRequest mergeRequest, ProjectBo projectBo) throws IOException {
        GitlabMergeRequest mr = api.getMergeRequestChanges(mergeRequest.getProjectId(), mergeRequest.getId());

        List<CommitDiffBo> diffs = new LinkedList<CommitDiffBo>();
        for (GitlabCommitDiff diff : mr.getChanges()) {
            diffs.add(CommitDiffBo.fromGitlabCommitDiff(diff, projectBo));
        }

        return MergeRequestBo.fromGitlabMergeRequest(mr, projectBo, diffs);
    }

    public static String getBranchCommitId(ProjectBo projectBo, String branchName) throws IOException {
        return api.getBranch(projectBo.project, branchName).getCommit().getId();
    }
}
