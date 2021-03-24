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
    private final Context mContext;
    private final Activity mActivity;
    private final OnToggleAlarmListener mToggleListener;
    private List<AlarmDatabaseHelper.Alarm> mAlarmList;


    public AlarmRecyclerViewAdapter(List<AlarmDatabaseHelper.Alarm> alarmList,
                                    OnToggleAlarmListener toggleListener, Context context, Activity mActivity) {
        this.mAlarmList = alarmList;
        this.mToggleListener = toggleListener;
        this.mContext = context;
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.alarm_recycler_item, parent, false);
        return new AlarmViewHolder(rootView, mToggleListener, mContext, mActivity);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        AlarmDatabaseHelper.Alarm alarm = mAlarmList.get(position);
        holder.bind(alarm);
    }

    @Override
    public int getItemCount() {
        return mAlarmList.size();
    }

    @Override
    public void onViewRecycled(@NonNull AlarmViewHolder holder) {
        super.onViewRecycled(holder);
        holder.getmSwtStarted().setOnCheckedChangeListener(null);
    }

    public void setAlarm(List<AlarmDatabaseHelper.Alarm> alarmList) {
        this.mAlarmList = alarmList;
        notifyDataSetChanged();
    }
}
