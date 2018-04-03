package com.bwldr.emijit.emails;

import com.bwldr.emijit.data.Email;

import java.util.List;


// TODO: will hook in after getting RecyclerView working

public interface EmailsContract {

    interface View {

        void setProgressIndicator(boolean active);

        void showEmails(List<Email> emails);
    }

    interface UserActionsListener {

        void loadEmails(boolean forceUpdate);
    }
}
