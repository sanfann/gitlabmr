package gitlabmr.idea.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import gitlabmr.idea.plugin.controller.MergeRequestController;

public class MergeRequestWindow implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        MergeRequestController controller = new MergeRequestController(project);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(controller.getListView(), "List", false);
        toolWindow.getContentManager().addContent(content);
    }
}
