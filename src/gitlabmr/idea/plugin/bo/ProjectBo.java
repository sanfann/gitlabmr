package gitlabmr.idea.plugin.bo;

import org.gitlab.api.models.GitlabProject;

import java.util.List;

public class ProjectBo {
    public final Integer id;
    public final String name;
    public final GitlabProject project;

    private List<MergeRequestBo> mergeRequestBos;

    private ProjectBo(Integer id, String name, GitlabProject project) {
        this.id = id;
        this.name = name;
        this.project = project;
    }

    public static ProjectBo fromGitlabProject(GitlabProject project) {
        return new ProjectBo(project.getId(), project.getNameWithNamespace(), project);
    }

    public List<MergeRequestBo> getMergeRequestBos() {
        return mergeRequestBos;
    }

    public void setMergeRequestBos(List<MergeRequestBo> mergeRequestBos) {
        this.mergeRequestBos = mergeRequestBos;
    }
}
