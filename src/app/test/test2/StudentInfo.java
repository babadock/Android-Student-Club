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

public class StudentInfo extends Activity {
	protected static final String TAG = "From second activity";
	private EditText nameEditText;
	private EditText idEditText;
	private EditText emailEditText;
	private Button saveButton;
	private Button cancelButton;
	private ImageView imgAvatar;
	private String pathImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);

		idEditText = (EditText) findViewById(R.id.editText1);
		nameEditText = (EditText) findViewById(R.id.editText2);
		emailEditText = (EditText) findViewById(R.id.editText3);

		saveButton = (Button) findViewById(R.id.button1);
		imgAvatar = (ImageView) findViewById(R.id.imgAvatar);

		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i(TAG, "save button has been clicked !!!");
				Intent i = new Intent();
				i.putExtra(MainActivity.ID, idEditText.getText().toString());
				i.putExtra(MainActivity.NAME, nameEditText.getText().toString());
				i.putExtra(MainActivity.EMAIL, emailEditText.getText()
						.toString());
				if (pathImage != null) {
					i.putExtra(MainActivity.AVATAR, pathImage);
				}
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
