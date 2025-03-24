package context;

import java.util.Locale;

public class LocaleManager {
    private static LocaleManager instance;
    private Locale locale;

    private LocaleManager() {
        this.locale = new Locale("en", "US");
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

    public void resetLocale() {
        this.locale = new Locale("en", "US");
    }
}
