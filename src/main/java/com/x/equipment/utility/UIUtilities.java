package com.x.equipment.utility;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.icon.VaadinIcon;
import io.jmix.flowui.kit.component.button.JmixButton;

public class UIUtilities {

    public static void fullScreen(JmixButton button) {
// 執行 JavaScript 檢查全屏狀態並切換
        UI.getCurrent().getPage().executeJs(
                "var doc = document;" +
                        "var isFullscreen = !!doc.fullscreenElement || !!doc.mozFullScreenElement || !!doc.webkitFullscreenElement;" +
                        "if (!isFullscreen) {" +
                        "    doc.documentElement.requestFullscreen();" +
                        "    return 'enter';" +
                        "} else {" +
                        "    doc.exitFullscreen();" +
                        "    return 'exit';" +
                        "}"
        ).then(result -> {
            // 根據返回的字符串更新按鈕文本
            String action = result.asString();
            if ("enter".equals(action)) {
                button.setIcon(VaadinIcon.COMPRESS_SQUARE.create());
            } else if ("exit".equals(action)) {
                button.setIcon(VaadinIcon.EXPAND_FULL.create());
            }
        }, error -> {
            // 處理 JavaScript 執行錯誤
            button.setText("全屏切換失敗");
            System.err.println("Fullscreen toggle error: " + error);
        });
    }
}
