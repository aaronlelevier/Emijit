package com.bwldr.emijit.emails;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwldr.emijit.R;
import com.bwldr.emijit.data.Email;
import com.bwldr.emijit.util.FirebaseUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class EmailsFragment extends Fragment {

    private View mView;
    private EmailsAdapter mListAdapter;

    public EmailsFragment() {
        // required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mListAdapter = new EmailsAdapter(new ArrayList<Email>(0));

        Query query = FirebaseUtil.userEmailsList();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    Email email = ds.getValue(Email.class);
                    mListAdapter.add(email);
                }
                mListAdapter.notifyDataSetChanged();
                setProgressIndicator(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_emails, container, false);

        initSwipeToRefreshListener();
        setProgressIndicator(true);

        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.emails_list);
        recyclerView.setAdapter(mListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        return mView;
    }

    private class EmailsAdapter extends RecyclerView.Adapter<EmailsAdapter.ViewHolder> {

        private List<Email> mEmails;

        public EmailsAdapter(List<Email> mEmails) {
            this.mEmails = mEmails;
        }

        @Override
        public EmailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View emailView = inflater.inflate(R.layout.item_email, parent, false);
            return new ViewHolder(emailView);
        }

        @Override
        public void onBindViewHolder(EmailsAdapter.ViewHolder viewHolder, int position) {
            Email email = mEmails.get(position);
            viewHolder.email.setText(email.getEmail());
        }

        @Override
        public int getItemCount() {
            return mEmails.size();
        }

        public void add(Email email) {
            this.mEmails.add(email);
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView email;

            ViewHolder(View itemView) {
                super(itemView);
                email = (TextView) itemView.findViewById(R.id.list_item_email);
            }
        }
    }

    private void setProgressIndicator(final boolean active) {
        if (mView == null) {
            return;
        }
        final SwipeRefreshLayout srl =
                (SwipeRefreshLayout) mView.findViewById(R.id.refresh_layout);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }

    /**
     * Add AppTheme colors for UI. Will only show 'onCreateView' and after that
     * 'pullToRefresh' is disabled.
     */
    private void initSwipeToRefreshListener() {
        SwipeRefreshLayout swipeRefreshLayout =
                (SwipeRefreshLayout) mView.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

        swipeRefreshLayout.setEnabled(false);
    }
}
