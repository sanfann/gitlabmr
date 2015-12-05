package gitlabmr.idea.plugin.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class MergeRequestRefresher extends AnAction {
    public MergeRequestRefresher() {
        super("Refresh", "Refresh merge request list", AllIcons.Actions.Refresh);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        System.out.println("refresh");
    }
}
