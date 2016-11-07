package test.homework.nick.snp20.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.turbomanage.httpclient.AsyncCallback;
import com.turbomanage.httpclient.HttpResponse;
import com.turbomanage.httpclient.ParameterMap;
import com.turbomanage.httpclient.android.AndroidHttpClient;
import test.homework.nick.snp20.R;
import test.homework.nick.snp20.activity.MainActivity;
import test.homework.nick.snp20.model.Info;
import test.homework.nick.snp20.utils.Constants;
import test.homework.nick.snp20.utils.MRecyclerAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 30.10.16.
 */
public class SearchPlayerFragment extends Fragment {

    private EditText requertField;
    private ImageView requestButton;
    private RecyclerView recyclerView;

    private List<Info> playlist = new ArrayList<>();
    private MRecyclerAdapter adapter;
    private Gson gson = new GsonBuilder().create();
    private MainActivity activity = (MainActivity) getActivity();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initControlElements();
        setupControlElements();
    }

    private void initControlElements() {
        requertField = (EditText) getView().findViewById(R.id.search_edit_text);
        requestButton = (ImageView) getView().findViewById(R.id.search_button);
        recyclerView = (RecyclerView) getView().findViewById(R.id.search_result_recycler_view);
    }

    private void setupControlElements() {
        adapter = new MRecyclerAdapter(playlist);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRequest(requertField.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });
    }


    private void makeRequest(String request) {
        AndroidHttpClient httpClient = new AndroidHttpClient("http://api.soundcloud.com");
        ParameterMap params = httpClient.newParams()
                .add("q", request)
                .add("limit", "100")
                .add("client_id", Constants.USER_ID);
        httpClient.setMaxRetries(5);
        httpClient.get("/tracks.json", params, new AsyncCallback() {
            @Override
            public void onComplete(HttpResponse httpResponse) {
                Log.i("tag", httpResponse.getBodyAsString());
                String s = httpResponse.getBodyAsString();
                Type listType = new TypeToken<ArrayList<Info>>() {
                }.getType();
                playlist.clear();
                List<Info> list = gson.fromJson(s, listType);
                playlist.addAll(list);
//                adapter.notifyDataSetChanged();
            }


        });

    }
}

