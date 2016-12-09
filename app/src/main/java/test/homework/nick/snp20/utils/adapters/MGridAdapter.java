package test.homework.nick.snp20.utils.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import test.homework.nick.snp20.R;
import test.homework.nick.snp20.model.playlist_model.Playlist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 04.12.16.
 */
public class MGridAdapter extends BaseAdapter {

    private List<Playlist> listOfPlaylists;
    private LayoutInflater inflater;

    public MGridAdapter(Context context, List<Playlist> listOfPlaylists) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listOfPlaylists = listOfPlaylists;
    }

    @Override
    public int getCount() {
        return listOfPlaylists.size();
    }

    @Override
    public Object getItem(int position) {
        return listOfPlaylists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        view = inflater.inflate(R.layout.grid_item_layout, parent, false);
        Playlist playlist = listOfPlaylists.get(position);

        TextView title = (TextView) view.findViewById(R.id.grid_item_text);
        ImageView icon = (ImageView) view.findViewById(R.id.grid_item_icon);

        title.setText(playlist.getTitle());

        if (position == 0) {
            icon.setImageResource(R.drawable.ic_library_add_black_48dp);
        } else {
            if (playlist.getArtwork() == null) {
                icon.setImageResource(R.drawable.ic_library_music_black_48dp);
            }
        }
        return view;
    }
}
