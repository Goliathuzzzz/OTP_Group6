package context;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleManager {
    private static LocaleManager instance;
    private Locale locale;
    private ResourceBundle bundle;

    private LocaleManager() {
        this.locale = new Locale("en", "US");
        this.bundle = ResourceBundle.getBundle("Messages", locale);
    }

    public static LocaleManager getInstance() {
        if (instance == null) {
            instance = new LocaleManager();
        }
        return instance;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void resetLanguage() {
        this.locale = new Locale("en", "US");
        this.bundle = ResourceBundle.getBundle("Messages", locale);
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }
}
