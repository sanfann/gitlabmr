package gitlabmr.idea.plugin.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class MergeRequestRefresher extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        System.out.println("refresh");
    }
}
