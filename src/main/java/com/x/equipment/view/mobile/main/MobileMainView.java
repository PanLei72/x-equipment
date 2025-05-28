package com.x.equipment.view.mobile.main;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.x.equipment.entity.RepairOrder;
import com.x.equipment.view.mobile.equipmentrepair.EquipmentRepairView;
import com.x.equipment.view.mobile.equipmentrepair.RepairOrderStartView;
import com.x.equipment.view.repairorder.RepairOrderDetailView;
import com.x.equipment.view.repairorder.RepairOrderListView;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.app.main.StandardMainView;
import io.jmix.flowui.component.main.JmixListMenu;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.kit.component.main.ListMenu;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Route(value = "mobile-main")
@ViewController("MobileMainView")
@ViewDescriptor("mobile-main-view.xml")
public class MobileMainView extends StandardMainView {
    @Autowired
    private ViewNavigators viewNavigators;
    @ViewComponent
    private JmixListMenu menu;
    @Autowired
    private Notifications notifications;
    @ViewComponent
    private MessageBundle messageBundle;

    @Subscribe(id = "homeButton", subject = "clickListener")
    public void onHomeButtonClick(final ClickEvent<JmixButton> event) {
        viewNavigators.view(this, MobileMainView.class).navigate();
    }

    @Subscribe(id = "equipmentRepair", subject = "clickListener")
    public void onEquipmentRepairClick(final ClickEvent<JmixButton> event) {
        viewNavigators.view(this, EquipmentRepairView.class).withBackwardNavigation(true).navigate();
    }

    @Subscribe(id = "equipmentRepairCreate", subject = "clickListener")
    public void onEquipmentRepairCreateClick(final ClickEvent<JmixButton> event) {
        viewNavigators.detailView(this, RepairOrder.class)
                .withViewClass(RepairOrderDetailView.class)
                .newEntity()
                .withBackwardNavigation(true)
                .navigate();
    }

    @Subscribe(id = "equipmentRepairOrderQuery", subject = "clickListener")
    public void onEquipmentRepairOrderQueryClick(final ClickEvent<JmixButton> event) {
        viewNavigators.listView(this, RepairOrder.class)
                .withViewClass(RepairOrderListView.class)
                .withBackwardNavigation(true)
                .navigate();
    }


    @Subscribe
    public void onAttachEvent(final AttachEvent event) {
        initMobileMenu();
        initSideMenu();

    }


    private void initSideMenu() {

        menu.addMenuItem(
                ListMenu.MenuItem.create("myProfile")
                        .withTitle(messageBundle.getMessage("myProfile"))
                        .withPrefixComponent(icon("user.svg"))
                        .withClickHandler(e -> notifications.create(messageBundle.getMessage("notImplemented"))
                                .withType(Notifications.Type.WARNING)
                                .show()
                        ),
                0
        );


        setMenuIcon("Turbine.list", "turbine-white.svg");
        setMenuIcon("Inspection.list", "inspection-white.svg");

        menu.addMenuItem(
                ListMenu.MenuItem.create("settings")
                        .withTitle(messageBundle.getMessage("settings"))
                        .withPrefixComponent(icon("settings.svg"))
                        .withClickHandler(e -> notifications.create(messageBundle.getMessage("notImplemented"))
                                .withType(Notifications.Type.WARNING)
                                .show()
                        )
        );
        menu.addMenuItem(
                ListMenu.MenuItem.create("technicalSupport")
                        .withTitle(messageBundle.getMessage("technicalSupport"))
                        .withPrefixComponent(icon("technical-support.svg"))
                        .withClickHandler(e -> notifications.create(messageBundle.getMessage("notImplemented"))
                                .withType(Notifications.Type.WARNING)
                                .show()
                        )
        );
    }

    private void setMenuIcon(String menuItemId, String iconFilename) {
        Optional.ofNullable(menu.getMenuItem(menuItemId))
                .ifPresent(it -> it.setPrefixComponent(icon(iconFilename)));
    }

    private void initMobileMenu() {
//        inspectionsTab = createTab("Inspections", "inspections", icon("inspection.svg"));
//        turbineTab = createTab("Turbines", "turbines", icon("turbine.svg"));
//        mainMenuTabs.add(
//                inspectionsTab,
//                turbineTab
//        );
    }

    private SvgIcon icon(String filename) {
        StreamResource iconResource = new StreamResource(filename,
                () -> getClass().getResourceAsStream("/META-INF/resources/icons/%s".formatted(filename)));
        return new SvgIcon(iconResource);
    }

}
