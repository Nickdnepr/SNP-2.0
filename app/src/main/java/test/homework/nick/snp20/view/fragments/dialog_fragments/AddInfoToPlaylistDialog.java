package test.homework.nick.snp20.view.fragments.dialog_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import org.greenrobot.eventbus.EventBus;
import test.homework.nick.snp20.R;
import test.homework.nick.snp20.database.service.InfoService;
import test.homework.nick.snp20.database.service.PlaylistService;
import test.homework.nick.snp20.events_for_eventbus.dialog_events.AddInfoToPlaylistDialogEvent;
import test.homework.nick.snp20.model.playlist_model.Playlist;
import test.homework.nick.snp20.utils.adapters.MListViewAdapter;

import java.util.List;

/**
 * Created by Nick on 08.12.16.
 */
public class AddInfoToPlaylistDialog extends DialogFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_info_to_playlist_dialog_layout, null);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        ListView listView = (ListView) v.findViewById(R.id.list_of_playlists);

        final List<Playlist> list = new PlaylistService(getContext()).getAll();
        list.remove(1);

        MListViewAdapter adapter = new MListViewAdapter(getContext(), list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventBus.getDefault().post(new AddInfoToPlaylistDialogEvent(list.get(position)));
                dismiss();
            }
        });
        return v;
    }
}
