package com.bwldr.emijit.util;


import com.bwldr.emijit.data.ListRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * To store utility methods commonly used with Firebase.
 */
public class FirebaseUtil {

    private FirebaseUtil() {
        throw new AssertionError("don't allow instantiation, this is a utility class");
    }

    private static final String ANONYMOUS = "anonymous";

    public static DatabaseReference userEmails() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        return firebaseDatabase.getReference().child(currentUserId()).child("emails");
    }

    public static Query userEmailsList() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        return firebaseDatabase.getReference().child(currentUserId())
                .child("emails")
                .orderByChild("date")
                .limitToLast(Constants.EMAIL_LIST_LIMIT);
    }

    private static String currentUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        }
        return ANONYMOUS;
    }

    public static void saveListRequest(FirebaseUser user) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("list-requested");
        databaseReference.push().setValue(new ListRequest(user.getUid(), user.getEmail()));
    }
}
