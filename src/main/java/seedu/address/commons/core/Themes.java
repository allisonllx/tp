package seedu.address.commons.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import seedu.address.ui.UiUtil;

/**
 * Contains a map of every theme URL and its respective command word.
 */
public final class Themes {
    public static final String AVAILABLE_THEMES_MESSAGE = "Available themes: dark, light, book, sakura";
    public static final String DEFAULT_THEME = UiUtil.getUrl("DarkTheme.css").toString();

    private static final Map<String, String> AVAILABLE_THEMES;

    private Themes() {}

    //Allow for more additions in the future rather than being limited to 10
    static {
        Map<String, String> map = new HashMap<>();
        map.put("dark", UiUtil.getUrl("DarkTheme.css").toString());
        map.put("light", UiUtil.getUrl("LightTheme.css").toString());
        map.put("book", UiUtil.getUrl("ReadingMode.css").toString());
        map.put("sakura", UiUtil.getUrl("Sakura.css").toString());
        map.put("jirai", UiUtil.getUrl("JiraiKei.css").toString());
        map.put("tech", UiUtil.getUrl("Techcore.css").toString());
        AVAILABLE_THEMES = Collections.unmodifiableMap(map);
    }

    /**
     * Checks if there exists a theme with the given theme command word.
     */
    public static boolean contains(String theme) {
        return AVAILABLE_THEMES.containsKey(theme);
    }

    /**
     * Returns the Url for the given theme command word.
     */
    public static String get(String theme) {
        assert AVAILABLE_THEMES.containsKey(theme);
        return AVAILABLE_THEMES.get(theme);
    }
}
