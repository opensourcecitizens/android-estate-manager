package io.mtini.android.tenantmanager;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import io.mtini.android.tenantmanager.databinding.ActivityDetailsEstateBinding;
import io.mtini.android.tenantmanager.dialog.EditEstateDetailsDialogFragment;
import io.mtini.model.AppDAO;
import io.mtini.model.AppDAOInterface;
import io.mtini.model.EstateModel;
import io.mtini.model.TenantModel;

public class EstateDetailsActivity extends AppCompatActivity implements EditEstateDetailsDialogFragment.OnEditEstateListener {


    public static String TAG = Class.class.getSimpleName();

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
        Button viewTenantBtn = (Button) findViewById(R.id.view_tenantlist_button);
        viewTenantBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(currentContext, FragmentedEstateDetailsActivity.class);
                TenantModel tag = (TenantModel)v.getTag();
                //Object h = v.getHandler();
                intent.putExtra("estate",estate);
                startActivity(intent);
            }
        });


        Button editButton = (Button) findViewById(R.id.edit_estate_button );
        editButton.setOnClickListener(new View.OnClickListener(){
            final EstateModel editEstate = estate;
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                EditEstateDetailsDialogFragment editEstateDialogFragment = new EditEstateDetailsDialogFragment();
                Bundle args = new Bundle();
                args.putSerializable(EditEstateDetailsDialogFragment.ARG_EDIT_ESTATE, editEstate);
                editEstateDialogFragment.setArguments(args);
                editEstateDialogFragment.show(fm, "fragment_edit_estate");
            }
        });

    }


    @Override
    public void onEstateEdited(EstateModel newEstate, EstateModel oldEstate) {
        if(newEstate.toString().equalsIgnoreCase(oldEstate.toString())){
            Toast.makeText(this, "No changes detected.", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(this, newEstate.getName()+" info has been updated.", Toast.LENGTH_SHORT).show();
            //ProgressDialog progress = DialogUtils.startProgressDialog(this);
            //onTenantSelected(newtenant);
            updateDB(newEstate);
            viewEstateDetailsDialog(newEstate);

            //progress.dismiss();
        }
    }

    private void updateDB(EstateModel estate){
        AppDAOInterface dbHelper = new AppDAO(this);

        try {
            dbHelper.open();
        } catch (SQLException e) {

            Log.e(TAG,e.getLocalizedMessage());
        }
        if(dbHelper.getEstateById(estate.getId()+"")!=null)
            dbHelper.updateEstate(estate);
        else
            dbHelper.addEstate(estate);

    }

    private void viewEstateDetailsDialog(EstateModel estate){

        //FragmentManager fm = getFragmentManager();
        //EditEstateDetailsDialogFragment editEstateDialogFragment = new EditEstateDetailsDialogFragment();
        //Bundle args = new Bundle();
        //args.putSerializable(EditEstateDetailsDialogFragment.ARG_EDIT_ESTATE, estate);
        //editEstateDialogFragment.setArguments(args);
        //editEstateDialogFragment.show(fm, "fragment_edit_estate");
        Intent intent = getIntent();//new Intent(EstateActivity.this, EstateDetailsActivity.class);
        intent.putExtra("estate",estate);
        startActivity(intent);
    }

}
