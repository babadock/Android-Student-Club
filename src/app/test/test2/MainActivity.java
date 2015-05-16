package app.test.test2;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {
	public static final String NAME = "app.test.test2.name";
	public static final String ID = "app.test.test2.id";
	public static final String EMAIL = "app.test.test2.email";

	protected static final String TAG = "From Main Activity";
	protected static final int REQUEST_CODE = 0;
	private Button addnewBtn;

	ArrayList<Student> arrStudent = new ArrayList<Student>();
	ArrayAdapter<Student> adapter = null;
	Student student = null;

	/**
	 * 1. how to use onActivityResult to get value pass between two activity ???
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) {
			return;
		}
		student = new Student();
		student.setId(data.getStringExtra(ID));
		student.setName(data.getStringExtra(NAME));
		student.setEmail(data.getStringExtra(EMAIL));

		arrStudent.add(student);
		adapter.notifyDataSetChanged();
		Log.i(TAG, "add success !!!");

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ListView lv = (ListView) findViewById(R.id.listView1);
		/**
		 * 2. how to use another listview ???
		 */
		adapter = new ArrayAdapter<Student>(this,
				android.R.layout.simple_list_item_1, arrStudent);
		lv.setAdapter(adapter);
		/**
		 * 3. how to add avatar of student ???
		 */

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.i(TAG, "setOnItemClickListener ");
				/**
				 * 4. how to edit exist student in listview ???
				 */
				Intent i = new Intent(MainActivity.this, StudentInfo.class);
				startActivity(i);
			}
		});

		addnewBtn = (Button) findViewById(R.id.button1);
		addnewBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int a = 44;
				Log.i(TAG, "addnewBtn clicked !!! " + a);
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, StudentInfo.class);
				startActivityForResult(i, REQUEST_CODE);
			}
		});
	}
}
