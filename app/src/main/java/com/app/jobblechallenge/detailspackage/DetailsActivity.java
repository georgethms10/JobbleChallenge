package com.app.jobblechallenge.detailspackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.jobblechallenge.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    @Bind(R.id.detail_image)
    ImageView detailImage;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.desc)
    TextView desc;
    @Bind(R.id.cv_cat)
    CardView cvCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        setDetailstoUi(getIntent());
    }

    private void setDetailstoUi(Intent intent) {

        try {
            name.setText(intent.getStringExtra("name"));
            desc.setText(intent.getStringExtra("desc"));
            Picasso.with(DetailsActivity.this)
                    .load(intent.getStringExtra("image"))
                    .into(detailImage);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.three_,R.anim.four_);
    }
}
