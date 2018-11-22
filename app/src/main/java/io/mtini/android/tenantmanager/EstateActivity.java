package io.mtini.android.tenantmanager;


import android.app.FragmentManager;
import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.UUID;

import io.mtini.android.adaptor.EstateListBindingAdapter;
import io.mtini.android.tenantmanager.dialog.EditEstateDetailsDialogFragment;
import io.mtini.model.AppDAO;
import io.mtini.model.AppDAOInterface;
import io.mtini.model.EstateModel;

public class EstateActivity extends AppCompatActivity implements EditEstateDetailsDialogFragment.OnEditEstateListener {



    public static String TAG = Class.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_estates);

        AppDAOInterface dbHelper = new AppDAO(this);

        try {
            dbHelper.open();
        } catch (SQLException e) {
            Log.e(TAG,e.getMessage());
        }


        //DROP TABLE
        //((AppDAO) dbHelper).dropTables();
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
        EstateListBindingAdapter dataAdapter = new EstateListBindingAdapter(this,
                estates,
                new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(EstateActivity.this, EstateDetailsActivity.class);
                                EstateModel tag = (EstateModel)v.getTag();
                                intent.putExtra("estate",tag);
                                startActivity(intent);
                                //TODO try using fragment instead of activity
                    }
                });

        listView.setAdapter(dataAdapter);
        ////END


        ///START add Estate button

        FloatingActionButton addEstateButton = (FloatingActionButton) findViewById(R.id.add_estate_button );
        addEstateButton.setOnClickListener(new View.OnClickListener(){
            final EstateModel newEstate = new EstateModel(UUID.randomUUID(),"","",null);
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                EditEstateDetailsDialogFragment editEstateDialogFragment = new EditEstateDetailsDialogFragment();
                Bundle args = new Bundle();
                args.putSerializable(EditEstateDetailsDialogFragment.ARG_EDIT_ESTATE, newEstate);
                editEstateDialogFragment.setArguments(args);
                editEstateDialogFragment.show(fm, "fragment_new_estate");
            }
        });
        ///END

    }



    ///TESTING
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

        /*
        FragmentManager fm = getFragmentManager();
        EditEstateDetailsDialogFragment editEstateDialogFragment = new EditEstateDetailsDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(EditEstateDetailsDialogFragment.ARG_EDIT_ESTATE, estate);
        editEstateDialogFragment.setArguments(args);
        editEstateDialogFragment.show(fm, "fragment_edit_estate"); */
        Intent intent = getIntent();//new Intent(EstateActivity.this, EstateDetailsActivity.class);
        intent.putExtra("estate",estate);
        startActivity(intent);
    }

}
