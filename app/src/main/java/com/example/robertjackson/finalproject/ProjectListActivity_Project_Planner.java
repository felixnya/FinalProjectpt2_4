package com.example.robertjackson.finalproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Robert Jackson on Nov 24 2016.
 * ProjectListActivity_Project_Planner.java
 */

/**
 * @name ProjectListActivity
 * @return void
 * Main class for project list activity
 */
public class ProjectListActivity_Project_Planner extends AppCompatActivity {
    ExpandableListView expandableListView;
    ExpandableListAdapter adapter;
    View mainView;
    View main;
    Button b1;
    Button b2;
    Button b3;
    ArrayList<String> project = new ArrayList<>();
    HashMap<String, List<String>> hashMap = new HashMap<>();
    ArrayList<Boolean> isLate = new ArrayList<>();
    List<List<String>> studentList;
    Cursor projectCursor;
    Cursor studentCursor;
    Context ctx = this;
    Database_Project_Planner tempDb = new Database_Project_Planner(ctx);
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean exists;
    View vv;

    /**
     * @param savedInstanceState
     * @return void
     * onCreate method for class body, sets buttons and expendablelist views for the class
     * buttons go from refreshing the page, loading project and student input forms, to displaying the lists
     * @name onCreate
     */
    /* ----------------------------------------------------------------------------------------------*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_project_planner);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Project Manager");
        setSupportActionBar(toolbar);

        expandableListView = (ExpandableListView) findViewById(R.id.ProjectListView);
        expandableListView.setGroupIndicator(null);
        onSetItems();
        onSetListeners();
        main = getCurrentFocus();
        b1 = (Button) findViewById(R.id.button6);
        b2 = (Button) findViewById(R.id.button4);
        b3 = (Button) findViewById(R.id.buttonRefresh);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Snackbar.make(v, "Starting Project Form,", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Intent ffs = new Intent(getApplicationContext(), ProjectFormActivity_Project_Planner.class);
                    startActivity(ffs);
                    editor = sharedPreferences.edit().putBoolean("exists", true);
                    editor.apply();
                } catch (Exception e) {
                    Log.e("Exception Occurred ", e.toString());
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Snackbar.make(v, "Starting Student Form,", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Intent ffs = new Intent(getApplicationContext(), StudentFormActivity_Project_Planner.class);
                    startActivity(ffs);
                } catch (Exception e) {
                    Log.e("Exception Occurred ", e.toString());
                }
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    ProgressBar p = (ProgressBar) findViewById(R.id.progressBar3);
                    p.setVisibility(View.VISIBLE);
                    for (int i = 0; i < 100; i++) {
                        SystemClock.sleep(5);
                        p.setProgress(i);

                    }


                    finish();
                    startActivity(getIntent());


                } catch (Exception e) {
                    Log.e("Exception Occurred ", e.toString());
                }


            }
        });


    }

    /**
     * @return void
     * @name onResume
     */
    /* ----------------------------------------------------------------------------------------------*/
    public void onResume() {
        super.onResume();

    }

    /**
     * @return void
     * @name onStart
     */
    /* ----------------------------------------------------------------------------------------------*/
    public void onStart() {
        super.onStart();

    }

    /**
     * @param m
     * @return boolean
     * on create options menu_schedual_planner used to inflate the toolbar
     * @name onCreateOptionsMenu
     */
    /* ----------------------------------------------------------------------------------------------*/
    public boolean onCreateOptionsMenu(Menu m) {
        try {
            getMenuInflater().inflate(R.menu.toolbar_menu_project_planner, m);
            return true;
        } catch (Exception e) {
            Log.e("Exception Occurred ", e.toString());
        }
        return false;
    }

    /**
     * @param mi
     * @return boolean
     * on options item selected, basicly to select an action to take if a button if pressed on the
     * toolbar
     * @name onOptionsItemSelected
     */
    /* ----------------------------------------------------------------------------------------------*/
    public boolean onOptionsItemSelected(MenuItem mi) {
        try {
            int id = mi.getItemId();

            switch (id) {
                case R.id.action_one:
                    LayoutInflater inflater2 = this.getLayoutInflater();
                    vv = inflater2.inflate(R.layout.dialog_box_two_project_planner, null);

                    AlertDialog.Builder b = new AlertDialog.Builder(this);
                    b.setView(vv);
                    b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    b.create();
                    b.show();
                    break;
                case R.id.about:

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    LayoutInflater inflater = this.getLayoutInflater();
                    mainView = inflater.inflate(R.layout.dialog_box_project_planner, null);
                    builder.setView(mainView);
                    EditText et = (EditText) mainView.findViewById(R.id.dialogboxText);
                    et.setText(" Instructions:\n" +
                            "        1. Click on each project to expand it, showing all students in\n" +
                            "           relation with it\n\n" +
                            "        2. Press project form button for inserting or deleting a project.\n\n" +
                            "        3. Press student form button for inserting or deleting a student\n\n" +
                            "        4. Press the refresh button after inserting or deleting a \n" +
                            "           student or project. This will refresh the view\n\n" +
                            "            Written by Robert Jackson Student Number: 040627795\"\n\n" +
                            "            CST2355 Final Project\n\n" +
                            "            Project Planner Ver 1.2");

                    AlertDialog dialog = builder.create();
                    dialog.show();

                    break;

            }
            return true;
        } catch (Exception e) {
            Log.e("Exception Occurred ", e.toString());
        }
        return false;
    }

