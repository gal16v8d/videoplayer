package co.com.gsdd.videoplayer.model;

import java.io.File;

import co.com.gsdd.videoplayer.constants.ConstantsPlayer;
import co.com.gsdd.videoplayer.controller.VLCPlayerController;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Implements a thread for take the snapshot, because if the snapshot run on application thread you will no able to use
 * the menu or properly close the application until the snapshot over, implement as a thread solves that issue.
 * 
 * @author Great System Development Dynamic [GSDD] <br>
 *         Alexander Galvis Grisales <br>
 *         alex.galvis.sistemas@gmail.com <br>
 * @since 1.0
 */
@Slf4j
@Getter
@Setter
public class SnapThread extends Thread {

    /**
     * Route of current video playing.
     */
    private String route;
    /**
     * Time between capture/snapshot.
     */
    private Long sleep;
    private VLCPlayerController controller;

    /**
     * A basic construct method for thread.
     * 
     * @since 1.0
     * @param route
     *            the video/audio local route.
     * @param sleep
     *            time between captures
     * @param controller
     */
    public SnapThread(String route, Long sleep, VLCPlayerController controller) {
        this.route = route;
        this.sleep = sleep != null ? sleep : ConstantsPlayer.TIME_CAPTURES;
        this.controller = controller;
    }

    /**
     * This method runs the thread for take the snapshots and put it on the folder. Listing properties:
     * <ul>
     * <li>The time between captures is sleep or {@link ConstantsPlayer#TIME_CAPTURES}
     * <li>The total captures to take is {@link ConstantsPlayer#CAPTURES}
     * <li>The name of the snapshot is {@link ConstantsPlayer#SNAPSHOT_PREFIX} and number of capture.
     * <li>The image format is {@link ConstantsPlayer#SNAPSHOT_EXT}
     * </ul>
     * <p>
     * Once the method finish the video will stop and you can select another video.
     * 
     * @since 1.0
     * @see ConstantsPlayer
     */
    @Override
    public void run() {
        try {
            // Subtract the file name for the folder separator.
            String folderName = route.substring(route.lastIndexOf(File.separator));
            for (Integer i = 1; i < (ConstantsPlayer.CAPTURES + 1); i++) {
                getController().getMediaPlayerComponent().getMediaPlayer()
                        .saveSnapshot(new File(ConstantsPlayer.ROUTE_OUT + File.separator + folderName + File.separator
                                + ConstantsPlayer.SNAPSHOT_PREFIX + i + ConstantsPlayer.SNAPSHOT_EXT));
                Thread.sleep(sleep);
            }
            getController().stopVideo();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
    }

}
