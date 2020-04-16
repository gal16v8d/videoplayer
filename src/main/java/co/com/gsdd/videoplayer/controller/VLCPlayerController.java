package co.com.gsdd.videoplayer.controller;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import co.com.gsdd.videoplayer.constants.ConstantsPlayer;
import co.com.gsdd.videoplayer.model.SnapThread;
import co.com.gsdd.videoplayer.model.VLCPlayer;
import co.com.gsdd.videoplayer.view.VLCPlayerView;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * This class acts like a controller that get the resources of the model ( {@link VLCPlayer}) and interacts with view
 * ({@link VLCPlayerView}) to acomplished the objectives of the project.
 * 
 * @author Great System Development Dynamic [GSDD] <br>
 *         Alexander Galvis Grisales <br>
 *         alex.galvis.sistemas@gmail.com <br>
 * @since 1.0
 */
@Slf4j
@Getter
@Setter
public class VLCPlayerController {

    /**
     * The view for the java application.
     */
    private VLCPlayerView view;
    /**
     * Define the model to be used in the aplication.
     */
    private VLCPlayer model;
    /**
     * Define a thread for take the snapshots.
     */
    private SnapThread snap;
    /**
     * Define the sleep time.
     */
    private Long sleep;

    /**
     * Unique instance for this class.
     */
    public static final VLCPlayerController INSTANCE = new VLCPlayerController();

    /**
     * A public construct method. Initialize the view and the model for work with.
     * 
     * @author Alexander Galvis
     * @since 1.0
     * @see VLCPlayer
     * @see VLCPlayerView
     * @see VLCPlayerView#setUpFrame(uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent)
     */
    private VLCPlayerController() {
        view = new VLCPlayerView();
        model = new VLCPlayer();
        view.setUpFrame(model.getMediaPlayerComponent());
    }

    /**
     * This method allows to choose a video file {@link ConstantsPlayer#FILTER_INPUT} and if it is a valid video file
     * begin the process to take the required snapshot.
     * <p>
     * If nothing choose , nothing will happen.
     * 
     * @since 1.0
     */
    public void chooseFile() {
        JFileChooser fs = new JFileChooser();
        fs.setCurrentDirectory(new File(ConstantsPlayer.CHOOSE_ROOT));
        fs.setDialogTitle(ConstantsPlayer.CHOOSE_VIDEO);
        fs.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fs.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(ConstantsPlayer.CHOOSE_DESC,
                ConstantsPlayer.getFilterInput());
        fs.setFileFilter(filter);
        if (fs.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            log.info(fs.getSelectedFile().getAbsolutePath());
            playVideoFromRoute(fs.getSelectedFile().getAbsolutePath());
        }
    }

    /**
     * This method allows to play the video of the selected route and while play the media can take the snapshot.
     * 
     * @author Alexander Galvis
     * @since 1.0
     * @param route
     *            an absolute path to the video resource.
     */
    private void playVideoFromRoute(String route) {
        Boolean play = model.getMediaPlayerComponent().getMediaPlayer().playMedia(route);
        log.info("{}:::{}", route, play);
        log.info("Time: {}", model.getMediaPlayerComponent().getMediaPlayer().getTime());
        takeSnapshot(route);
    }

    /**
     * This method creates an instance of {@link SnapThread} to take the snapshot required, if there is already a
     * definition of the thread that will be stopped and create a new one.
     * 
     * @author Alexander Galvis
     * @since 1.0
     * @param route
     *            the audio/video file absolute path.
     */
    private void takeSnapshot(String route) {
        if (snap != null) {
            snap.interrupt();
        }
        snap = new SnapThread(route, sleep);
        snap.start();
    }

    /**
     * This method allows to stop the current playing video, also if the {@link SnapThread} is taking the captures will
     * be interrupted. If there is not video playing nothign will happen.
     * 
     * @since 1.0
     * @see SnapThread
     */
    public void stopVideo() {
        if (model.getMediaPlayerComponent().getMediaPlayer().isPlaying()) {
            log.info(ConstantsPlayer.LOGGER_VIDEO_STOP);
            model.getMediaPlayerComponent().getMediaPlayer().stop();
            if (snap != null) {
                snap.interrupt();
            }
        }
    }

    /**
     * This method allows to mute/un-mute the current video playing. If the video is not currently playing nothing will
     * happen. If the current video is mute the action will be un-mute.
     * 
     * @since 1.0
     */
    public void muteVideo() {
        if (model.getMediaPlayerComponent().getMediaPlayer().isPlaying()) {
            if (model.getMediaPlayerComponent().getMediaPlayer().isMute()) {
                model.getMediaPlayerComponent().getMediaPlayer().mute(Boolean.FALSE);
            } else {
                model.getMediaPlayerComponent().getMediaPlayer().mute(Boolean.TRUE);
            }
        }
    }

    /**
     * This method allows to exit of the application. Stop current actions and exit program.
     * 
     * @since 1.0
     */
    public void exit() {
        log.info(ConstantsPlayer.LOGGER_EXIT);
        stopVideo();
        System.exit(0);
    }

    /**
     * @return the instance
     */
    public static VLCPlayerController getInstance() {
        return INSTANCE;
    }

}
