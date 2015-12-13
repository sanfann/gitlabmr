package gitlabmr.idea.plugin.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;
import gitlabmr.idea.plugin.view.MergeRequestListView;

public class SelfMergeRequestFilter extends ToggleAction {
    private MergeRequestListView listView;

    public SelfMergeRequestFilter() {
    }

    public SelfMergeRequestFilter(MergeRequestListView listView) {
        super("Assigned to me", "Filter merge request assigned to me", AllIcons.Actions.Filter_small);
        this.listView = listView;
    }

    @Override
    public boolean isSelected(AnActionEvent e) {
        return listView.isSelfFilterOn();
    }

    @Override
    public void setSelected(AnActionEvent e, boolean state) {
        listView.setSelfFilterOn(state);
        listView.refresh();
    }
}
