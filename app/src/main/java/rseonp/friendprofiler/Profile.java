package rseonp.friendprofiler;

import android.net.Uri;

/**
 * Created by Victor on 6/4/15.
 */
public class Profile {

    private String name, notes;
    private Uri imageUri;
    private int id;

    public Profile(int id, String name, String notes, Uri imageUri) {
        this.id = id;
        this.name = name;
        this.notes = notes;
        this.imageUri = imageUri;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNotes() {
        return notes;
    }

    public Uri getImageUri() {
        return imageUri;
    }
}
