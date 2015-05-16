package app.test.test2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StudentInfo extends Activity{
	protected static final String TAG = "From second activity";
	private EditText nameEditText;
	private EditText idEditText;
	private EditText emailEditText;
	private Button saveButton;
	private Button cancelButton;
	private Button imageButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);

		idEditText = (EditText) findViewById(R.id.editText1);
		nameEditText = (EditText) findViewById(R.id.editText2);
		emailEditText = (EditText) findViewById(R.id.editText3);

		saveButton = (Button) findViewById(R.id.button1);
		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i(TAG, "save button has been clicked !!!");
				Intent i = new Intent();
				i.putExtra(MainActivity.ID, idEditText.getText().toString());
				i.putExtra(MainActivity.NAME, nameEditText.getText().toString());
				i.putExtra(MainActivity.NAME, emailEditText.getText()
						.toString());
				setResult(RESULT_OK, i);
				finish();
			}
		});
	}
}
