package seedu.address.commons.core.theme;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import seedu.address.ui.UiUtil;

/**
 * Contains a map of every theme URL and its respective command word.
 */
public final class Themes {
    public static final String AVAILABLE_THEMES_MESSAGE = "Available themes: dark, light, book, sakura, grass, tech, jirai";
    public static final String DEFAULT_THEME = UiUtil.getUrl("DarkTheme.css").toString();

    private static final Map<String, Theme> AVAILABLE_THEMES;

    private Themes() {}

    //Allow for more additions in the future rather than being limited to 10
    static {
        Map<String, Theme> map = new HashMap<>();
        map.put("dark", new Theme("dark", UiUtil.getUrl("DarkTheme.css").toString()));
        map.put("light", new Theme("light", UiUtil.getUrl("LightTheme.css").toString()));
        map.put("book", new Theme("book", UiUtil.getUrl("ReadingMode.css").toString()));
        map.put("sakura", new Theme("sakura", UiUtil.getUrl("SakuraTheme.css").toString()));
        map.put("grass", new Theme("grass", UiUtil.getUrl("GrassTheme.css").toString()));
        map.put("tech", new Theme("tech", UiUtil.getUrl("Techcore.css").toString()));
        map.put("jirai", new Theme("jirai", UiUtil.getUrl("JiraiKei.css").toString()));
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
    public static Theme get(String theme) {
        assert AVAILABLE_THEMES.containsKey(theme);
        return AVAILABLE_THEMES.get(theme);
    }
}
