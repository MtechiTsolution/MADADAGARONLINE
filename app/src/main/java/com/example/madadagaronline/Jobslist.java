package com.example.madadagaronline;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.madadagaronline.Models.MyUser;
import com.example.madadagaronline.placeholder.PlaceholderContent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A fragment representing a list of Items.
 */
public class Jobslist extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference dr;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public Jobslist() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static Jobslist newInstance(int columnCount) {
        Jobslist fragment = new Jobslist();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAuth= FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        dr= FirebaseDatabase.getInstance().getReference();
        View view = inflater.inflate(R.layout.fragment_jobslist_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            //Toast.makeText(getActivity(),""+ PlaceholderContent.ITEMS.size(),Toast.LENGTH_SHORT).show();
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(getActivity(),PlaceholderContent.ITEMS));
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        PlaceholderContent.ITEMS.clear();
        dr.child("Users").orderByChild("status").equalTo("mazdor").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

               if(snapshot.exists()){
                   MyUser myUser=snapshot.getValue(MyUser.class);
                   if(myUser.getCatogory().equals(ListJobs_Act.catogory)){
                       PlaceholderContent.ITEMS.add(new PlaceholderContent.PlaceholderItem(myUser.getFirst_name(),myUser.getLast_name(),myUser.getPhonenumber()));
                   }
               }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}