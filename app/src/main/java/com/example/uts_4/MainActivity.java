package com.example.uts_4;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.constraintlayout.widget.ConstraintLayout;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.ProgressBar;

        import com.google.android.material.floatingactionbutton.FloatingActionButton;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.ChildEventListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

        import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.ItemClickInterface{

    private RecyclerView RV;
    private FloatingActionButton fab;
    private FirebaseDatabase fDB;
    private DatabaseReference fRef;
    private ArrayList<RecipeModel> modelArray;
    private ConstraintLayout main;
    private RecipeAdapter recipeAdapter;
    private ImageView LogoutBtn;

    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RV = findViewById(R.id.idRVmain);
        fab = findViewById(R.id.idFab);
        LogoutBtn = findViewById(R.id.LogoutBtn);
        fDB = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fRef = fDB.getReference("Recipe");
        modelArray = new ArrayList<>();
        recipeAdapter = new RecipeAdapter(modelArray,this,this);
        RV.setLayoutManager(new LinearLayoutManager(this));
        RV.setAdapter(recipeAdapter);

        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddRecipeActivity.class));
            }
        });
    }

    public void fetchRecipe(){
        modelArray.clear();
        fRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                modelArray.add(snapshot.getValue(RecipeModel.class));
                recipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                recipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                recipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                recipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClick(int pos) {
        displayFrag(modelArray.get(pos));
    }

    private void displayFrag(RecipeModel model){
        DialogFragment dialogFragment = new DialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("recipe", model);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getSupportFragmentManager(),"MyFragment");
    }

    @Override
    protected void onResume() {
        fetchRecipe();
        super.onResume();
    }
}