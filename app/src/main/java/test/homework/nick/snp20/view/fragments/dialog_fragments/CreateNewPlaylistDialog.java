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
import test.homework.nick.snp20.R;

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


        return inflater.inflate(R.layout.create_new_playlist_dialog_layout, null);
    }


    @Override
    public void onClick(View v) {

    }
}
