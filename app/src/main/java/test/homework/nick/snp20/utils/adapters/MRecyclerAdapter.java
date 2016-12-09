package test.homework.nick.snp20.utils.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.greenrobot.eventbus.EventBus;
import test.homework.nick.snp20.R;
import test.homework.nick.snp20.events_for_eventbus.view_to_player_events.ListEvent;
import test.homework.nick.snp20.model.music_info_model.Info;
import test.homework.nick.snp20.utils.StringGenerator;

import java.util.List;

/**
 * Created by Nick on 30.10.16.
 */
public class MRecyclerAdapter extends RecyclerView.Adapter<MRecyclerAdapter.MViewHolder> {

    public static final String TAG = "mRecyclerAdapter";
    private List<Info> playlist;

    public MRecyclerAdapter(List<Info> playlist) {
        this.playlist = playlist;
    }

    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MViewHolder holder, int position) {
        Info info = playlist.get(position);
        holder.setTime(info.getDuration());
        holder.setTitle(info.getTitle());
    }

    @Override
    public int getItemCount() {
        return playlist.size();
    }

    class MViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView time;
        private TextView title;

        public void setTime(int mills) {
            this.time.setText(StringGenerator.generateDurationString(mills));
        }

        public void setTitle(String title) {
            this.title.setText(title);
        }

        public MViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.list_item_track_duration);
            title = (TextView) itemView.findViewById(R.id.list_item_track_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.i(TAG, "clicked, id="+getAdapterPosition());
            EventBus.getDefault().post(new ListEvent(playlist, getAdapterPosition()));
        }
    }
}
