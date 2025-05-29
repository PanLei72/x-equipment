package com.x.equipment.view.web.user;

import com.x.equipment.entity.User;
import com.x.equipment.view.web.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "users", layout = MainView.class)
@ViewController("EQUI_User.list")
@ViewDescriptor("user-list-view.xml")
@LookupComponent("usersDataGrid")
public class UserListView extends StandardListView<User> {
}