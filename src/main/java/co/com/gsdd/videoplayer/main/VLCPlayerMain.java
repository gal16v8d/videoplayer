package co.com.gsdd.videoplayer.main;

import javax.swing.SwingUtilities;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import co.com.gsdd.videoplayer.constants.ConstantsPlayer;
import co.com.gsdd.videoplayer.controller.VLCPlayerController;
import lombok.extern.slf4j.Slf4j;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 * This is the main class of the program. Runs all the actions to meet the requirements.
 * <p>
 * Requisites Taken: If new video in folder exists and convert it to images. Convert entire video to 250 images which
 * should be distributed equally through out the video. (not the 1st 250 frames but to pick 250 frames from the entire
 * video) Store the 250 images in another folder. The app should not require user input. detect if new video came in,
 * convert that to 250 images in another folder.
 * <p>
 * Important: The Libraries used in this project are in the pom.xml at this project.For a good use you will need:
 * <ul>
 * <li>Java in your PC (x86 or x64)
 * <li>VLC Media Player (Minimum version 2.1.5) NOTE: If java is (x86) I recommend use a VLC (x86) too AND if java is
 * (x64) I recommend use a VLC (x64) too.
 * </ul>
 * <p>
 * 
 * <ul>
 * Software Tested in:
 * <li>Windows 7 with Java (x64) And VLC Player 2.2.1 (x64).
 * <li>Windows 10 with Java (x64) And VLC Player 3.0.8 (x64).
 * </ul>
 * 
 * @author Great System Development Dynamic [GSDD] <br>
 *         Alexander Galvis Grisales <br>
 *         alex.galvis.sistemas@gmail.com <br>
 * @since 1.0
 */
@Slf4j
public class VLCPlayerMain {

    /**
     * Main method for the application.
     * 
     * @since 1.0
     * @param args
     *            the arg to be received. This wil be used to receipt the VLCLIBPATH, if no arguments
     *            {@link ConstantsPlayer#VLC_LIB_PATH} will be the default value.
     * @see VLCPlayerController
     * @see ConstantsPlayer
     */
    public static void main(final String[] args) {
        String searchPath = loadSearchPath(args);
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), searchPath);
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
        SwingUtilities.invokeLater(() -> {
            VLCPlayerController.getInstance().setSleep(loadCaptureTime(args));
        });
    }

    /**
     * This method validate if the user put an arg for the libvlc path.
     * 
     * @since 1.0
     * @param args
     *            The input args in execution.
     * @return The arg of the execution or {@link ConstantsPlayer#VLC_LIB_PATH}
     */
    private static String loadSearchPath(String[] args) {
        return args.length == 1 && args[0] != null ? ConstantsPlayer.validateRoute(args[0])
                : ConstantsPlayer.VLC_LIB_PATH;
    }

    /**
     * This method validate if the user put an arg for the time between captures.
     * 
     * @since 1.0
     * @param args
     *            The input args in execution.
     * @return The arg of the execution or {@link ConstantsPlayer#TIME_CAPTURES}
     */
    private static Long loadCaptureTime(String[] args) {
        try {
            if (args.length == 2 && args[1] != null) {
                return Long.parseLong(args[1]);
            } else {
                return ConstantsPlayer.TIME_CAPTURES;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ConstantsPlayer.TIME_CAPTURES;
        }
    }

}
