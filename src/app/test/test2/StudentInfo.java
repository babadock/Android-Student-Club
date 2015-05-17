package app.test.test2;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

public class StudentInfo extends Activity {
	protected static final String TAG = "From second activity";

	AQuery aQuery = new AQuery(this);
	final String urlAvatar = "http://api.routine.geekup.vn/uploads/";
	final String urlRegister = "http://api.routine.geekup.vn/class/register";
	final String urlUpdate = "http://api.routine.geekup.vn/students/";

	private EditText idEditText;
	private EditText nameEditText;
	private EditText emailEditText;
	private TextView titleTextView;

	private Button saveButton;
	private Button cancelButton;
	private ImageView imgAvatar;
	private Intent data;

	String token = null;
	String id;
	String studentId;
	String name;
	String email;
	String avatar;
	private String pathImage;

	private boolean test = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);

		idEditText = (EditText) findViewById(R.id.editText1);
		nameEditText = (EditText) findViewById(R.id.editText2);
		emailEditText = (EditText) findViewById(R.id.editText3);
		titleTextView = (TextView) findViewById(R.id.textView1);
		imgAvatar = (ImageView) findViewById(R.id.imgAvatar);
		saveButton = (Button) findViewById(R.id.button1);
		cancelButton = (Button) findViewById(R.id.button2);

		data = getIntent();
		if (data.getExtras() == null) {
			// Sign up
			titleTextView.setText("Add Student");
			Log.i(TAG, "Sign up");
		} else {
			// Edit info
			if (test) {
				titleTextView.setText("Student Info");
				saveButton.setVisibility(View.INVISIBLE);
				cancelButton.setVisibility(View.INVISIBLE);

				idEditText.setKeyListener(null);
				idEditText.setCursorVisible(false);
				idEditText.setPressed(false);
				idEditText.setFocusable(false);

				nameEditText.setKeyListener(null);
				nameEditText.setCursorVisible(false);
				nameEditText.setPressed(false);
				nameEditText.setFocusable(false);

				emailEditText.setKeyListener(null);
				emailEditText.setCursorVisible(false);
				emailEditText.setPressed(false);
				emailEditText.setFocusable(false);
			} else {

			}

			// imgAvatar.setKeyListener(null);

			idEditText.setText(data.getStringExtra("studentid"));
			nameEditText.setText(data.getStringExtra("name"));
			emailEditText.setText(data.getStringExtra("email"));

			// idEditText.setVisibility(View.INVISIBLE);
			// idEditText.setActivated(activated)
			// idEditText.setKeyListener(null);

			/**
			 * getStringExtra avatar ???
			 */
			Log.i(TAG, "Edit info");
		}
		final Intent backdata = new Intent();

		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(TAG, "save button has been clicked !!!");
				studentId = idEditText.getText().toString();
				name = nameEditText.getText().toString();
				email = emailEditText.getText().toString();
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("student_id", studentId);
				params.put("name", name);
				params.put("email", email);
				if (pathImage != null) {
					params.put("avatar", new File(pathImage));
				}
				if (token == null) {
					// signup
					aQuery.ajax(urlRegister, params, JSONObject.class,
							new AjaxCallback<JSONObject>() {
								@Override
								public void callback(String url,
										JSONObject object, AjaxStatus status) {
									// TODO Auto-generated method stub
									if (object != null) {
										try {
											Boolean result = object
													.getBoolean("success");
											if (result) {
												JSONObject student = object
														.getJSONObject("student");
												token = student
														.getString("token");
												avatar = student
														.getString("avatar");
												Toast.makeText(
														getApplicationContext(),
														"Success",
														Toast.LENGTH_SHORT)
														.show();
												backdata.putExtra("id", id);
												backdata.putExtra("studentId",
														studentId);
												backdata.putExtra("name", name);
												backdata.putExtra("email",
														email);
												backdata.putExtra("token",
														token);
												backdata.putExtra("avatar",
														avatar);
												setResult(RESULT_OK, backdata);
												finish();
											} else {
												Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
		                                        return;
											}
										} catch (JSONException e) {
											// TODO: handle exception
											 e.printStackTrace();
										}
									}
									super.callback(url, object, status);
								}
							});
				}
			}
		});
		imgAvatar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, 200);
			}
		});
		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// setResult(RESULT_CANCEL, i);
				finish();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 200 && resultCode == RESULT_OK && data != null) {
			Uri selectedImg = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = getContentResolver().query(selectedImg,
					filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			pathImage = picturePath;
			cursor.close();

			ImageView imgAvatar = (ImageView) findViewById(R.id.imgAvatar);
			imgAvatar.setImageBitmap(BitmapFactory.decodeFile(picturePath));
		}
	}
}
