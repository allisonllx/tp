package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_LAST_CONTACTED = new Prefix("lc/");
    public static final Prefix PREFIX_LAST_UPDATED = new Prefix("lu/");
    public static final Prefix PREFIX_CLEAR_OLDEST = new Prefix("co/");
    public static final Prefix PREFIX_CLEAR_ALL = new Prefix("ca/");
    public static final Prefix PREFIX_CLEAR_LINE = new Prefix("cl/");
    public static final Prefix PREFIX_EDIT_LINE = new Prefix("el/");
    public static final Prefix PREFIX_ON = new Prefix("on/");
    public static final Prefix PREFIX_BEFORE = new Prefix("before/");
    public static final Prefix PREFIX_AFTER = new Prefix("after/");
    public static final Prefix PREFIX_OPEN = new Prefix("open/");
    public static final Prefix PREFIX_DELETE = new Prefix("delete/");

}
