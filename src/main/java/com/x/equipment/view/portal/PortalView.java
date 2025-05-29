package com.x.equipment.view.portal;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.x.equipment.utility.UIUtilities;
import com.x.equipment.view.web.main.MainView;
import com.x.equipment.view.mobile.main.MobileMainView;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.component.layout.ViewLayout;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

/*
 * To use the view as a main view don't forget to set
 * new value (see @ViewController) to 'jmix.ui.main-view-id' property.
 * Also, the route of this view (see @Route) must differ from the route of default MainView.
 */
@Route(value="portal-view")
@ViewController(id = "EQUI_PortalView")
@ViewDescriptor(path = "portal-view.xml")
public class PortalView extends View<ViewLayout> implements RouterLayout {
    @Autowired
    private ViewNavigators viewNavigators;
    @ViewComponent
    private JmixButton mobileAppButton;
    @ViewComponent
    private JmixButton equipmentManagementButton;
    @ViewComponent("tabSheet.tabAllModule")
    private Tab tabSheetTabAllModule;
    @ViewComponent("tabSheet.tabSystemManagement")
    private Tab tabSheetTabSystemManagement;
    @ViewComponent("tabSheet.tabOperationExecution")
    private Tab tabSheetTabOperationExecution;
    @ViewComponent
    private JmixButton fullScreenButton;

    @Subscribe("tabSheet")
    public void onTabSheetAttach(final AttachEvent event) {
        tabSheetTabAllModule.addComponentAsFirst(VaadinIcon.GRID_BIG.create());
        tabSheetTabSystemManagement.addComponentAsFirst(VaadinIcon.DATABASE.create());
        tabSheetTabOperationExecution.addComponentAsFirst(VaadinIcon.COGS.create());
    }

    @Subscribe(id = "equipmentManagementButton", subject = "clickListener")
    public void onEquipmentManagementButtonClick(final ClickEvent<JmixButton> event) {
        viewNavigators.view(this, MainView.class).navigate();

    }

    @Subscribe(id = "mobileAppButton", subject = "clickListener")
    public void onMobileAppButtonClick(final ClickEvent<JmixButton> event) {
        viewNavigators.view(this, MobileMainView.class).navigate();
    }

    @Subscribe(id = "fullScreenButton", subject = "clickListener")
    public void onFullScreenButtonClick(final ClickEvent<JmixButton> event) {
        UIUtilities.fullScreen(fullScreenButton);
    }

}