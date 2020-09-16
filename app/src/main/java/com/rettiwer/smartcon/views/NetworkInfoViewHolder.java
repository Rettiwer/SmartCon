package com.rettiwer.smartcon.views;

import android.view.View;
import android.widget.TextView;

import com.rettiwer.smartcon.R;
import com.rettiwer.smartcon.models.Network;

/**
 * Created by retti on 27.05.2018.
 */

public class NetworkInfoViewHolder extends CustomViewHolder {

    public NetworkInfoViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(Object item) {
        TextView network_address = (TextView) itemView.findViewById(R.id.network_address);
        TextView mask_address = (TextView) itemView.findViewById(R.id.mask_address);
        TextView broadcast_address = (TextView) itemView.findViewById(R.id.broadcast_address);
        TextView hosts_amount = (TextView) itemView.findViewById(R.id.hosts_amount);
        TextView subnet_amount = (TextView) itemView.findViewById(R.id.subnets_amount);
        TextView address_class = (TextView) itemView.findViewById(R.id.address_class);

        Network network = (Network) item;

        network_address.setText(network.getNetworkAddress());
        mask_address.setText(network.getMaskAddress());
        broadcast_address.setText(network.getBroadcastAddress());
        hosts_amount.setText(Integer.toString(network.getHostsAmount()));
        subnet_amount.setText(Integer.toString(network.getSubnetsAmount()));
        address_class.setText(network.getAddress_class());
    }
}
