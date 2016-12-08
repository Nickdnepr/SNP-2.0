package test.homework.nick.snp20.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import org.greenrobot.eventbus.EventBus;
import test.homework.nick.snp20.R;
import test.homework.nick.snp20.database.MDBHelper;
import test.homework.nick.snp20.database.service.PlaylistService;
import test.homework.nick.snp20.model.playlist_model.Playlist;
import test.homework.nick.snp20.utils.MGridAdapter;
import test.homework.nick.snp20.view.ViewModel;
import test.homework.nick.snp20.view.fragments.dialog_fragments.CreateNewPlaylistDialog;

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
    private CreateNewPlaylistDialog createNewPlaylistDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.playlist_fragment_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        EventBus.getDefault().register(this);
        initControlElements();
        setupControlElements();
    }

    @Override
    public void initControlElements() {
        gridView = (GridView) getView().findViewById(R.id.grid_view);
        searchText = (TextView) getView().findViewById(R.id.search_playlist_edit_text);
        searchButton = (ImageView) getView().findViewById(R.id.search_playlist_button);
        createNewPlaylistDialog = new CreateNewPlaylistDialog();
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

    private void setupGridView() {
        gridView.setNumColumns(3);
        gridView.setHorizontalSpacing(5);
        gridView.setVerticalSpacing(5);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
//                    createNewPlaylistDialog.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.MDialog);

                    createNewPlaylistDialog.show(getFragmentManager(), "create new playlist dialog");
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }
}
