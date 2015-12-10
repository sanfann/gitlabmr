package gitlabmr.idea.plugin.view;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBScrollPane;
import gitlabmr.idea.plugin.action.MergeRequestRefresher;
import gitlabmr.idea.plugin.action.SelfMergeRequestFilter;

import javax.swing.*;
import java.awt.*;

public class MergeRequestListWithToolBarView extends JPanel {
    public MergeRequestListWithToolBarView(final Project project, MergeRequestListView listView) {
        ActionGroup actionGroup = new DefaultActionGroup(new MergeRequestRefresher(listView), new SelfMergeRequestFilter(listView));
        ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar(ActionPlaces.STATUS_BAR_PLACE, actionGroup, false);

        this.setLayout(new BorderLayout());
        final JBScrollPane scroller = new JBScrollPane(toolbar.getComponent(), JBScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JBScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(listView, BorderLayout.CENTER);
        this.add(scroller, BorderLayout.WEST);
    }
}
