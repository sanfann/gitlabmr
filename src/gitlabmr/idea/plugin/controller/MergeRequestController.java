package gitlabmr.idea.plugin.controller;

import com.intellij.openapi.project.Project;
import gitlabmr.idea.plugin.view.MergeRequestListView;
import gitlabmr.idea.plugin.view.MergeRequestListWithToolBarView;

import javax.swing.*;

public class MergeRequestController {
    private Project project;

    public MergeRequestController(Project project) {
        this.project = project;
    }

    public JPanel getListView() {
        MergeRequestListView listView = new MergeRequestListView(project);
        MergeRequestListWithToolBarView listWithToolBarView = new MergeRequestListWithToolBarView(project, listView);

        return listWithToolBarView;
    }
}


