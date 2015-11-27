package gitlabmr.idea.plugin.controller;

import com.intellij.openapi.project.Project;
import gitlabmr.idea.plugin.view.MergeRequestListView;

public class MergeRequestController {
    private Project project;

    public MergeRequestController(Project project) {
        this.project = project;
    }

    public MergeRequestListView getListView() {
        MergeRequestListView view = new MergeRequestListView(project);
        return view;
    }
}


