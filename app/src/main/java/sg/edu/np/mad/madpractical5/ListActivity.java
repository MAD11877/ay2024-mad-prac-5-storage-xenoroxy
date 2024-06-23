package sg.edu.np.mad.madpractical5;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import sg.edu.np.mad.madpractical.R;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialise ArrayList of Users
        DatabaseHandler dbHandler = new DatabaseHandler(this, null, null, 1);
        dbHandler.initWithUsers();
        ArrayList<User> users = dbHandler.getAllUsers();

        Log.d("AA", users.toString());
        if (users.isEmpty()) {
            return;
        }

        // Populate RecyclerView with Data
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        UserAdapter userAdapter = new UserAdapter(users, this);
        LinearLayoutManager userLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(userLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(userAdapter);
    }
}