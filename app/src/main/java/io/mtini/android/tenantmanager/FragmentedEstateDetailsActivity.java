package io.mtini.android.tenantmanager;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import io.mtini.android.tenantmanager.dialog.EditTenantDetailsDialogFragment;
import io.mtini.android.tenantmanager.fragments.TenantDetailsFragment;
import io.mtini.android.tenantmanager.fragments.TenantListFragment;
import io.mtini.model.AppDAO;
import io.mtini.model.AppDAOInterface;
import io.mtini.model.EstateModel;
import io.mtini.model.TenantModel;

public class FragmentedEstateDetailsActivity extends AppCompatActivity//FragmentActivity
        implements TenantListFragment.OnTenantSelectedListener , EditTenantDetailsDialogFragment.OnEditTenantListener{

    public static String  TAG = Class.class.getSimpleName();

    boolean twinView = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_tenant_list );

        if (findViewById( R.id.fragment_tenant_list ) != null) {
            if (savedInstanceState != null) {
                return;
            }

            TenantListFragment tenantListFragment = new TenantListFragment();
            tenantListFragment.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_tenant_list, tenantListFragment).commit();

        }


        final EstateModel estate = (EstateModel)getIntent().getSerializableExtra("estate");
        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.add_tenant_button );
        addButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                UUID uuid = UUID.randomUUID();

                TenantModel newTenant = new TenantModel(uuid,estate.getId(),"","","",new Date(),TenantModel.STATUS.new_tenant,null,null,"");
                FragmentManager fm = getFragmentManager();
                EditTenantDetailsDialogFragment editNameDialogFragment = new EditTenantDetailsDialogFragment();
                Bundle args = new Bundle();
                args.putSerializable(EditTenantDetailsDialogFragment.ARG_EDIT_TENANT, newTenant);
                editNameDialogFragment.setArguments(args);
                editNameDialogFragment.show(fm, "fragment_edit_name");
                //replace edit button with add button

            }
        });



    }

    public void onTenantSelected(TenantModel tenant) {

        //setup fragmentManager : two ways
        //FragmentManager fgmtMgr = getSupportFragmentManager();
        FragmentManager fgmtMgr = getFragmentManager();

        TenantDetailsFragment tenantFrag = (TenantDetailsFragment)
                fgmtMgr.findFragmentById(R.id.fragment_tenant_detail);

        if (tenantFrag != null ) {

            tenantFrag.updateTenantView(tenant);

        } else {

            TenantDetailsFragment newFragment = new TenantDetailsFragment();
            Bundle args = new Bundle();
            args.putSerializable(TenantDetailsFragment.ARG_SELECTED_TENANT, tenant);
            newFragment.setArguments(args);

            FragmentTransaction transaction = fgmtMgr.beginTransaction();

            if(twinView){

                transaction.replace(R.id.fragment_tenant_detail, newFragment);

            }else{

                transaction.replace(R.id.fragment_tenant_list, newFragment);
                transaction.addToBackStack(null);

            }
            // Commit the transaction
            transaction.commit();
        }
    }


    /**
     * If newtenant == oldtenant ;
     *      show timed dialog with "No changes"
     *      do nothing
     * else
     *      show wait dialog "Wait. Updating."
     *      call db
     *      update list
     *      //change fragment title to "update review"
     *      navigate to list ??
     *
     *      */
    @Override
    public void onTenantEdited(TenantModel newtenant,TenantModel oldtenant) {

        if(newtenant.toString().equalsIgnoreCase(oldtenant.toString())){
            Toast.makeText(this, "No changes detected.", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(this, newtenant.getName()+" info has been updated.", Toast.LENGTH_SHORT).show();
            //ProgressDialog progress = DialogUtils.startProgressDialog(this);
            //onTenantSelected(newtenant);
            updateDB(newtenant);
            navigateToList();

            //progress.dismiss();
        }


    }

    private void updateDB(TenantModel tenant){
        AppDAOInterface dbHelper = new AppDAO(this);

        try {
            dbHelper.open();
        } catch (SQLException e) {

            Log.e(TAG,e.getLocalizedMessage());
        }
        if(dbHelper.getTenantById(tenant.getId()+"")!=null)
            dbHelper.updateTenant(tenant);
        else
            dbHelper.addTenant(tenant,null);

    }

    private void navigateToList(){

        FragmentManager fgmtMgr = getFragmentManager();

        Fragment oldFragment = fgmtMgr.findFragmentById(R.id.fragment_tenant_list);

        FragmentTransaction transaction = fgmtMgr.beginTransaction();

        Fragment newFragment = new TenantListFragment();

        if(oldFragment != null && oldFragment instanceof TenantListFragment){

            transaction.replace(R.id.fragment_tenant_list, newFragment);

        }else{

            transaction.add(R.id.fragment_tenant_list, newFragment);

        }

        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {

        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else if (getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }

    }
}
