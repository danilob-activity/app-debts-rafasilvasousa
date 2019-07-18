package com.example.danilo.appdebts.adapters;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.danilo.appdebts.DAO.DebtsDAO;
import com.example.danilo.appdebts.InsertDebts;
import com.example.danilo.appdebts.R;
import com.example.danilo.appdebts.classes.Debts;
import com.example.danilo.appdebts.database.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Rafael Sousa (github.com/rafasilvasousa) on 03/07/19.
 */

public class DebtsAdapter extends RecyclerView.Adapter<DebtsAdapter.ViewHolderDebts> {
    private List<Debts> mData;
    private List<ViewHolderDebts> mDataViews = new ArrayList<ViewHolderDebts>();
    private int mSelectedItem = -1; //indice do ult viewholder selecionado.
    private int mActualItem = -1; //indice do atual viewholder selecionado.

    public DebtsAdapter(List<Debts> data) {
        mData = data;
    }

    private DebtsAdapter mDebtsAdapter = this;
    private Context mContext;

    final Calendar mCalendar = Calendar.getInstance();

//    private int mDescription;
//    private int mButtonVenc;
//    private int mButtonRefresh;
//    private int mButtonDelete;


    @NonNull
    @Override
    public DebtsAdapter.ViewHolderDebts
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
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
            holder.mCategory.setText(debt.getCategory().getType());
            holder.mDataPay.setText(debt.getExpire_date());
            holder.mDataPayment.setText(debt.getPayment_date());
            if (debt.getExpire_date().isEmpty()) {
                holder.mTextPay.setVisibility(View.GONE);
            } else {
                holder.mTextPay.setVisibility(View.VISIBLE);
            }
        }
    }

    public void updateViewHolderLast() {
        ViewHolderDebts holder = mDataViews.get(mSelectedItem);
        holder.mButtonUpdate.setVisibility(View.GONE);
        holder.mButtonPay.setVisibility(View.GONE);
        holder.mButtonDelete.setVisibility(View.GONE);
        holder.mLayout.setBackgroundColor(mContext.getResources().getColor(android.R.color.white));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolderDebts extends RecyclerView.ViewHolder {
        public TextView mDescription;
        public TextView mCategory;
        public TextView mDataPay;
        public TextView mDataPayment;
        public TextView mTextPayment;
        public TextView mTextPay;
        public ImageButton mButtonUpdate;
        public ImageButton mButtonPay;
        public ImageButton mButtonDelete;
        public ConstraintLayout mLayout;


        public ViewHolderDebts(View itemView) {
            super(itemView);
            mDescription = itemView.findViewById(R.id.textViewDescription);
            mCategory = itemView.findViewById(R.id.textViewCategory);
            mDataPay = itemView.findViewById(R.id.textViewPaymentDay);
            mDataPayment = itemView.findViewById(R.id.textViewPayDay);
            mButtonPay = itemView.findViewById(R.id.imageButtonPayment);
            mButtonUpdate = itemView.findViewById(R.id.imageButtonRefresh);
            mButtonDelete = itemView.findViewById(R.id.imageButtonDelete);
            mTextPay = itemView.findViewById(R.id.textView2);
            mTextPayment = itemView.findViewById(R.id.textView4);
            mLayout = itemView.findViewById(R.id.linearLayout);

            mButtonPay.setVisibility(View.GONE);
            mButtonUpdate.setVisibility(View.GONE);
            mButtonDelete.setVisibility(View.GONE);

            mButtonPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int day) {
                            mCalendar.set(Calendar.YEAR, year);
                            mCalendar.set(Calendar.MONTH, month);
                            mCalendar.set(Calendar.DAY_OF_MONTH, day);
                            String myFormat = "dd/MM/yyyy";
                            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                            String dataPay = sdf.format(mCalendar.getTime());
                            if (mData.size() > 0) {
                                Debts debt = mData.get(getLayoutPosition());
                                debt.setPayment_date(dataPay);
                                DatabaseHelper mDataHelper = new DatabaseHelper(mContext);
                                SQLiteDatabase mConnection = mDataHelper.getWritableDatabase();
                                DebtsDAO debtsDAO = new DebtsDAO(mConnection);
                                debtsDAO.alter(debt);
                                mDebtsAdapter.notifyItemChanged(getLayoutPosition());
                            }
                        }
                    };

                    new DatePickerDialog(mContext, date, mCalendar
                            .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }

            });

            mButtonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mData.size() > 0) {
                        Debts debt = mData.get(getLayoutPosition());
                        DatabaseHelper mDataHelper = new DatabaseHelper(mContext);
                        SQLiteDatabase mConnection = mDataHelper.getWritableDatabase();
                        DebtsDAO debtsDAO = new DebtsDAO(mConnection);
                        debtsDAO.remove(debt.getId());
                        mData.remove(getLayoutPosition());
                        mDebtsAdapter.notifyItemRemoved(getLayoutPosition());
                    }
                }
            });

            mButtonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mData.size() > 0) {
                        Debts debts = mData.get(getLayoutPosition());
                        Intent intent = new Intent(mContext, InsertDebts.class);
                        intent.putExtra("DEBT", debts);
                        ((AppCompatActivity) mContext).startActivityForResult(intent, 1001);
                    }
                }
            });

            mDescription.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActualItem = getLayoutPosition();
                    if (mSelectedItem != -1 && mActualItem != mSelectedItem) {
                        updateViewHolderLast();
                    }
                    if (mButtonDelete.getVisibility() == View.GONE) {
                        mButtonPay.setVisibility(View.VISIBLE);
                        mButtonUpdate.setVisibility(View.VISIBLE);
                        mButtonDelete.setVisibility(View.VISIBLE);
                        mLayout.setBackgroundColor(mContext.getResources().getColor(R.color.selectedItem));

                    } else {
                        mButtonPay.setVisibility(View.GONE);
                        mButtonUpdate.setVisibility(View.GONE);
                        mButtonDelete.setVisibility(View.GONE);
                        mLayout.setBackgroundColor(mContext.getResources().getColor(android.R.color.white));

                    }
                    mSelectedItem = mActualItem;

                    
                }
            });


        }
    }


}
