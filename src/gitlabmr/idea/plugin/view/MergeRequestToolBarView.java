package gitlabmr.idea.plugin.view;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBScrollPane;
import gitlabmr.idea.plugin.action.MergeRequestRefresher;

import javax.swing.*;
import java.awt.*;

public class MergeRequestToolBarView extends JPanel {
    public MergeRequestToolBarView(final Project project) {
        ActionGroup actionGroup = new DefaultActionGroup(new MergeRequestRefresher());
        ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar(ActionPlaces.STATUS_BAR_PLACE, actionGroup, false);

        this.setLayout(new BorderLayout());
        final JBScrollPane scroller = new JBScrollPane(toolbar.getComponent(), JBScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JBScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scroller, BorderLayout.CENTER);
    }
}
