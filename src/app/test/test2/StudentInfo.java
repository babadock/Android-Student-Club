package app.test.test2;

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

public class StudentInfo extends Activity {
	protected static final String TAG = "From second activity";

	private EditText idEditText;
	private EditText nameEditText;
	private EditText emailEditText;
	private TextView titleTextView;

	private Button saveButton;
	private Button cancelButton;
	private ImageView imgAvatar;
	private String pathImage;
	private Intent data;

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
		if (data.getStringExtra("token") == null) {
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
		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i(TAG, "save button has been clicked !!!");
				Intent i = new Intent();
				i.putExtra("studentid", idEditText.getText().toString());
				i.putExtra("name", nameEditText.getText().toString());
				i.putExtra("email", emailEditText.getText().toString());
				if (pathImage != null) {
					i.putExtra("avatar", pathImage);
				}
				i.putExtra("token", "token");
				setResult(RESULT_OK, i);
				finish();
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
