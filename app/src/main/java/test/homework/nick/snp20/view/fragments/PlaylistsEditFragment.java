package test.homework.nick.snp20.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import test.homework.nick.snp20.R;
import test.homework.nick.snp20.database.MDBHelper;
import test.homework.nick.snp20.database.service.PlaylistService;
import test.homework.nick.snp20.model.playlist_model.Playlist;
import test.homework.nick.snp20.utils.MGridAdapter;
import test.homework.nick.snp20.view.ViewModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 05.12.16.
 */
public class PlaylistsEditFragment extends Fragment implements ViewModel {

    private GridView gridView;
    private TextView searchText;
    private ImageView searchButton;

    private MDBHelper mdbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.playlist_fragment_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initControlElements();
        setupControlElements();
    }

    @Override
    public void initControlElements() {
        gridView= (GridView) getView().findViewById(R.id.grid_view);
        searchText= (TextView) getView().findViewById(R.id.search_playlist_edit_text);
        searchButton= (ImageView) getView().findViewById(R.id.search_playlist_button);
    }

    @Override
    public void setupControlElements() {
        List<Playlist> list = new PlaylistService(getContext()).getAll();
        list.add(0, new Playlist("add new", null));
        MGridAdapter mGridAdapter = new MGridAdapter(getActivity(), list);
        gridView.setAdapter(mGridAdapter);
        mGridAdapter.notifyDataSetChanged();
        setupGridView();
    }

    private void setupGridView(){
        gridView.setNumColumns(3);
//        gridView.setColumnWidth(800);
        gridView.setHorizontalSpacing(5);
        gridView.setVerticalSpacing(5);
//        gridView.setStretchMode(GridView.STRETCH_SPACING_UNIFORM);
    }

}
