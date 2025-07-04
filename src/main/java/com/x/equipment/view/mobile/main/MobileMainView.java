package com.x.equipment.view.mobile.main;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.x.equipment.constants.CheckCategory;
import com.x.equipment.entity.RepairOrder;
import com.x.equipment.entity.User;
import com.x.equipment.view.mobile.equipmentcheckjob.EquipmentCheckJobView;
import com.x.equipment.view.mobile.equipmentcheckjobquery.EquipmentCheckJobQueryView;
import com.x.equipment.view.mobile.equipmentrepair.EquipmentRepairConfirmView;
import com.x.equipment.view.mobile.equipmentrepair.EquipmentRepairView;
import com.x.equipment.view.mobile.equipmentrepair.RepairOrderCreateView;
import com.x.equipment.view.mobile.equipmentrepairorderquery.EquipmentRepairOrderQueryView;
import com.x.equipment.view.portal.PortalView;
import com.x.equipment.view.web.repairorder.RepairOrderDetailView;
import com.x.equipment.view.web.repairorder.RepairOrderListView;
import com.x.equipment.view.web.user.UserDetailView;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.app.main.StandardMainView;
import io.jmix.flowui.component.UiComponentUtils;
import io.jmix.flowui.component.main.JmixListMenu;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.kit.component.main.ListMenu;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

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
    @Autowired
    private CurrentAuthentication currentAuthentication;

    @Subscribe(id = "homeButton", subject = "clickListener")
    public void onHomeButtonClick(final ClickEvent<JmixButton> event) {
        View<?> currentView = UiComponentUtils.getCurrentView();

        if(currentView instanceof PortalView) {
            return;
        }

        if(currentView instanceof MobileMainView) {
            viewNavigators.view(this, PortalView.class).navigate();
        } else {
            viewNavigators.view(this, MobileMainView.class).navigate();
        }
    }


    @Subscribe(id = "maintenanceJobVbox", subject = "clickListener")
    public void onMaintenanceJobVboxClick(final ClickEvent<VerticalLayout> event) {
        viewNavigators.view(this, EquipmentCheckJobView.class)
                .withBackwardNavigation(true)
                .withQueryParameters(QueryParameters.of("category", CheckCategory.MAINTENANCE.getId()))
                .navigate();
    }

    @Subscribe(id = "checkJobQueryVbox", subject = "clickListener")
    public void onCheckJobQueryVboxClick(final ClickEvent<VerticalLayout> event) {
        viewNavigators.view(this, EquipmentCheckJobQueryView.class)
                .withBackwardNavigation(true)
                .withQueryParameters(QueryParameters.of("category", CheckCategory.INSPECTION.getId()))
                .navigate();
    }

    @Subscribe(id = "maintenanceJobQueryVbox", subject = "clickListener")
    public void onMaintenanceJobQueryVboxClick(final ClickEvent<VerticalLayout> event) {
        viewNavigators.view(this, EquipmentCheckJobQueryView.class)
                .withBackwardNavigation(true)
                .withQueryParameters(QueryParameters.of("category", CheckCategory.MAINTENANCE.getId()))
                .navigate();
    }

    @Subscribe(id = "equipmentRepairCreateVbox", subject = "clickListener")
    public void onEquipmentRepairCreateVboxClick(final ClickEvent<VerticalLayout> event) {
        viewNavigators.detailView(this, RepairOrder.class)
                .withViewClass(RepairOrderCreateView.class)
                .newEntity()
                .withBackwardNavigation(true)
                .navigate();
    }

    @Subscribe(id = "equipmentRepairVbox", subject = "clickListener")
    public void onEquipmentRepairVboxClick(final ClickEvent<VerticalLayout> event) {
        viewNavigators.view(this, EquipmentRepairView.class).withBackwardNavigation(true).navigate();
    }

    @Subscribe(id = "equipmentRepairConfirmVbox", subject = "clickListener")
    public void onEquipmentRepairConfirmVboxClick(final ClickEvent<VerticalLayout> event) {
        viewNavigators.view(this, EquipmentRepairConfirmView.class).withBackwardNavigation(true).navigate();
    }

    @Subscribe(id = "equipmentRepairOrderQueryVbox", subject = "clickListener")
    public void onEquipmentRepairOrderQueryVboxClick(final ClickEvent<VerticalLayout> event) {
        viewNavigators.listView(this, RepairOrder.class)
                .withViewClass(EquipmentRepairOrderQueryView.class)
                .withBackwardNavigation(true)
                .navigate();
    }


    @Subscribe
    public void onAttachEvent(final AttachEvent event) {
        initSideMenu();

    }


    private void initSideMenu() {

        menu.addMenuItem(
                ListMenu.MenuItem.create("myProfile")
                        .withTitle(messageBundle.getMessage("myProfile"))
                        .withPrefixComponent(icon("user.svg"))
                        .withClickHandler(e ->
                                viewNavigators.detailView(this, User.class)
                                        .withViewClass(UserDetailView.class)
                                        .editEntity((User) this.currentAuthentication.getUser())
                                        .withBackwardNavigation(true)
                                        .navigate()
                        ),
                0
        );


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


    private SvgIcon icon(String filename) {
        StreamResource iconResource = new StreamResource(filename,
                () -> getClass().getResourceAsStream("/META-INF/resources/icons/%s".formatted(filename)));
        return new SvgIcon(iconResource);
    }

}
