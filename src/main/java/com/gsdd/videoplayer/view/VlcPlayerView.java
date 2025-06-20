package com.gsdd.videoplayer.view;

import com.gsdd.videoplayer.constants.ConstantsPlayer;
import com.gsdd.videoplayer.controller.VlcPlayerController;
import com.gsdd.videoplayer.enums.MenuOptionEnum;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.Serial;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.WindowConstants;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

/**
 * This class define the graphic components to be used. Basically are a frame with a menu bar that
 * hava the choices for the program.
 *
 * @author Great System Development Dynamic [GSDD] <br>
 *     Alexander Galvis Grisales <br>
 *     alex.galvis.sistemas@gmail.com <br>
 * @since 1.0
 */
@Slf4j
@Getter
public class VlcPlayerView extends JFrame {

  /** Default serial version for the class. */
  @Serial private static final long serialVersionUID = 1L;

  /** The title for the frame. */
  private static final String FRAME_TITLE = "Java Video Player";

  private final VlcPlayerController controller;

  public VlcPlayerView() {
    controller = new VlcPlayerController();
    setUpFrame(controller.getMediaPlayerComponent());
  }

  /**
   * Set to frame the properties that will be need to show.
   *
   * @since 1.0
   * @param mediaPlayerComponent the component to play audio/video that will be added to the
   *     container of the frame.
   */
  private void setUpFrame(EmbeddedMediaPlayerComponent mediaPlayerComponent) {
    // Maximize to fit screen size.
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setTitle(FRAME_TITLE);
    setContentPane(mediaPlayerComponent);
    setLocation(0, 0);
    setSize(screenSize.width, screenSize.height);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    buildMenu();
    setVisible(true);
  }

  /**
   * Create the menu for the frame.
   *
   * @since 1.0
   * @see ConstantsPlayer
   * @see MenuOptionEnum
   */
  private void buildMenu() {
    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);
    JMenu menuAdm = new JMenu(ConstantsPlayer.MENU_TITLE);
    menuBar.add(menuAdm);
    JMenuItem itemFile = new JMenuItem(ConstantsPlayer.MENU_ITEM_FILE);
    itemFile.addActionListener(evt -> selectOption(MenuOptionEnum.FILE));
    JMenuItem itemMute = new JMenuItem(ConstantsPlayer.MENU_ITEM_MUTE);
    itemMute.addActionListener(evt -> selectOption(MenuOptionEnum.MUTE));
    JMenuItem itemStop = new JMenuItem(ConstantsPlayer.MENU_ITEM_STOP);
    itemStop.addActionListener(evt -> selectOption(MenuOptionEnum.STOP));
    JMenuItem itemOut = new JMenuItem(ConstantsPlayer.MENU_ITEM_OUT);
    itemOut.addActionListener(evt -> selectOption(MenuOptionEnum.OUT));

    menuAdm.add(itemFile);
    menuAdm.add(new JSeparator());
    menuAdm.add(itemMute);
    menuAdm.add(new JSeparator());
    menuAdm.add(itemStop);
    menuAdm.add(new JSeparator());
    menuAdm.add(itemOut);

    try {
      setIconImage(new ImageIcon(getClass().getResource(ConstantsPlayer.IMAGE_MENU)).getImage());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  /**
   * Select an option of menu for execute an action, it depends on {@link MenuOptionEnum} what do
   * you need to choose. The action will call an instance of {@link VlcPlayerController} to be
   * executed.
   *
   * <p>This method will execute one of three actions like open a file chooser, stop a current
   * playing video or exit the program.
   *
   * @since 1.0
   * @param op the choose for make an action.
   * @see MenuOptionEnum
   */
  private void selectOption(MenuOptionEnum op) {
    switch (op) {
      case FILE -> getController().chooseFile();
      case MUTE -> getController().muteVideo();
      case STOP -> getController().stopVideo();
      case OUT -> getController().exit();
      default -> throw new IllegalArgumentException("Not Implemented");
    }
  }
}
