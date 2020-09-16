package com.rettiwer.smartcon.views;

import android.view.View;
import android.widget.TextView;

import com.rettiwer.smartcon.R;
import com.rettiwer.smartcon.models.Subnet;

/**
 * Created by retti on 27.05.2018.
 */

public class SubnetInfoViewHolder extends CustomViewHolder {

    public SubnetInfoViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(Object item) {
        TextView subnet_id = (TextView) itemView.findViewById(R.id.subnet_id);
        TextView subnet_address = (TextView) itemView.findViewById(R.id.subnet_adress);
        TextView first_host_address = (TextView) itemView.findViewById(R.id.first_host_address);
        TextView last_host_address = (TextView) itemView.findViewById(R.id.last_host_address);
        TextView subnet_broadcast_address = (TextView) itemView.findViewById(R.id.subnet_broadcast_address);

        Subnet s = (Subnet)item;

        subnet_id.setText("Podsieć #" + s.getID());
        subnet_address.setText(s.getSubnetAddress());
        first_host_address.setText(s.getFirstHostAddress());
        last_host_address.setText(s.getLastHostAddress());
        subnet_broadcast_address.setText(s.getBroadcastAddress());
    }
}
