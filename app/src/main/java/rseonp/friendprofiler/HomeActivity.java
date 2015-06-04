package rseonp.friendprofiler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


public class HomeActivity extends AppCompatActivity {

    EditText txtName, txtBirthday, txtFavHob, txtNotes;
    Button bAddProfile;

    TextView tvFriendList;
    ListView lvFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtName = (EditText) findViewById(R.id.txtName);
        txtBirthday = (EditText) findViewById(R.id.txtBirthday);
        txtFavHob = (EditText) findViewById(R.id.txtFavHob);
        txtNotes = (EditText) findViewById(R.id.txtNotes);
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("profiler");
        tabSpec.setContent(R.id.tabProfiler);
        tabSpec.setIndicator("Profiler");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("list");
        tabSpec.setContent(R.id.tabFriendList);
        tabSpec.setIndicator("Friend List");
        tabHost.addTab(tabSpec);

        bAddProfile = (Button) findViewById(R.id.bAddProfile);

        bAddProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Your Profile has been created", Toast.LENGTH_SHORT).show();
            }
        });

        tvFriendList = (TextView) findViewById(R.id.tvFriendList);
        lvFriends = (ListView) findViewById(R.id.lvFriends);

        txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                bAddProfile.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bAddProfile.setEnabled(!txtName.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