    /**
     * @return void
     * Inflates the list for the project list, goes thru and makes 2 different cursor objects,
     * one for student and the other for project. the project goes first, inflates its name, then
     * goes to student, and if the project name matches the project name of the student, then its added
     * to the list for that project.
     * <p>
     * I found that, sqlite and such are kind of buggy in android studio, as i've had select statements
     * and joins that would not work, either exact strings would not match, or that, they would put the
     * fields in the wrong getString statement.
     * @name onSetItems
     */
    /* ----------------------------------------------------------------------------------------------*/
    private void onSetItems() {


        try {
            projectCursor = tempDb.getProjectInfo(tempDb);

            studentList = new ArrayList<>();

            sharedPreferences = getSharedPreferences("exists", Context.MODE_PRIVATE);
            exists = sharedPreferences.getBoolean("exists", false);


            projectCursor.moveToFirst();
            int iterator = 0;
            if (exists) {
                do {
                    studentList.add(addListToArray());

                    Calendar c = Calendar.getInstance();

                    String[] e = projectCursor.getString(3).split("/");


                    //year month day
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date currentDate = sdf.parse(c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DAY_OF_MONTH));
                    System.out.println(projectCursor.getString(0));
                    Date endProjectDate = sdf.parse(e[0].toString() + "-" + e[1].toString() + "-" + e[2].toString());
                    if (currentDate.after(endProjectDate)) {
                        isLate.add(true);
                        project.add("Project Name: " + projectCursor.getString(0) + "\nCourse Code: " + projectCursor.getString(1) +
                                "\nStart Date: " + projectCursor.getString(2) + "\nEndDate: " + projectCursor.getString(3));
                    } else {
                        isLate.add(false);
                        project.add("Project Name: " + projectCursor.getString(0) + "\nCourse Code: " + projectCursor.getString(1) +
                                "\nStart Date: " + projectCursor.getString(2) + "\nEndDate: " + projectCursor.getString(3));
                    }
                    System.out.println(projectCursor.getString(0));
                    studentCursor = tempDb.getStudentInfo(tempDb);
                    studentCursor.moveToFirst();
                    do {
                        if (studentCursor.getString(0).equals(projectCursor.getString(0))) {
                            studentList.get(iterator).add("First Name: " + studentCursor.getString(1) + "\t\t\tLast Name: " + studentCursor.getString(2));
                        }

                    } while (studentCursor.moveToNext());


                    hashMap.put(project.get(iterator), studentList.get(iterator));
                    iterator++;
                } while (projectCursor.moveToNext());

            }


            adapter = new ExpandableListAdapter(ProjectListActivity_Project_Planner.this, project, hashMap);
            expandableListView.setAdapter(adapter);
        } catch (Exception e) {
            Log.e("Exception Occurred ", e.toString());
        }
    }

    /**
     * @return List<String>
     * Adds another array for the arraylist of arrays of string
     * @name addListToArray
     */
    /* ----------------------------------------------------------------------------------------------*/
    private List<String> addListToArray() {
        try {
            List<String> list = new ArrayList<>();
            return list;
        } catch (Exception e) {
            Log.e("Exception Occurred ", e.toString());
        }
        return null;
    }

    /**
     * @return void
     * Set listeners for toast of each item selected
     * @name onSetListeners
     */
    /* ----------------------------------------------------------------------------------------------*/
    private void onSetListeners() {
        try {
            expandableListView.setOnGroupClickListener(new OnGroupClickListener() {

                @Override
                public boolean onGroupClick(ExpandableListView listview, View view,
                                            int group_pos, long id) {
                    if (studentList.get(group_pos).size() == 0) {
                        Toast.makeText(ProjectListActivity_Project_Planner.this,
                                "Not currently Filled with students",
                                Toast.LENGTH_LONG).show();
                        return true;
                    } else if (isLate.get(group_pos)) {
                        Toast.makeText(ProjectListActivity_Project_Planner.this,
                                "Project is late!!!",
                                Toast.LENGTH_SHORT).show();

                        return false;
                    }
                    return false;
                }
            });

        } catch (Exception e) {
            Log.e("Exception Occurred ", e.toString());
        }

    }

    /**
     * @name ExpandableListAdapter
     * @return void
     * sets weither or not an item is expandable
     */
    /*----------------------------------------------------------------------------------------------*/
    public class ExpandableListAdapter extends BaseExpandableListAdapter {
        private Context _context;
        private List<String> project;
        private HashMap<String, List<String>> groupMember;

        /* ----------------------------------------------------------------------------------------------*/
        public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                     HashMap<String, List<String>> listChildData) {
            this._context = context;
            this.project = listDataHeader;
            this.groupMember = listChildData;
        }

        /**
         * @param groupPosition
         * @param childPosititon
         * @return Object
         * returns a child and its position of the list
         * @name getChild
         */
        /* ----------------------------------------------------------------------------------------------*/
        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            try {
                return this.groupMember.get(this.project.get(groupPosition)).get(
                        childPosititon);
            } catch (Exception e) {
                Log.e("Exception Occurred ", e.toString());
            }
            return null;
        }

        /**
         * @param groupPosition
         * @param childPosition
         * @return long
         * returns child id long
         * @name getChildId
         */
        /* ----------------------------------------------------------------------------------------------*/
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            try {
                return childPosition;
            } catch (Exception e) {
                Log.e("Exception Occurred ", e.toString());
            }
            return 0;
        }

        /**
         * @param groupPosition
         * @param childPosition
         * @param isLastChild
         * @param convertView
         * @param parent
         * @return View
         * returns view of child, to see which layout to use
         * @name getChildView
         */
        /* ----------------------------------------------------------------------------------------------*/
        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            try {
                final String childText = (String) getChild(groupPosition, childPosition);
                if (convertView == null) {
                    LayoutInflater infalInflater = (LayoutInflater) this._context
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = infalInflater.inflate(R.layout.child_student_project_planner, parent, false);
                }

                TextView child_text = (TextView) convertView.findViewById(R.id.child);
                child_text.setText(childText);
                return convertView;
            } catch (Exception e) {
                Log.e("Exception Occurred ", e.toString());
            }
            return null;
        }

        /**
         * @param groupPosition
         * @return int
         * returns the total child ammount as an int
         * @name getChildrenCount
         */
        /* ----------------------------------------------------------------------------------------------*/
        @Override
        public int getChildrenCount(int groupPosition) {
            try {
                return this.groupMember.get(this.project.get(groupPosition)).size();
            } catch (Exception e) {
                Log.e("Exception Occurred ", e.toString());
            }
            return 0;
        }

        /**
         * @param groupPosition
         * @return Object
         * returns an object of project group
         * @@name getGroup
         */
        /* ----------------------------------------------------------------------------------------------*/
        @Override
        public Object getGroup(int groupPosition) {
            try {
                return this.project.get(groupPosition);
            } catch (Exception e) {
                Log.e("Exception Occurred ", e.toString());
            }
            return null;
        }

        /**
         * @return int
         * returns a project as a group
         * @name getGroupCount
         */
        /* ----------------------------------------------------------------------------------------------*/
        @Override
        public int getGroupCount() {
            try {
                return this.project.size();
            } catch (Exception e) {
                Log.e("Exception Occurred ", e.toString());
            }
            return 0;
        }

        /**
         * @return long
         * returns project id
         * @name getGroupId
         */
        /* ----------------------------------------------------------------------------------------------*/
        @Override
        public long getGroupId(int groupPosition) {
            try {
                return groupPosition;
            } catch (Exception e) {
                Log.e("Exception Occurred ", e.toString());
            }
            return 0;
        }

        /**
         * @param groupPosition
         * @param isExpanded
         * @param convertView
         * @param parent
         * @return View
         * @name getGroupView
         * returns project view
         */
        /* ----------------------------------------------------------------------------------------------*/
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            try {
                String headerTitle = (String) getGroup(groupPosition);
                if (convertView == null) {
                    LayoutInflater infalInflater = (LayoutInflater) this._context
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = infalInflater.inflate(R.layout.header_project_project_planner, parent, false);
                }
                TextView header_text = (TextView) convertView.findViewById(R.id.header);
                header_text.setText(headerTitle);

                if (isLate.get(groupPosition)) {
                    header_text.setTextColor(Color.parseColor("#FF0000"));
                } else {

                    header_text.setTypeface(null, Typeface.NORMAL);
                }
                return convertView;
            } catch (Exception e) {
                Log.e("Exception Occurred ", e.toString());
            }
            return null;
        }

        /**
         * @return boolean
         * inquires if it has a stable id
         * @name hasStableIds
         */
        /* ----------------------------------------------------------------------------------------------*/
        @Override
        public boolean hasStableIds() {

            return false;
        }

        /**
         * @param groupPosition
         * @param childPosition
         * @return boolean
         * inquires if a child is selectable for a toast
         * @name isChildSelectable
         */
        /* ----------------------------------------------------------------------------------------------*/
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {

            return true;
        }
    }
}
/*------------------------------------------------------------------------------------------------*/