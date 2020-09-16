package com.rettiwer.smartcon.views;

import android.app.Activity;
import android.content.Context;
import android.net.IpPrefix;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rettiwer.smartcon.MainActivity;
import com.rettiwer.smartcon.R;
import com.rettiwer.smartcon.models.Network;
import com.rettiwer.smartcon.models.Subnet;
import com.rettiwer.smartcon.utils.IPUtils;

import java.util.Iterator;

/**
 * Created by retti on 27.05.2018.
 */

public class NetworkInfoInputViewHolder extends CustomViewHolder {

    public NetworkInfoInputViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(final Object item) {
        final EditText ip_address = (EditText) itemView.findViewById(R.id.ip_address_input);
        final Spinner mask_selector = (Spinner) itemView.findViewById(R.id.mask_selector);
        Button submit_button = (Button) itemView.findViewById(R.id.submit_button);

        mask_selector.setSelection(23);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!IPUtils.isAddressValid(ip_address.getText().toString())) {
                    Toast.makeText(itemView.getContext(), "Nieprawidłowy adres IPv4", Toast.LENGTH_SHORT).show();
                    return;
                }

                while (MainActivity.adapter_items.size() > 1) {
                    MainActivity.adapter_items.remove(MainActivity.adapter_items.size()-1);
                    MainActivity.mAdapter.notifyItemRemoved(MainActivity.adapter_items.size());
                }

                Network net = new Network();
                net.setMaskAddress(mask_selector.getSelectedItem().toString().split(" ")[0]);
                net.setNetworkAddress(IPUtils.getPartedAddressFromBinary(IPUtils.getNetworkAddress(ip_address.getText().toString(), net.getMaskAddress())));
                net.setBroadcastAddress(IPUtils.getBroadcastAddress(net.getNetworkAddress(), net.getMaskAddress()));
                net.setHostsAmount(IPUtils.getHostAmount(net.getMaskAddress()));
                net.setSubnetsAmount(IPUtils.getSubnetsAmount(net.getMaskAddress()));
                net.setAddress_class(String.valueOf(IPUtils.getAddressClass(net.getNetworkAddress())));

                MainActivity.adapter_items.add(net);
                MainActivity.mAdapter.notifyItemInserted(1);

                int mask = Integer.valueOf(mask_selector.getSelectedItem().toString().split("/")[1]);
                if (mask != 8 && mask != 16 && mask != 24) {
                    for(Subnet s : IPUtils.getSubnetsB(net.getNetworkAddress(), net.getMaskAddress())) {
                        MainActivity.adapter_items.add(s);
                        MainActivity.mAdapter.notifyItemInserted(MainActivity.adapter_items.size()-1);
                    }

                }
                else {
                    net.setSubnetsAmount(1);
                }

                InputMethodManager imm = (InputMethodManager)itemView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(itemView.getWindowToken(), 0);
            }
        });
    }

}
