package com.nklymok.mindspace.view.effect;

import javafx.scene.Parent;
import javafx.scene.effect.GaussianBlur;

public class BlurEffect {
    private final GaussianBlur BLUR;
    private static Parent target;

    public BlurEffect() {
        BLUR = new GaussianBlur(5);
    }

    private static class BlurEffectHelper {
        private static final BlurEffect instance = new BlurEffect();
    }

    public static BlurEffect getInstance() {
        return BlurEffectHelper.instance;
    }

    public static void setTarget(Parent root) {
        target = root;
    }

    public void blur() {
        target.setEffect(BLUR);
    }

    public void unblur() {
        target.setEffect(null);
    }

}
