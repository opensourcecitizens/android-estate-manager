package io.mtini.android.tenantmanager;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;

import io.mtini.android.adaptor.TenantListBindingAdapter;
import io.mtini.android.tenantmanager.databinding.ActivityDetailsEstateBinding;
import io.mtini.android.tenantmanager.databinding.ActivityDetailsTenantBinding;
import io.mtini.model.AppDAO;
import io.mtini.model.AppDAOInterface;
import io.mtini.model.EstateModel;
import io.mtini.model.TenantModel;

public class TenantDetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details_tenant);

        AppDAOInterface dbHelper = new AppDAO(this);

        try {
            dbHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //EstateModel estate = (EstateModel)getIntent().getSerializableExtra("estate");
        TenantModel tenant = (TenantModel)getIntent().getSerializableExtra("tenant");
        //String tenantName = tenant.getName();
        //This binds TenantModel to the UI as an estate object. Allows for ${tenant.name} data extraction
        ActivityDetailsTenantBinding  binding = DataBindingUtil.setContentView(this,R.layout.activity_details_tenant);
        binding.setTenant(tenant);
        //binding.setEstate(estate);

        //TODO add estate setting on back button

        /*
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
                        Intent intent = new Intent(TenantDetailsActivity.this, TenantDetailsActivity.class);
                        EstateModel tag = (EstateModel)v.getTag();
                        //Object h = v.getHandler();
                        intent.putExtra("estate",tag);
                        startActivity(intent);
                    }
                });
        listView.setAdapter(dataAdapter);
        */
    }


}
