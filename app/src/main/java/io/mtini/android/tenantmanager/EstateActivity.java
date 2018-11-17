package io.mtini.android.tenantmanager;


import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import io.mtini.android.adaptor.EstateListBindingAdapter;
import io.mtini.model.AppDAO;
import io.mtini.model.AppDAOInterface;
import io.mtini.model.EstateModel;
import io.mtini.model.TenantModel;

public class EstateActivity extends AppCompatActivity {

    //private ViewDataBinding activityEstateBinding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_estates);

        AppDAOInterface dbHelper = new AppDAO(this);

        try {
            dbHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*
        List<EstateModel> estateList = ((AppDAO) dbHelper).getMyEstateList();
        //START Cleanup mechanism
        for(EstateModel estate: estateList) {
            List<TenantModel> tenantList = ((AppDAO) dbHelper).getTenantList(estate);
            for(TenantModel tenant: tenantList){
                ((AppDAO) dbHelper).deleteTenant(tenant,estate);
            }
            ((AppDAO) dbHelper).deleteEstate(estate);
        }
        //END
        */
        if(((AppDAO) dbHelper).getMyEstateList().size()==0)
            ((AppDAO) dbHelper).uploadData();


        ListView listView = (ListView) findViewById(R.id.estateslistView);

        ObservableList<EstateModel> estates = new ObservableArrayList();
        estates.addAll(dbHelper.getMyEstateList());

        int count = estates.size();
        TextView estateSizeTxtView = (TextView)findViewById(R.id.textEstateCount);
        estateSizeTxtView.setText("You have "+count+" estates under your management");

        ////START NB this works fine
        EstateListBindingAdapter dataAdapter = new EstateListBindingAdapter(this,estates,
                new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(EstateActivity.this, FragmentedEstateDetailsActivity.class);
                                EstateModel tag = (EstateModel)v.getTag();
                                intent.putExtra("estate",tag);
                                startActivity(intent);
                    }
                });

        listView.setAdapter(dataAdapter);
        ////END

        ////START
        //GenericItemListBindingAdapter.setEntries(listView,estates,R.layout.estate_layout);
        ////END

    }

}
