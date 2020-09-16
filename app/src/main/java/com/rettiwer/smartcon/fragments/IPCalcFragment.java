package com.rettiwer.smartcon.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rettiwer.smartcon.IPAdapter;
import com.rettiwer.smartcon.MainActivity;
import com.rettiwer.smartcon.R;

public class IPCalcFragment extends Fragment {
    private RecyclerView recyclerView;
    private OnFragmentInteractionListener mListener;
    private Parcelable recyclerViewState;

    public static IPCalcFragment newInstance(String param1, String param2) {
        IPCalcFragment fragment = new IPCalcFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_ipcalc, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.ipcalc_items);
        ((MainActivity)getActivity()).setAdapter(new IPAdapter(MainActivity.adapter_items));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(((MainActivity)getActivity()).getAdapter());
        recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();

        MainActivity.adapter_items.add("NETWORK_INFO_INPUT");
        ((MainActivity)getActivity()).getAdapter().notifyDataSetChanged();
        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
