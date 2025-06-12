package com.x.equipment.view.fragment;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.component.UiComponentUtils;
import io.jmix.flowui.component.image.JmixImage;
import io.jmix.flowui.fragment.Fragment;
import io.jmix.flowui.fragment.FragmentDescriptor;
import io.jmix.flowui.view.MessageBundle;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.Target;
import io.jmix.flowui.view.ViewComponent;
import org.springframework.beans.factory.annotation.Autowired;

@FragmentDescriptor("mobile-function-card-fragment.xml")
public class MobileFunctionCardFragment extends Fragment<VerticalLayout> {
    @ViewComponent
    private Span nameSpan;
    @ViewComponent
    private JmixImage<Object> staticImage;
    @Autowired
    private ViewNavigators viewNavigators;
    @ViewComponent
    private MessageBundle messageBundle;
    private String name;

    private String imagePath;

    private String viewId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    @Subscribe(target = Target.HOST_CONTROLLER)
    public void onHostAttach(final AttachEvent event) {
        nameSpan.setText(messageBundle.getMessage(this.name));
        staticImage.setSrc(this.imagePath);
    }

    @Subscribe(id = "checkJobVbox", subject = "clickListener")
    public void onCheckJobVboxClick(final ClickEvent<VerticalLayout> event) {
        viewNavigators.view(UiComponentUtils.getCurrentView(), viewId)
                .withBackwardNavigation(true)
                .navigate();
    }

}