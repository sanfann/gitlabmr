package gitlabmr.idea.plugin.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import gitlabmr.idea.plugin.view.MergeRequestListView;

public class MergeRequestRefresher extends AnAction {
    private MergeRequestListView listView;

    public MergeRequestRefresher() {
    }

    public MergeRequestRefresher(MergeRequestListView listView) {
        super("Refresh", "Refresh merge request list", AllIcons.Actions.Refresh);
        this.listView = listView;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        listView.refresh();
    }
}
