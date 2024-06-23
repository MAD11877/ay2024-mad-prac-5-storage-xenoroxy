package sg.edu.np.mad.madpractical5;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import sg.edu.np.mad.madpractical.R;


public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {
    private ArrayList<User> listObjects;
    private ListActivity activity;

    public UserAdapter(ArrayList<User> listObjects, ListActivity activity) {
        this.listObjects = listObjects;
        this.activity = activity;
    }

    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_activity_list, parent, false);
        UserViewHolder holder = new UserViewHolder(view);
        return holder;
    }

    public void onBindViewHolder(UserViewHolder holder, int position) {
        User listItem = listObjects.get(position);
        holder.name.setText(listItem.getName());
        holder.description.setText(listItem.getDescription());
        // Display Dialog when Image Clicked
        holder.smallImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Profile")
                        .setMessage(listItem.getName())
                        .setPositiveButton("Close", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .setNegativeButton("View", (dialog, which) -> {
                            Intent intent = new Intent(v.getContext(), MainActivity.class);
                            intent.putExtra("id", listItem.getId());
                            intent.putExtra("name", listItem.getName());
                            intent.putExtra("description", listItem.getDescription());
                            intent.putExtra("followed", listItem.getFollowed());
                            v.getContext().startActivity(intent);
                        });

                builder.create().show();
            }
        });
        // Check if ID contains 7, if yes, show image
        String userId = String.valueOf(listItem.getId());
        if (userId.contains("7")) {
            holder.bigImage.setVisibility(View.VISIBLE);
        }
    }

    public int getItemCount() { return listObjects.size(); }
}