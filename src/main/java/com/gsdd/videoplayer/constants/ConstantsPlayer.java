package com.gsdd.videoplayer.constants;

import java.io.File;
import java.util.regex.Matcher;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;

/**
 * The main objective of this class is to store the basic constants needed for the application to
 * work with.
 *
 * @author Great System Development Dynamic [GSDD] <br>
 *     Alexander Galvis Grisales <br>
 *     alex.galvis.sistemas@gmail.com <br>
 * @since 1.0
 */
@UtilityClass
public final class ConstantsPlayer {

  /**
   * This property is for scan a found the required lib for VLC. (This is the path for libvlc.dll)
   */
  public static final String VLC_LIB_PATH = validateRoute(null);

  // Properties for titles of the menu and menuitem in application.

  /** Text definition for admin menu. */
  public static final String MENU_TITLE = "Admon";

  /** Text definition for menu item file. */
  public static final String MENU_ITEM_FILE = "Choose File";

  /** Text definition for menu item mute. */
  public static final String MENU_ITEM_MUTE = "Mute/Un-Mute Current Video";

  /** Text definition for menu item stop. */
  public static final String MENU_ITEM_STOP = "Stop Current Video";

  /** Text definition for menu item out. */
  public static final String MENU_ITEM_OUT = "Exit Program";

  /** File route to image icon for the window. */
  public static final String IMAGE_MENU = "/images/videoPlayer-Icon.png";

  /** Logger when video has stopped. */
  public static final String LOGGER_VIDEO_STOP = "Stopping current video...";

  /** Logger when exit. */
  public static final String LOGGER_EXIT = "Exit press in menu...";

  // Chooser properties

  /** Initial route for the chooser. (Current directory). */
  public static final String CHOOSE_ROOT = ".";

  /** Title for chooser window. */
  public static final String CHOOSE_VIDEO = "Please select the video:";

  /** Chooser filtered files, only allows to select a file with ext like description. */
  public static final String CHOOSE_DESC = "FileInput filtered: avi, wmv, mkv, mp4, flv, rmvb";

  /** Array of ext filter to be implemented in the chooser. */
  static final String[] FILTER_INPUT = {"avi", "wmv", "mkv", "mp4", "flv", "rmvb"};

  // This properties will be used to take and store snapshot.

  /** Time between captures/snapshots (In milliseconds). */
  public static final long TIME_CAPTURES = 200;

  /** A maximum number of captures/snapshots. */
  public static final int CAPTURES = 250;

  /** Prefix for store the captures. */
  public static final String SNAPSHOT_PREFIX = "Capture_";

  /** Extension for the captures. */
  public static final String SNAPSHOT_EXT = ".png";

  /** Expression to normalize routes. */
  public static final String EXPRESSION_SLASH = "[\\/]";

  /** File route out if not defined will be the user home. */
  public static final String ROUTE_OUT = System.getProperty("user.home");

  public static final String OS_NAME = "os.name";
  public static final String OS_MAC = "mac";
  public static final String OS_WIN = "win";

  public static final Stream<String> UBUNTU_PATTERN = Stream.of("nix", "nux", "ubuntu");

  /**
   * This method allows to get a route even with SO differences.
   *
   * @since 1.0
   * @return a normalized route.
   */
  public static String validateRoute(String route) {
    var osName = System.getProperty(OS_NAME).toLowerCase();
    var safeRoute = route == null ? getPathByOs(osName) : route;
    String r = safeRoute.replaceAll(EXPRESSION_SLASH, Matcher.quoteReplacement(File.separator));
    if (!r.endsWith(File.separator)) {
      r += File.separator;
    }
    return r;
  }

  private static String getPathByOs(String osName) {
    String result;
    if (osName.contains(OS_WIN)) {
      result = "D:/Programas/VideoLAN/VLC/";
    } else if (osName.contains(OS_MAC) || UBUNTU_PATTERN.anyMatch(osName::contains)) {
      result = "/usr/lib/vlc";
    } else {
      result = null;
    }
    return result;
  }

  /**
   * @return the filterInput
   */
  public static String[] getFilterInput() {
    return FILTER_INPUT;
  }
}
