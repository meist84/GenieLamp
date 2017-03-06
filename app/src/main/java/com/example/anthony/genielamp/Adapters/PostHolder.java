package com.example.anthony.genielamp.Adapters;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import com.example.anthony.genielamp.R;

public class PostHolder extends ViewHolder {
    private View mView;
    private TextView mImage;
    private TextView mTitle;
    private TextView mDescription;
    private TextView mEmail;

    public PostHolder(View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setImage(String Image) {
        this.mImage.setText(Image);
    }

    public void setTitle(String Title) {
        mTitle = (TextView) itemView.findViewById(R.id.posttitle);
        this.mTitle.setText(Title);
    }

    public void setDescription(String Description) {
        mDescription = (TextView) itemView.findViewById(R.id.postdescription);
        this.mDescription.setText(Description);
    }

    public void setEmail(String Email) {
        mEmail = (TextView) itemView.findViewById(R.id.postemail);
        this.mEmail.setText(Email);
    }

}
