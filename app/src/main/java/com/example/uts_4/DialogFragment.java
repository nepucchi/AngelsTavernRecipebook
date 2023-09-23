package com.example.uts_4;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DialogFragment extends androidx.fragment.app.DialogFragment {

    private Button edit,delete;
    private FirebaseDatabase fDB;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootview = inflater.inflate(R.layout.fragment_custom_layout,container,false);
        edit = rootview.findViewById(R.id.idGotoEdit);
        delete = rootview.findViewById(R.id.idGotoDelete);
        fDB = FirebaseDatabase.getInstance();

        Bundle bundle = getArguments();
        RecipeModel model = bundle.getParcelable("recipe");
        databaseReference = fDB.getReference("Recipe").child(model.getRecipeTitle());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), EditRecipeActivity.class);
                i.putExtra("recipe", model);
                startActivity(i);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeValue();
                dismiss();
            }
        });
        return rootview;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        MainActivity home = (MainActivity) getActivity();
        if(home!=null){
            home.fetchRecipe();
        }

    }
}
