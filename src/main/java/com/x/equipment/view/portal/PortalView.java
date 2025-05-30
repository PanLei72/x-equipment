package com.x.equipment.view.portal;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.x.equipment.utility.UIUtilities;
import io.jmix.flowui.component.layout.ViewLayout;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;

/*
 * To use the view as a main view don't forget to set
 * new value (see @ViewController) to 'jmix.ui.main-view-id' property.
 * Also, the route of this view (see @Route) must differ from the route of default MainView.
 */
@Route(value="portal-view")
@ViewController(id = "EQUI_PortalView")
@ViewDescriptor(path = "portal-view.xml")
public class PortalView extends View<ViewLayout> implements RouterLayout {

    @ViewComponent
    private JmixButton fullScreenButton;


    @Subscribe(id = "fullScreenButton", subject = "clickListener")
    public void onFullScreenButtonClick(final ClickEvent<JmixButton> event) {
        UIUtilities.fullScreen(fullScreenButton);
    }

}