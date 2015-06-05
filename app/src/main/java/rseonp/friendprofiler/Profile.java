package rseonp.friendprofiler;

import android.net.Uri;

/**
 * Created by Victor on 6/4/15.
 */
public class Profile {

    private String name, focus, remember;
    private Uri imageUri;
    private int id;

    public Profile(int id, String name, String focus, String remember, Uri imageUri) {
        this.id = id;
        this.name = name;
        this.focus = focus;
        this.remember = remember;
        this.imageUri = imageUri;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFocus() {
        return focus;
    }

    public String getRemember() {
        return remember;
    }

    public Uri getImageUri() {
        return imageUri;
    }
}
