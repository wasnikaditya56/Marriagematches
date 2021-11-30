package com.wasnikaditya.marriagematches;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

//Adapter extends Custom View holder class defined on the bottom of this code.
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVHOLDER> {
    List<RecyclerViewData> recyclerViewDataList;
    Context mContext;

    // Constructor with List and Context which we'll pass from RecyclerView Activity after a connection to Volley. And application context for the listener that we'll implement this later.
    public RVAdapter(List<RecyclerViewData> recyclerViewDataList, Context mContext)
    {
        this.recyclerViewDataList = recyclerViewDataList;
        this.mContext = mContext;
    }

    // Override the method onCreateViewHolder, which will call the custom view holder that needs to be initialized. We specify the layout that each item to be used, so we can achieve this using Layout Inflator to inflate the layout and passing the output to constructor of custom ViewHolder.
    @NonNull
    @Override
    public RVHOLDER onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.adapter_layout, viewGroup, false);
        RVHOLDER rvholder = new RVHOLDER(itemView);
        return rvholder;
    }


    //onBindViewHolder is for specifying the each item of RecyclerView. This is very similar to getView() method on ListView. In our example, this is where you need to set the user's id, name and image.
    @Override
    public void onBindViewHolder(@NonNull RVHOLDER rvholder, int i)
    {
       // rvholder.id.setText("User id is "+recyclerViewDataList.get(i).getId());
        rvholder.name.setText("Name :"+recyclerViewDataList.get(i).getName());
        rvholder.email.setText("Email: "+recyclerViewDataList.get(i).getEmail());
        rvholder.gender.setText("Gender :"+recyclerViewDataList.get(i).getGender());
        rvholder.birthdate.setText("Dob :"+recyclerViewDataList.get(i).getBirthdate());
        rvholder.age.setText("Age :"+recyclerViewDataList.get(i).getAge());
        Picasso.get().load(recyclerViewDataList.get(i).getImage()).into(rvholder.image);

        rvholder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(mContext, "Accept !!! " + i, Toast.LENGTH_SHORT).show();

                final int status =(Integer) view.getTag();
                if(status == 1) {

                    rvholder.accept.setText("Membered accepted");
                    view.setTag(0); //pause
                } else {
                    rvholder.accept.setText("Accept");
                    view.setTag(1); //pause
                }


            }
        });

        rvholder.decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // rvholder.decline.setText("Membered declined");
                Toast.makeText(mContext, "Decline !!! " + i, Toast.LENGTH_SHORT).show();

               /* if(view.getId() == R.id.decline) {
                    rvholder.decline.setText("Membered declined");
                }else if(view.getId() == R.id.decline){
                    rvholder.decline.setText("Decline");
                }*/

                final int status =(Integer) view.getTag();
                if(status == 1) {

                    rvholder.decline.setText("Membered declined");
                    view.setTag(0); //pause
                } else {
                    rvholder.decline.setText("Decline");
                    view.setTag(1); //pause
                }

            }
        });

    }

    //We need to return the size for RecyclerView as how long a RecyclerView is, Our data is in list so passing data.size() will return the number as long as we have.
    @Override
    public int getItemCount() {
        return recyclerViewDataList.size();
    }

    //This is CustomView holder that we had discuss it earlier above and inflated it in onCreateView() method. This constructor links with the xml to set the data, which we set into onBindViewHolder().
    class RVHOLDER extends RecyclerView.ViewHolder
    {
        Button accept, decline;
        private TextView name, email, gender, birthdate, age;
        private ImageView image;

        public RVHOLDER(@NonNull View itemView) {
            super(itemView);
            //id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            gender = itemView.findViewById(R.id.gender);
            birthdate = itemView.findViewById(R.id.birthdate);
            age = itemView.findViewById(R.id.age);
            image = itemView.findViewById(R.id.image);
            accept = itemView.findViewById(R.id.accept);
            decline = itemView.findViewById(R.id.decline);
            accept.setTag(1);
            accept.setText("Accept");

            decline.setTag(1);
            decline.setText("Decline");
        }
    }
}