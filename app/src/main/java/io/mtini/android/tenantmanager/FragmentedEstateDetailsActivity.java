package io.mtini.android.tenantmanager;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.sql.SQLException;

import io.mtini.android.tenantmanager.dialog.DialogUtils;
import io.mtini.android.tenantmanager.dialog.TenantDetailsDialogFragment;
import io.mtini.android.tenantmanager.fragments.TenantDetailsFragment;
import io.mtini.android.tenantmanager.fragments.TenantListFragment;
import io.mtini.model.AppDAO;
import io.mtini.model.AppDAOInterface;
import io.mtini.model.TenantModel;

public class FragmentedEstateDetailsActivity extends AppCompatActivity//FragmentActivity
        implements TenantListFragment.OnTenantSelectedListener , TenantDetailsDialogFragment.OnEditTenantListener{

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


    }

    public void onTenantSelected(TenantModel tenant) {

        //setup fragmentManager : two ways
        //FragmentManager fgmtMgr = getSupportFragmentManager();
        FragmentManager fgmtMgr = getFragmentManager();

        // The user selected the headline of an article from the HeadlinesFragment

        // Capture the article fragment from the activity layout
        TenantDetailsFragment tenantFrag = (TenantDetailsFragment)
                fgmtMgr.findFragmentById(R.id.fragment_tenant_detail);

        if (tenantFrag != null ) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content
            tenantFrag.updateTenantView(tenant);

        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            TenantDetailsFragment newFragment = new TenantDetailsFragment();
            //newFragment.updateTenantView(tenant);//caused null pointer
            Bundle args = new Bundle();
            args.putSerializable(TenantDetailsFragment.ARG_SELECTED_TENANT, tenant);
            newFragment.setArguments(args);

            FragmentTransaction transaction = null;

            //Now remove list fragment from view
            transaction = fgmtMgr.beginTransaction();

            if(twinView){

                transaction.replace(R.id.fragment_tenant_detail, newFragment);

            }else{
                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.fragment_tenant_list, newFragment);
                transaction.addToBackStack(null);
                //transaction.add(R.id.fragment_tenant_detail, newFragment,"tenantDetails");

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

        FragmentManager fgmtMgr = getFragmentManager();

        FragmentTransaction transaction = fgmtMgr.beginTransaction();

        //TODO update fragments and views

        if(newtenant.toString().equalsIgnoreCase(oldtenant.toString())){
            Toast.makeText(this, "No changes detected.", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(this, newtenant.getName()+" info has been updated.", Toast.LENGTH_SHORT).show();
            ProgressDialog progress = DialogUtils.startProgressDialog(this);
            //onTenantSelected(newtenant);
            updateDB(newtenant);
            navigateToList();

            progress.dismiss();
        }


    }

    private void updateDB(TenantModel tenant){
        AppDAOInterface dbHelper = new AppDAO(this);

        try {
            dbHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        dbHelper.updateTenant(tenant);
    }

    private void navigateToList(){

        FragmentManager fgmtMgr = getFragmentManager();

        Fragment oldFragment = fgmtMgr.findFragmentById(R.id.fragment_tenant_list);

        FragmentTransaction transaction = fgmtMgr.beginTransaction();

        Fragment newFragment = null;
        if(oldFragment != null && oldFragment instanceof TenantListFragment){
            newFragment = oldFragment;
            transaction.replace(R.id.fragment_tenant_list, newFragment);
        }else{
            newFragment = new TenantListFragment();
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
