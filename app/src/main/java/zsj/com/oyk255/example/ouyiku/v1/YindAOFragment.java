package zsj.com.oyk255.example.ouyiku.v1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import zsj.com.oyk255.R;

/**
 * A simple {@link Fragment} subclass.
 *
 */

@SuppressLint("ValidFragment")
public class YindAOFragment extends Fragment {

	private View view;
	private ImageView nPhoto;
	private ImageView nBtn;
	int position;

	public YindAOFragment(int position) {
		this.position=position;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view==null){
			view = inflater.inflate(R.layout.fragment_yind_ao, container, false);
			 initUI();
		}
		return view;
	}

	private void initUI() {
		nPhoto = (ImageView) view.findViewById(R.id.yindao_img);
		nBtn = (ImageView) view.findViewById(R.id.yindao_btn);
		switch (position) {
		case 0:
			nPhoto.setImageResource(R.mipmap.yin1);
			break;
		case 1:
			nPhoto.setImageResource(R.mipmap.yin2);
			break;
		case 2:
			nPhoto.setImageResource(R.mipmap.yin3);
			break;
		case 3:
			nPhoto.setImageResource(R.mipmap.yin4);
			nBtn.setVisibility(View.VISIBLE);
			nBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivity(new Intent(getActivity(), MainActivity.class));
					getActivity().finish();
				}
			});
			break;

		default:
			break;
		}
	}
}
