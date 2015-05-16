package app.test.test2;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StudentAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<Student> mStudents;

	public StudentAdapter(Context context, List<Student> students) {
		// TODO Auto-generated constructor stub
		mInflater = LayoutInflater.from(context);
		mStudents = students;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mStudents.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mStudents.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view;
		ViewHolder holder;
		if (convertView == null) {
			view = mInflater.inflate(R.layout.student_layout, parent, false);
			holder = new ViewHolder();
			holder.avatar = (ImageView) view.findViewById(R.id.avatar);
			holder.name = (TextView) view.findViewById(R.id.name);
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		return view;
	}

	private class ViewHolder {
		public ImageView avatar;
		public TextView name;
	}
}
