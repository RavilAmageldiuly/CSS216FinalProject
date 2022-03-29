package ravil.amangeldiuly.example.simpleregisterapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adaptery extends RecyclerView.Adapter<Adaptery.MyViewHolder>{

    private Context mContext;
    private List<Character> charactersList;

    public Adaptery(Context context, List<Character> charactersList) {
        mContext = context;
        this.charactersList = charactersList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        v = layoutInflater.inflate(R.layout.character_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameTextView.setText(charactersList.get(position).getName());
//        holder.birthdayTextView.setText(charactersList.get(position).getBirthday());
//        holder.statusTextView.setText(charactersList.get(position).getStatus());

        Glide.with(mContext)
                .load(charactersList.get(position).getImg())
                .into(holder.character_image);
    }

    @Override
    public int getItemCount() {
        return charactersList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nameTextView;
//        TextView birthdayTextView;
//        TextView statusTextView;
        ImageView character_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.nameTextView);
//            birthdayTextView = itemView.findViewById(R.id.birthdayTextView);
//            statusTextView = itemView.findViewById(R.id.statusTextView);
            character_image = itemView.findViewById(R.id.character_image);
        }
    }
}
