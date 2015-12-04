package gitlabmr.idea.plugin.controller;

import com.intellij.openapi.project.Project;
import gitlabmr.idea.plugin.view.MergeRequestListView;
import gitlabmr.idea.plugin.view.MergeRequestToolBarView;

import javax.swing.*;
import java.awt.*;

public class MergeRequestController {
    private Project project;

    public MergeRequestController(Project project) {
        this.project = project;
    }

    public JPanel getListView() {
        MergeRequestListView listView = new MergeRequestListView(project);
        MergeRequestToolBarView toolBar = new MergeRequestToolBarView(project);

        listView.add(toolBar, BorderLayout.WEST);
        return listView;
    }
}


