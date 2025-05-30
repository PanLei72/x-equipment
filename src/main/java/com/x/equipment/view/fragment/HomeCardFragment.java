package com.x.equipment.view.fragment;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.component.UiComponentUtils;
import io.jmix.flowui.component.image.JmixImage;
import io.jmix.flowui.fragment.Fragment;
import io.jmix.flowui.fragment.FragmentDescriptor;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.MessageBundle;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.Target;
import io.jmix.flowui.view.ViewComponent;
import org.springframework.beans.factory.annotation.Autowired;

@FragmentDescriptor("home-card-fragment.xml")
public class HomeCardFragment extends Fragment<VerticalLayout> {
    @ViewComponent
    private JmixButton applicationButton;
    @ViewComponent
    private JmixImage<Object> applicationImage;

    @ViewComponent
    private JmixButton infoButton;
    @ViewComponent
    private MessageBundle messageBundle;
    private String applicationImagePath;
    @Autowired
    private ViewNavigators viewNavigators;
    private String applicationName;

    private String viewId;

    private String applicationIcon;


    @Subscribe(id = "applicationButton", subject = "clickListener")
    public void onApplicationButtonClick(final ClickEvent<JmixButton> event) {
        this.openApplication();
    }

    @Subscribe(id = "applicationImage", subject = "clickListener")
    public void onApplicationImageClick(final ClickEvent<JmixImage<?>> event) {
        this.openApplication();
    }


    public void setApplicationImagePath(String applicationImagePath) {
        this.applicationImagePath = applicationImagePath;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public void setApplicationIcon(String applicationIcon) {
        this.applicationIcon = applicationIcon;
    }

    @Subscribe(target = Target.HOST_CONTROLLER)
    public void onHostAttach(final AttachEvent event) {
        this.applicationImage.setSrc(applicationImagePath);
        this.applicationButton.setText(messageBundle.getMessage(this.applicationName));

        Icon vaadinIcon = new Icon(VaadinIcon.valueOf(applicationIcon));

        this.applicationButton.setIcon(vaadinIcon);
    }

    private void openApplication()
    {
        viewNavigators.view(UiComponentUtils.getCurrentView(), viewId)
                .withBackwardNavigation(true)
                .navigate();
    }

}