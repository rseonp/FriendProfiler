package rseonp.friendprofiler;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {

    EditText etName, etNotes;
    Button bAddProfile;

    ImageView ivSetProfileImage;
    ListView lvProfileList;
    Uri imageUri = Uri.parse("android.resource://rseonp.friendprofiler/drawable/no_user.gif");
    DatabaseHandler dbHandler;

    List<Profile> Profiles = new ArrayList<Profile>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        etName = (EditText) findViewById(R.id.etName);
        etNotes = (EditText) findViewById(R.id.etNotes);
        lvProfileList = (ListView) findViewById(R.id.lvProfileList);
        ivSetProfileImage = (ImageView) findViewById(R.id.ivSetProfileImage);
        dbHandler = new DatabaseHandler(getApplicationContext());


        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("profiler");
        tabSpec.setContent(R.id.tProfiler);
        tabSpec.setIndicator("Profiler");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("list");
        tabSpec.setContent(R.id.tFriendList);
        tabSpec.setIndicator("Friend List");
        tabHost.addTab(tabSpec);

        bAddProfile = (Button) findViewById(R.id.bAddProfile);

        bAddProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile profile = new Profile(dbHandler.getProfileCount(), String.valueOf(etName.getText()), String.valueOf(etNotes.getText()), imageUri);
                if (!profileExists(profile)) {
                    dbHandler.createProfile(profile);
                    Profiles.add(profile);
                    Toast.makeText(getApplicationContext(), String.valueOf(etName.getText()) + " has been added to your Profiles!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), String.valueOf(etName.getText()) + " already exists. Please use a different name!", Toast.LENGTH_SHORT).show();
            }
        });

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                bAddProfile.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bAddProfile.setEnabled(String.valueOf(etName.getText()).trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ivSetProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), 1);
            }
        });

//        List<Profile> addableProfiles = dbHandler.getAllProfiles();
//        int profileCount = dbHandler.getProfileCount();
//
//        for (int i = 0; i < profileCount; i++) {
//            Profiles.add(addableProfiles.get(i));
//        }
//
//        if (!addableProfiles.isEmpty()) {
//            populateList();
//        }
        if (dbHandler.getProfileCount() != 0) {
            Profiles.addAll(dbHandler.getAllProfiles());
        }

        populateList();
    }

    private boolean profileExists(Profile profile) {
        String name = profile.getName();
        int profileCount = Profiles.size();

        for (int i = 0; i < profileCount; i++) {
            if (name.compareToIgnoreCase(Profiles.get(i).getName()) == 0) {
                return true;
            }
        }
        return false;
    }

    public void onActivityResult(int reqCode, int resCode, Intent data) {
        if (resCode == RESULT_OK) {
            if (reqCode == 1 ) {
                imageUri = (Uri) data.getData();
                ivSetProfileImage.setImageURI(data.getData());
            }
        }
    }

    private void populateList() {
        ArrayAdapter<Profile> adapter = new ProfileListAdapter();
        lvProfileList.setAdapter(adapter);
    }

    private class ProfileListAdapter extends ArrayAdapter<Profile> {
        public ProfileListAdapter() {
            super(HomeActivity.this, R.layout.listview_item, Profiles);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if(view == null) {
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);
            }

            Profile currentProfile = Profiles.get(position);

            TextView profileName = (TextView) view.findViewById(R.id.tvProfileName);
            profileName.setText(currentProfile.getName());
            TextView notes = (TextView) view.findViewById(R.id.tvNotes);
            notes.setText(currentProfile.getNotes());
            ImageView ivProfileImage = (ImageView) view.findViewById(R.id.ivProfileImage);
            ivProfileImage.setImageURI(currentProfile.getImageUri());

            return view;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
