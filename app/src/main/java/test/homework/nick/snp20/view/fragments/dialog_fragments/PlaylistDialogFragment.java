package test.homework.nick.snp20.view.fragments.dialog_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.greenrobot.eventbus.EventBus;
import test.homework.nick.snp20.R;
import test.homework.nick.snp20.events_for_eventbus.dialog_events.PlaylistDialogEvent;
import test.homework.nick.snp20.model.music_info_model.Info;
import test.homework.nick.snp20.model.playlist_model.Playlist;
import test.homework.nick.snp20.utils.adapters.MRecyclerAdapter;

import java.util.List;

/**
 * Created by Nick on 09.12.16.
 */
public class PlaylistDialogFragment extends DialogFragment implements Toolbar.OnMenuItemClickListener{

    private Toolbar toolbar;
    private TextView textInToolbar;
    private List<Info> playlist;
    private Playlist title;
    private RecyclerView recyclerView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View v = inflater.inflate(R.layout.current_playlist_layout, null);
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.playlist_menu);
        textInToolbar= (TextView) toolbar.findViewById(R.id.text_in_toolbar);
        textInToolbar.setText(title.getTitle());
        recyclerView= (RecyclerView) getView().findViewById(R.id.playlist_recycler_view);
        MRecyclerAdapter adapter = new MRecyclerAdapter(playlist);
        return v;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    public void onEvent(PlaylistDialogEvent playlistDialogEvent){
        playlist = playlistDialogEvent.getList();
        title = playlistDialogEvent.getPlaylist();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
