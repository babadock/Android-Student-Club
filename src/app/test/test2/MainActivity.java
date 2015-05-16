package app.test.test2;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

public class MainActivity extends ActionBarActivity {

	protected static final String TAG = "From Main Activity";
	protected static final int REQUEST_CODE = 0;
	private int positionEdited = 0;

	private List<Student> mStudents;
	private Button addnewBtn;
	private ListView listView;

	AQuery aQuery = new AQuery(this);
	String urlClass = "http://api.routine.geekup.vn/class";
	String urlLeave = "http://api.routine.geekup.vn/class/leave";

	StudentAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initListview();

		adapter = new StudentAdapter(this, mStudents);
		listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					final int position, long id) {
				final Student student = (Student) parent
						.getItemAtPosition(position);
				positionEdited = position;
				Intent editInfo = new Intent(MainActivity.this,
						StudentInfo.class);
				editInfo.putExtra("id", student.getId());
				editInfo.putExtra("studentid", student.getStudentid());
				editInfo.putExtra("name", student.getName());
				editInfo.putExtra("email", student.getEmail());
				editInfo.putExtra("avatar", student.getAvatar());
				// editInfo.putExtra("studentId", student.getmStudentId());
				editInfo.putExtra("token", student.getToken());
				startActivityForResult(editInfo, 200);
			}
		});

		addnewBtn = (Button) findViewById(R.id.button1);
		addnewBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent signup = new Intent(MainActivity.this, StudentInfo.class);
				startActivityForResult(signup, 100);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			String id = data.getStringExtra("id");
			String studentid = data.getStringExtra("studentid");
			String name = data.getStringExtra("name");
			String email = data.getStringExtra("email");
			String avatar = data.getStringExtra("avatar");
			String token = data.getStringExtra("token");
			if (requestCode == 100) {
				// Sign up
				Log.i(TAG, "requestCode == 100");
				Student newStudent = new Student();
				newStudent.setId(id);
				newStudent.setStudentid(studentid);
				newStudent.setName(name);
				newStudent.setEmail(email);
				newStudent.setAvatar(avatar);
				newStudent.setToken(token);
				// student.setName(data.getStringExtra(name));
				mStudents.add(0, newStudent);
				Log.i(TAG, "add success");
			} else {
				// Edit info
				Student student = mStudents.get(positionEdited);
				student.setId(studentid);
				student.setName(name);
				student.setEmail(email);
				student.setAvatar(avatar);
				Log.i(TAG, "requestCode == 200");
			}
		}
		adapter.notifyDataSetChanged();
	}

	private void initListview() {
		mStudents = new ArrayList<Student>();
		aQuery.ajax(urlClass, JSONObject.class, new AjaxCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject object,
					AjaxStatus status) {
				// TODO Auto-generated method stub
				if (object != null) {
					try {
						JSONArray array = object.getJSONArray("students");
						for (int i = 0; i < array.length(); i++) {
							JSONObject student = array.getJSONObject(i);
							String avatar = student.getString("avatar");
							String student_id = student.getString("student_id");
							String name = student.getString("name");
							String email = student.getString("email");
							String id = student.getString("id");
							addPersonWith(id, student_id, name, email, avatar,
									null);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	protected void addPersonWith(String id, String student_id, String name,
			String email, String avatar, Object object) {
		// TODO Auto-generated method stub
		Student student = new Student();
		student.setId(id);
		student.setName(name);
		// student.set

	}
}
