package com.nklymok.mindspace.component;

import jdk.vm.ci.meta.Local;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class ResourceBundles {
    private final ResourceBundle resourceBundle;
    private final List<Locale> LOCALES;
    private final Preferences prefs;

    private ResourceBundles() {
        prefs = Preferences.userRoot();
        resourceBundle = ResourceBundle.getBundle("resbundles.UI_" + prefs.get("prefs_lang", "en_US"));
        LOCALES = Arrays.asList(Locale.ENGLISH, new Locale("uk", "UA"));
    }

    private static final class Helper {
        private static final ResourceBundles instance = new ResourceBundles();
    }

    public static ResourceBundle getResourceBundle() {
        return Helper.instance.resourceBundle;
    }

    public static List<Locale> getLocales() {
        return Helper.instance.LOCALES;
    }

    public static Locale getDefaultLocale() {
        return getLocales().contains(Locale.getDefault()) ? Locale.getDefault() : Locale.ENGLISH;
    }

    public static void setLocale(Locale locale) {
        Helper.instance.prefs.put("prefs_lang", locale.toLanguageTag());
    }

    public static Locale orDefault(Locale locale) {
        return getLocales().contains(locale) ? locale : getDefaultLocale();
    }

    public static Locale getCurrentLocale() {
        return orDefault(Locale.forLanguageTag(Helper.instance.prefs.get("prefs_lang", "en_US")));
    }
}
