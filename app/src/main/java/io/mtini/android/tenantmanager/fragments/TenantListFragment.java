package io.mtini.android.tenantmanager.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
//import android.support.v4.app.ListFragment;
import android.widget.ListView;
import android.view.View;
import android.widget.TextView;
import android.content.Context;

import java.sql.SQLException;
import java.util.List;

import io.mtini.android.adaptor.TenantListBindingAdapter;
import io.mtini.android.tenantmanager.R;
import io.mtini.model.AppDAO;
import io.mtini.model.AppDAOInterface;
import io.mtini.model.EstateModel;
import io.mtini.model.TenantModel;

public class TenantListFragment extends ListFragment {

    OnTenantSelectedListener mCallback;

    public interface OnTenantSelectedListener {
        public void onTenantSelected(TenantModel tenant);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We need to use a different list item layout for devices older than Honeycomb
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;


        final Context currentContext = this.getActivity();

        AppDAOInterface dbHelper = new AppDAO(currentContext);

        try {
            dbHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //get data object passed by previous activity
        final EstateModel estate = (EstateModel)getActivity().getIntent().getSerializableExtra("estate");
        assert estate!=null;

        //populate tenant list from db
        String estateName = estate.getName();
        List<TenantModel> tenantList = dbHelper.getTenantList(estate);

        //update Text view for onscreen debugging
        int count = tenantList.size();
        estate.setTenantCount(count);
        TextView estateSizeTxtView = (TextView)getActivity().findViewById(R.id.textEstateCount);
        estateSizeTxtView.setText(estateName+" has "+count+" tenants.");

        //Now bind the list of Tenants using an adapter
        ListView listView = (ListView) getActivity().findViewById(R.id.fragment_tenant_list_linearlayout);
        TenantListBindingAdapter dataAdapter = new TenantListBindingAdapter(
                currentContext,tenantList,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TenantModel tag = (TenantModel)v.getTag();

                        Snackbar.make(v, "Selected "+tag.getName()+" Details", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();


                        ((TenantListFragment.OnTenantSelectedListener)getActivity()) .onTenantSelected(tag);

                    }
                });

        setListAdapter(dataAdapter);


    }

    @Override
    public void onStart() {
        super.onStart();

        // When in two-pane layout, set the listview to highlight the selected list item
        // (We do this during onStart because at the point the listview is available.)
        if (getFragmentManager().findFragmentById(R.id.fragment_tenant_list ) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnTenantSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTenantSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Notify the parent activity of selected item
        //mCallback.onTenantSelected(position);

        // Set the item as checked to be highlighted when in two-pane layout
        getListView().setItemChecked(position, true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

}
