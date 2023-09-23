package com.example.uts_4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditRecipeActivity extends AppCompatActivity {

    private EditText title, desc, ingredients;
    private RadioGroup type;
    private Button edit, delete;
    private FirebaseDatabase fDB;
    private DatabaseReference databaseReference;
    private String recipeID;
    private RecipeModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);
        title = findViewById(R.id.idEditTitle);
        desc = findViewById(R.id.idEditDesc);
        ingredients = findViewById(R.id.idEditIngredient);
        type = findViewById(R.id.idEditTypeGroup);
        edit = findViewById(R.id.idEditBtn);
        delete = findViewById(R.id.idDeleteBtn);
        RadioButton radioButton = (RadioButton) findViewById(type.getCheckedRadioButtonId());
        fDB = FirebaseDatabase.getInstance();

        model = getIntent().getParcelableExtra("recipe");
        if(model!=null){
            title.setText(model.getRecipeTitle());
            desc.setText(model.getRecipeDesc());
            ingredients.setText(model.getRecipeIngre());
            radioButton.setChecked(true);
            recipeID = model.getRecipeID();
        }

        databaseReference = fDB.getReference("Recipe").child(recipeID);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editTitle = title.getText().toString();
                String editDesc = desc.getText().toString();
                String editIngre = ingredients.getText().toString();
                String editType = radioButton.getText().toString();

                RecipeModel EditRecipe = new RecipeModel(recipeID, editTitle,editType,editDesc,editIngre);
                Map<String, Object> map = new HashMap<>();
                map.put("recipeDesc", editDesc);
                map.put("recipeID", recipeID);
                map.put("recipeIngre", editIngre);
                map.put("recipeTitle", editTitle);
                map.put("recipeType", editType);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditRecipeActivity.this, "Recipe Successfully edited!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(EditRecipeActivity.this, MainActivity.class);
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditRecipeActivity.this, "Error: "+ error.toString(), Toast.LENGTH_SHORT).show();
                    }

                });
            }
        });
        /*delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeValue();
                Intent i = new Intent(EditRecipeActivity.this, MainActivity.class);
                startActivity(i);
            }
        });*/

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if (error == null) {
                            Toast.makeText(EditRecipeActivity.this, "Recipe Successfully deleted!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(EditRecipeActivity.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(EditRecipeActivity.this, "Error: "+ error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}