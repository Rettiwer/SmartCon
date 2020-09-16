package com.rettiwer.smartcon;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rettiwer.smartcon.models.Network;
import com.rettiwer.smartcon.models.Subnet;
import com.rettiwer.smartcon.views.CustomViewHolder;
import com.rettiwer.smartcon.views.NetworkInfoInputViewHolder;
import com.rettiwer.smartcon.views.NetworkInfoViewHolder;
import com.rettiwer.smartcon.views.SubnetInfoViewHolder;

import java.util.List;

/**
 * Created by retti on 27.05.2018.
 */

public class IPAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private List<Object> dataSet;

    private final int SUBNET = 0, NETWORK_INFO_INPUT = 1, NETWORK_INFO = 2;

    public IPAdapter(List<Object> data) {
        this.dataSet = data;
    }

    @Override
    public int getItemViewType(int position) {
        if (dataSet.get(position) instanceof Subnet)
            return SUBNET;
        else if (dataSet.get(position) instanceof String)
            return NETWORK_INFO_INPUT;
        else if(dataSet.get(position) instanceof Network)
            return NETWORK_INFO;
        return -1;
    }

    @Override
    public int getItemCount() {
        return this.dataSet.size();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CustomViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case SUBNET:
                View room_info = inflater.inflate(R.layout.subnet_info_row, parent, false);
                viewHolder = new SubnetInfoViewHolder(room_info);
                break;
            case NETWORK_INFO_INPUT:
                View v1 = inflater.inflate(R.layout.ip_info_input_row, parent, false);
                viewHolder = new NetworkInfoInputViewHolder(v1);
                break;
            case NETWORK_INFO:
                View v2 = inflater.inflate(R.layout.network_info_row, parent, false);
                viewHolder = new NetworkInfoViewHolder(v2);
                break;
            default:
                viewHolder = null;
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.bind(dataSet.get(holder.getAdapterPosition()));
    }
}
