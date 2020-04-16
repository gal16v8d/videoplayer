package co.com.gsdd.videoplayer.model;

import lombok.Getter;
import lombok.Setter;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

/**
 * This class only define the components to work with for this application the component is a basic mediaPlayer (based
 * on VLC Player).
 * 
 * @author Great System Development Dynamic [GSDD] <br>
 *         Alexander Galvis Grisales <br>
 *         alex.galvis.sistemas@gmail.com <br>
 * @since 1.0
 */
@Getter
@Setter
public class VLCPlayer {

    /**
     * Component to play the video/audio.
     */
    private EmbeddedMediaPlayerComponent mediaPlayerComponent;

    /**
     * This is a default construct method, his only purpose is to init the required variables.
     * 
     * @since 1.0
     */
    public VLCPlayer() {
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
    }

}
