package guiContext;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleManager {
    private static LocaleManager instance;
    private final GuiContext guiContext = GuiContext.getInstance();
    private Locale locale;
    private ResourceBundle bundle;

    private LocaleManager() {
        this.locale = new Locale("en", "US");
        guiContext.setLanguage(locale.getLanguage());
        this.bundle = ResourceBundle.getBundle("Messages", locale);
    }

    public static synchronized LocaleManager getInstance() {
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
        guiContext.setLanguage(locale.getLanguage());
    }

    public void resetLanguage() {
        this.locale = new Locale("en", "US");
        guiContext.setLanguage(locale.getLanguage());
        this.bundle = ResourceBundle.getBundle("Messages", locale);
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }
}
