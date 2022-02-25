package com.myers.saveme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Activity_NewNote extends AppCompatActivity {
    EditText noteTitle,noteContent;
    Calendar c;
    String todayDate, currentTime;
    long ID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setHomeAsUpIndicator(R.drawable.mybutton);

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#2E3467"));
        actionBar.setBackgroundDrawable(colorDrawable);

        noteTitle = findViewById(R.id.txtTitle);
        noteContent = findViewById(R.id.txtContent);

        noteTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //get Current Date and Time
        c = Calendar.getInstance();
        todayDate = c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH);
        currentTime = pad((c.get(Calendar.HOUR)))+":"+pad(c.get(Calendar.MINUTE));
        Intent i = getIntent();
        ID = i.getLongExtra("ID",0);
        if(ID != 0){
            NoteDatabase db = new NoteDatabase(this);
            ListElement note = db.getNote(ID);
            TextView title = findViewById(R.id.txtTitle);
            title.setText(note.getTitle());
            TextView details = findViewById(R.id.txtContent);
            details.setText(note.getDescription());
            details.setMovementMethod(new ScrollingMovementMethod());
        }
    }

    private String pad(int n){
        if(n<10) return "0"+n;
        return String.valueOf(n);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater;
        inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        NoteDatabase db = new NoteDatabase(this);
        if (item.getItemId() == android.R.id.home) {
            String title = noteTitle.getText().toString().trim();
            if (title.length() != 0) {
                String content = noteContent.getText().toString().trim();
                ListElement listElement = new ListElement(ID,title, content, todayDate, currentTime);
                if(ID != 0){
                    db.editNote(listElement);
                }else{
                    long id = db.addNote(listElement);
                    ListElement check = db.getNote(id);
                    Log.d("inserted","Note "+id+" -> Title:"+check.getTitle());
                }
            }
            onBackPressed();
        }
        if (item.getItemId() == R.id.Delete){
            if(ID != 0){
                db.deleteNote(ID);
            }
            onBackPressed();
            Toast.makeText(this,"Nota eliminda",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(this,MainActivity.class);
        this.startActivity(i);
    }
}