package co.com.gsdd.videoplayer.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import co.com.gsdd.videoplayer.constants.ConstantsPlayer;
import co.com.gsdd.videoplayer.controller.VLCPlayerController;
import co.com.gsdd.videoplayer.enums.MenuOptionEnum;
import lombok.extern.slf4j.Slf4j;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

/**
 * This class define the graphic components to be used. Basically are a frame with a menu bar that hava the choices for
 * the program.
 * 
 * @author Great System Development Dynamic [GSDD] <br>
 *         Alexander Galvis Grisales <br>
 *         alex.galvis.sistemas@gmail.com <br>
 * @since 1.0
 */
@Slf4j
public class VLCPlayerView extends JFrame {

    /**
     * Default serial version for the class.
     */
    private static final long serialVersionUID = 1L;
    /**
     * A menu object for the frame.
     */
    private JMenu menuAdmon;
    /**
     * A menuitem for choose the video file to open.
     */
    private JMenuItem itemFile;
    /**
     * A menuitem for mute/un-mute the current video (only works if is currently playing).
     */
    private JMenuItem itemMute;
    /**
     * A menuitem for stop the current video (only works if is currently playing).
     */
    private JMenuItem itemStop;
    /**
     * A menuitem for exit the aplication.
     */
    private JMenuItem itemOut;

    /**
     * The title for the jframe.
     */
    private static final String FRAME_TITLE = "Java Video Player";

    /**
     * Set to frame the properties that will be need to show.
     * 
     * @since 1.0
     * @param mediaPlayerComponent
     *            the component to play audio/video that will be add to the container of the frame.
     */
    public void setUpFrame(EmbeddedMediaPlayerComponent mediaPlayerComponent) {
        // Maximize to fit screen size.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setTitle(FRAME_TITLE);
        setContentPane(mediaPlayerComponent);
        setLocation(0, 0);
        setSize(screenSize.width, screenSize.height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        menuAdmon = new JMenu(ConstantsPlayer.MENU_TITLE);
        menuBar.add(menuAdmon);
        itemFile = new JMenuItem(ConstantsPlayer.MENU_ITEM_FILE);
        itemFile.addActionListener((evt) -> selectOption(MenuOptionEnum.FILE));
        itemMute = new JMenuItem(ConstantsPlayer.MENU_ITEM_MUTE);
        itemMute.addActionListener((evt) -> selectOption(MenuOptionEnum.MUTE));
        itemStop = new JMenuItem(ConstantsPlayer.MENU_ITEM_STOP);
        itemStop.addActionListener((evt) -> selectOption(MenuOptionEnum.STOP));
        itemOut = new JMenuItem(ConstantsPlayer.MENU_ITEM_OUT);
        itemOut.addActionListener((evt) -> selectOption(MenuOptionEnum.OUT));

        menuAdmon.add(itemFile);
        menuAdmon.add(new JSeparator());
        menuAdmon.add(itemMute);
        menuAdmon.add(new JSeparator());
        menuAdmon.add(itemStop);
        menuAdmon.add(new JSeparator());
        menuAdmon.add(itemOut);

        try {
            setIconImage(new ImageIcon(getClass().getResource(ConstantsPlayer.IMAGE_MENU)).getImage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Select an option of menu for execute an action, it depends of {@link MenuOptionEnum} what do you need to choose.
     * The action will call an instance of {@link VLCPlayerController} to be executed.
     * <p>
     * This method will execute one of three actions like open a filechooser, stop a current playing video or exit the
     * program.
     * 
     * @since 1.0
     * @param op
     *            the choose for make an action.
     * @see MenuOptions.
     */
    private void selectOption(MenuOptionEnum op) {
        switch (op) {
        case FILE:
            VLCPlayerController.getInstance().chooseFile();
            break;
        case MUTE:
            VLCPlayerController.getInstance().muteVideo();
            break;
        case STOP:
            VLCPlayerController.getInstance().stopVideo();
            break;
        case OUT:
            VLCPlayerController.getInstance().exit();
            break;
        default:
            break;
        }
    }

}