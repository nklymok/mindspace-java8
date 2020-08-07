package com.nklymok.mindspace.view.effect;

import javafx.scene.Parent;
import javafx.scene.effect.GaussianBlur;

public class BlurEffect {
    private static final GaussianBlur BLUR = new GaussianBlur(5);

    private BlurEffect() {
    }

    private static class BlurEffectHelper {
        private static final BlurEffect instance = new BlurEffect();
    }

    public static BlurEffect getInstance() {
        return BlurEffectHelper.instance;
    }

    public static void blur(Parent parent) {
        parent.setEffect(BLUR);
    }

    public static void unblur(Parent parent) {
        parent.setEffect(null);
    }

}
