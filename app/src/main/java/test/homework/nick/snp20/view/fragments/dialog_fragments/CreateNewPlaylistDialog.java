package test.homework.nick.snp20.view.fragments.dialog_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import org.greenrobot.eventbus.EventBus;
import test.homework.nick.snp20.R;
import test.homework.nick.snp20.database.service.PlaylistService;
import test.homework.nick.snp20.events_for_eventbus.dialog_events.DialogEvent;
import test.homework.nick.snp20.model.playlist_model.Playlist;
import test.homework.nick.snp20.utils.string_containers.Commands;
import test.homework.nick.snp20.utils.converters.StringGenerator;

/**
 * Created by Nick on 08.12.16.
 */
public class CreateNewPlaylistDialog extends DialogFragment implements View.OnClickListener {

    private EditText name;
    private Button add;
    private Button cancel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        getDialog().setTitle("add new");
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = inflater.inflate(R.layout.create_new_playlist_dialog_layout, null);
        add = (Button) v.findViewById(R.id.create_playlist_button);
        cancel = (Button) v.findViewById(R.id.cancel_playlist_button);
        name= (EditText) v.findViewById(R.id.input_name);
        add.setOnClickListener(this);
        cancel.setOnClickListener(this);
        return v;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_playlist_button:
                if (!StringGenerator.containsNotAllowedCharacters(name.getText().toString())){
                    new PlaylistService(getContext()).save(new Playlist(name.getText().toString(), null));
                    EventBus.getDefault().post(new DialogEvent(Commands.NEW_PLAYLIST_CREATED));
                    dismiss();
                }else {

                }
                break;
            case R.id.cancel_playlist_button:
                dismiss();
                break;
        }
    }
}
