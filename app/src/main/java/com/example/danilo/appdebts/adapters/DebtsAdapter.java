package com.example.danilo.appdebts.adapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.danilo.appdebts.R;
import com.example.danilo.appdebts.classes.Debts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael Sousa (github.com/rafasilvasousa) on 03/07/19.
 */

public class DebtsAdapter extends RecyclerView.Adapter<DebtsAdapter.ViewHolderDebts>{
    private List<Debts> mData;
    private List<ViewHolderDebts> mDataViews = new ArrayList<ViewHolderDebts>();
    private int mSelectedItem = -1; //indice do ult viewholder selecionado.
    private int mActualItem = -1; //indice do atual viewholder selecionado.
    public DebtsAdapter(List<Debts> data) {
        mData = data;
    }

    private int mDescription;
    private int mButtonVenc;
    private int mButtonRefresh;
    private int mButtonDelete;


    @NonNull
    @Override
    public DebtsAdapter.ViewHolderDebts
        onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_view_debts, parent, false);
        ViewHolderDebts holderDebts = new ViewHolderDebts(view);

        mDataViews.add(holderDebts);

        return holderDebts;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDebts holder, int position) {
        if (mData != null && mData.size() > 0) {
            Debts debt = mData.get(position);
            holder.mDescription.setText(debt.getDescription());
        }
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }
    public class ViewHolderDebts extends RecyclerView.ViewHolder {
        public TextView mDescription;
        public ImageButton mButtonVenc;
        public ImageButton mButtonRefresh;
        public ImageButton mButtonDelete;
        public TextView mPayment;
        public TextView mPay;


        public ViewHolderDebts(View itemView) {
            super(itemView);
            mDescription = (TextView) itemView.findViewById(R.id.textViewCategory);
            mButtonVenc = itemView.findViewById(R.id.imageButtonPayment);
            mButtonRefresh = itemView.findViewById(R.id.imageButtonRefresh);
            mButtonDelete = itemView.findViewById(R.id.imageButtonDelete);
            mPayment = itemView.findViewById((R.id.textViewPaymentDay));
            mPay = itemView.findViewById((R.id.textViewPayDay));

        }
    }

}
