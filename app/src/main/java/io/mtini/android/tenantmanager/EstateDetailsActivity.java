package io.mtini.android.tenantmanager;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;

import io.mtini.android.adaptor.TenantListBindingAdapter;
import io.mtini.android.tenantmanager.databinding.ActivityDetailsEstateBinding;
import io.mtini.model.AppDAO;
import io.mtini.model.AppDAOInterface;
import io.mtini.model.EstateModel;
import io.mtini.model.TenantModel;

public class EstateDetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details_estate);

        final Context currentContext = this;

        AppDAOInterface dbHelper = new AppDAO(this);

        try {
            dbHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final EstateModel estate = (EstateModel)getIntent().getSerializableExtra("estate");
        assert estate!=null;

        String estateName = estate.getName();
        List<TenantModel> tenantList = dbHelper.getTenantList(estate);

        int count = tenantList.size();
        estate.setTenantCount(count);
        TextView estateSizeTxtView = (TextView)findViewById(R.id.textEstateDetails);
        estateSizeTxtView.setText(estateName+" has "+count+" tenants.");

        //This binds EstateModel to the UI as an estate object. Allows for ${estate.description} data extraction
        ActivityDetailsEstateBinding  binding = DataBindingUtil.setContentView(this,R.layout.activity_details_estate);
        binding.setEstate(estate);

        //No bind the list of Tenants using an adapter
        ListView listView = (ListView) findViewById(R.id.tenantListView);
        TenantListBindingAdapter dataAdapter = new TenantListBindingAdapter(
        this,tenantList,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(currentContext, TenantDetailsActivity.class);
                        TenantModel tag = (TenantModel)v.getTag();
                        //Object h = v.getHandler();
                        intent.putExtra("tenant",tag);
                        intent.putExtra("estate",estate);
                        startActivity(intent);

                        /*PendingIntent pendingIntent =
                                TaskStackBuilder.create(currentContext)
                                        .addNextIntentWithParentStack(intent)
                                        .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                        //NotificationCompat.Builder builder = new NotificationCompat.Builder(EstateDetailsActivity.this);
                        //builder.setContentIntent(pendingIntent);
                        //builder.build();
                        try {
                            pendingIntent.send();
                        }catch(PendingIntent.CanceledException e){
                            throw new RuntimeException(e.getMessage(),e);
                        }*/
                        finish();
                    }
                });
        listView.setAdapter(dataAdapter);


    }


}
