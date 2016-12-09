package test.homework.nick.snp20.view.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import test.homework.nick.snp20.R;
import test.homework.nick.snp20.database.MDBHelper;
import test.homework.nick.snp20.database.service.InfoService;
import test.homework.nick.snp20.database.service.PlaylistService;
import test.homework.nick.snp20.events_for_eventbus.dialog_events.DialogEvent;
import test.homework.nick.snp20.events_for_eventbus.dialog_events.PlaylistDialogEvent;
import test.homework.nick.snp20.model.playlist_model.Playlist;
import test.homework.nick.snp20.utils.string_containers.Commands;
import test.homework.nick.snp20.utils.adapters.MGridAdapter;
import test.homework.nick.snp20.view.ViewModel;
import test.homework.nick.snp20.view.fragments.dialog_fragments.CreateNewPlaylistDialog;
import test.homework.nick.snp20.view.fragments.dialog_fragments.PlaylistDialogFragment;

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
    private List<Playlist> playlistList;
    private MGridAdapter mGridAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.playlist_fragment_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
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
        getList();


        setupGridView();
    }

    private void getList() {
        List list = new PlaylistService(getContext()).getAll();
        list.add(0, new Playlist("add new", null));
        playlistList = list;
        mGridAdapter = new MGridAdapter(getActivity(), playlistList);
        gridView.setAdapter(mGridAdapter);
        mGridAdapter.notifyDataSetChanged();
    }

    private void setupGridView() {
        gridView.setNumColumns(3);
        gridView.setHorizontalSpacing(5);
        gridView.setVerticalSpacing(5);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    createNewPlaylistDialog.show(getFragmentManager(), "create new playlist dialog");
                }else {
                    PlaylistDialogFragment playlistDialogFragment = new PlaylistDialogFragment();
                    playlistDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MDialog);
                    playlistDialogFragment.show(getFragmentManager(), "tag");
                    EventBus.getDefault().postSticky(new PlaylistDialogEvent(new InfoService(getContext()).getPlaylist(playlistList.get(position)), playlistList.get(position)));
                }

                Log.i("grid view", position + "");
            }
        });
    }

    @Subscribe
    public void onEvent(DialogEvent dialogEvent) {
        if (dialogEvent.getMessage().equals(Commands.NEW_PLAYLIST_CREATED)) {
            getList();
            mGridAdapter.notifyDataSetChanged();
            Log.i("playlist", dialogEvent.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
