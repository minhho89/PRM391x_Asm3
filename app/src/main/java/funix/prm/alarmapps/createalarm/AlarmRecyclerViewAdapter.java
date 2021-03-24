package funix.prm.alarmapps.createalarm;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import funix.prm.alarmapps.R;
import funix.prm.alarmapps.data.AlarmDatabaseHelper;

/**
 * Adapter for AlarmListFragment RecyclerView
 */
public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmViewHolder> {
    private final Context context;
    private final Activity activity;
    private final OnToggleAlarmListener toggleListener;
    private List<AlarmDatabaseHelper.Alarm> alarmList;


    public AlarmRecyclerViewAdapter(List<AlarmDatabaseHelper.Alarm> alarmList,
                                    OnToggleAlarmListener toggleListener, Context context, Activity activity) {
        this.alarmList = alarmList;
        this.toggleListener = toggleListener;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.alarm_recycler_item, parent, false);
        return new AlarmViewHolder(rootView, toggleListener, context, activity);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        AlarmDatabaseHelper.Alarm alarm = alarmList.get(position);
        holder.bind(alarm);
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    @Override
    public void onViewRecycled(@NonNull AlarmViewHolder holder) {
        super.onViewRecycled(holder);
        holder.getSwtStarted().setOnCheckedChangeListener(null);
    }

    public void setAlarm(List<AlarmDatabaseHelper.Alarm> alarmList) {
        this.alarmList = alarmList;
        notifyDataSetChanged();
    }
}
