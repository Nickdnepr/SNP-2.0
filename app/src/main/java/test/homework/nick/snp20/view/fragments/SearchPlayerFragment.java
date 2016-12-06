package test.homework.nick.snp20.view.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import test.homework.nick.snp20.R;
import test.homework.nick.snp20.view.ViewModel;
import test.homework.nick.snp20.view.activity.MainActivity;
import test.homework.nick.snp20.model.music_info_model.Info;
import test.homework.nick.snp20.utils.MRecyclerAdapter;
import test.homework.nick.snp20.utils.StringGenerator;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 30.10.16.
 */
public class SearchPlayerFragment extends Fragment implements ViewModel{

    private EditText requertField;
    private ImageView requestButton;
    private RecyclerView recyclerView;
    private TextView noResultsView;
    private ProgressBar searchProgress;


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

    public void initControlElements() {
        requertField = (EditText) getView().findViewById(R.id.search_edit_text);
        requestButton = (ImageView) getView().findViewById(R.id.search_button);
        recyclerView = (RecyclerView) getView().findViewById(R.id.search_result_recycler_view);
        noResultsView = (TextView) getView().findViewById(R.id.search_no_results);
        searchProgress = (ProgressBar) getView().findViewById(R.id.search_progress);
    }

    public void setupControlElements() {
        adapter = new MRecyclerAdapter(playlist);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                makeRequest(requertField.getText().toString());
                makeSpringRequest(requertField.getText().toString());
                View view = getActivity().getCurrentFocus();
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
    }


    //http://api.soundcloud.com/tracks.json?q=h&limit=100&client_id=8a81c591a1701b27d7e76e7b4e780050
    private void makeSpringRequest(String request) {
        new HttpTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, StringGenerator.generateRequestHttpString(request));
        Log.i("spring request", request);
    }

    private class HttpTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("spring", "pre async");
            searchProgress.setVisibility(View.VISIBLE);
            noResultsView.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(String... params) {
            try{
                Log.i("spring", "inside async");
                String url = params[0];
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                Log.i("spring", url);

                String result = restTemplate.getForObject(url, String.class);
                Log.i("spring", result);
                Log.i("spring", String.valueOf(result.length()));

                Type listType = new TypeToken<ArrayList<Info>>() {
                }.getType();
                playlist.clear();
                List<Info> list = gson.fromJson(result, listType);
                playlist.addAll(list);

                Log.i("spring list", list.toString());
            }catch (Exception e){
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.i("spring", "post async");
            super.onPostExecute(aVoid);
            adapter.notifyDataSetChanged();
            searchProgress.setVisibility(View.INVISIBLE);
            if (playlist.size() == 0) {
                noResultsView.setVisibility(View.VISIBLE);
            }
        }
    }
}

