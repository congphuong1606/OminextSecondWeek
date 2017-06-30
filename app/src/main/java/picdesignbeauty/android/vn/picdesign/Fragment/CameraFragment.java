package picdesignbeauty.android.vn.picdesign.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import picdesignbeauty.android.vn.picdesign.R;

public class CameraFragment extends android.support.v4.app.Fragment {



    public static CameraFragment newInstance(String param1, String param2) {
        CameraFragment fragment = new CameraFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

}
