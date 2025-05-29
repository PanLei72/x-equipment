package com.x.equipment.view.web.main;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Nav;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import com.x.equipment.utility.UIUtilities;
import com.x.equipment.view.portal.PortalView;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.app.main.StandardMainView;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@Route("main")
@ViewController("EQUI_MainView")
@ViewDescriptor("main-view.xml")
public class MainView extends StandardMainView {
    @Autowired
    private ViewNavigators viewNavigators;
    @ViewComponent
    private JmixButton fullScreenButton;

    @Subscribe(id = "homeButton", subject = "clickListener")
    public void onHomeButtonClick(final ClickEvent<JmixButton> event) {
        viewNavigators.view(this, PortalView.class).navigate();
    }

    @Subscribe(id = "fullScreenButton", subject = "clickListener")
    public void onFullScreenButtonClick(final ClickEvent<JmixButton> event) {
        UIUtilities.fullScreen(fullScreenButton);
    }


}
