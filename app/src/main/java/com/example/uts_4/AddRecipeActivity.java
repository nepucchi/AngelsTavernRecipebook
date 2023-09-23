package com.example.uts_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddRecipeActivity extends AppCompatActivity {

    private EditText title,desc,ingredient;
    private RadioGroup type;
    private Button submit;
    private FirebaseDatabase fDB;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        title = findViewById(R.id.idTitle);
        desc = findViewById(R.id.idDesc);
        ingredient = findViewById(R.id.idIngredient);
        type = findViewById(R.id.idTypeGroup);
        submit = findViewById(R.id.idSubmitRecipe);
        fDB = FirebaseDatabase.getInstance();
        databaseReference = fDB.getReference("Recipe");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RadioButton radioButton = (RadioButton) findViewById(type.getCheckedRadioButtonId());
                String recipeTitle = title.getText().toString();
                String recipeDesc = desc.getText().toString();
                String recipeIngre = ingredient.getText().toString();
                String foodType = radioButton.getText().toString();
                String recipeID = recipeTitle;
                RecipeModel model = new RecipeModel(recipeID,recipeTitle,foodType,recipeDesc,recipeIngre);
                valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child(recipeID).setValue(model);
                        Toast.makeText(AddRecipeActivity.this, "recipe added", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(AddRecipeActivity.this, MainActivity.class);
                        startActivity(i);
                        databaseReference.removeEventListener(valueEventListener);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddRecipeActivity.this, "Error: "+ error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}