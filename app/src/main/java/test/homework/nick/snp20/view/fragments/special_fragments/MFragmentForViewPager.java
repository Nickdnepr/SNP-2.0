package test.homework.nick.snp20.view.fragments.special_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import test.homework.nick.snp20.R;


public class MFragmentForViewPager extends Fragment {

    private String url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.view_pager_fragment_layout, container, false);
        Log.i("glide", v.toString());
        url = getArguments().getString("url");
        Glide.with(this).load(url).into((ImageView) v.findViewById(R.id.artwork));
        return v;
    }
}
