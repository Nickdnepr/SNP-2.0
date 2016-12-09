package test.homework.nick.snp20.utils.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import test.homework.nick.snp20.R;
import test.homework.nick.snp20.model.playlist_model.Playlist;

import java.util.List;

/**
 * Created by Nick on 08.12.16.
 */
public class MListViewAdapter extends ArrayAdapter<Playlist> {


    public MListViewAdapter(Context context, List<Playlist> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.listview_with_playlists_item_layout, null);
        TextView textView = (TextView) convertView.findViewById(R.id.playlist_title);
        textView.setText(getItem(position).getTitle());

        return convertView;
    }
}
