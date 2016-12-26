package conductor.ru.rockylabs.androidconductorexample.changehandler;

/**
 * Created by Zinnur on 26.12.16.
 */

import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.bluelinelabs.conductor.changehandler.TransitionChangeHandlerCompat;

public class ArcFadeMoveChangeHandlerCompat extends TransitionChangeHandlerCompat {

    public ArcFadeMoveChangeHandlerCompat() {
        super(new ArcFadeMoveChangeHandler(), new FadeChangeHandler());
    }

}