package chumilion.embellish;

import android.app.ActionBar;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity
{

    EditText editor;
    TextView embel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editor = (EditText) findViewById(R.id.editor);

        embel = (TextView) findViewById(R.id.embel);
        Typeface fancy = Typeface.createFromAsset(getAssets(),"fonts/Precious.ttf");
        embel.setTypeface(fancy);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void onWindowFocusChanged(boolean b)
    {
        View parent = (View) editor.getParent();
        Log.i("parent", parent.getWidth() + " " + parent.getHeight());
        int margin = Math.min(parent.getWidth()/8, parent.getHeight()/8);
        RelativeLayout.LayoutParams editorLayoutParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        editorLayoutParams.setMargins(margin, margin, margin, 2*margin);
        editor.setLayoutParams(editorLayoutParams);
        RelativeLayout.LayoutParams embelLayoutParams = (RelativeLayout.LayoutParams) embel.getLayoutParams();
        embelLayoutParams.setMargins(0, 0, 0, margin - embel.getHeight()/2);
        embel.setLayoutParams(embelLayoutParams);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void embellish(View v)
    {
        String name = "Joe Smith";

        Cursor c = this.getContentResolver().query(ContactsContract.Profile.CONTENT_URI, null, null, null, null);
        int count = c.getCount();
        String[] columnNames = c.getColumnNames();
        boolean b = c.moveToFirst();
        int position = c.getPosition();
        String me = "Bug";
        if (count == 1 && position == 0) {
            {
                me = c.getString(c.getColumnIndex("DISPLAY_NAME"));
            }
        }
        c.close();

        String test = editor.getText() + "";

        //##############################################################

        String greeting = "Dear " + name + ",\n\n";

        String end = "\n\nSincerely,\n" + me;

        //##############################################################

        ArrayList<String> testlist = new ArrayList<>(Arrays.asList(test.split("\n")));

        String body = "";

        String initiator = "I hope this email finds you well. ";

        body += initiator;

        ArrayList<String> buildlist = new ArrayList<>();

        for(String line : testlist)
        {
            if(!(line.equals("")))
            {
                if(line.charAt(line.length() - 1) != '.')
                {
                    line += ". ";
                }
                if(Character.isLowerCase(line.charAt(0)))
                {
                    line = Character.toUpperCase(line.charAt(0)) + line.substring(1);
                }
                buildlist.add(line);
            }
        }

        testlist = buildlist;

        for(String line : testlist)
        {
            body += line;
        }

        String email = greeting + body + end;
        editor.setText(email);
    }
}
