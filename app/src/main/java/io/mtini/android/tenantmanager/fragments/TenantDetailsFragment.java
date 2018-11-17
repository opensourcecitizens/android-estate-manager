package io.mtini.android.tenantmanager.fragments;



import android.app.Fragment;
import android.app.FragmentManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import io.mtini.android.tenantmanager.R;
import io.mtini.android.tenantmanager.dialog.TenantDetailsDialogFragment;
import io.mtini.model.TenantModel;
import io.mtini.android.tenantmanager.databinding.TenantDetailLayoutBinding;
public class TenantDetailsFragment extends Fragment {

    //public final static String ARG_POSITION = "position";
    public final static String ARG_SELECTED_TENANT = "selectedTenant";
    //int mCurrentPosition = -1;
    TenantModel currentTenant = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            //mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
            currentTenant = (TenantModel)savedInstanceState.getSerializable(ARG_SELECTED_TENANT);
        }else if(getArguments()!=null) {

            currentTenant = (TenantModel)getArguments().getSerializable(ARG_SELECTED_TENANT);
        }

        TenantDetailLayoutBinding  binding  = DataBindingUtil.inflate(
                inflater, R.layout.tenant_detail_layout, container, false);
        View view = binding.getRoot();//binding.getRoot().getRootView();
        binding.setTenant(currentTenant);


        Button editButton = (Button) view.findViewById(R.id.editTenantBtn);
        editButton.setOnClickListener(new View.OnClickListener(){
            final TenantModel editTenant = currentTenant;
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                TenantDetailsDialogFragment editNameDialogFragment = new TenantDetailsDialogFragment();
                Bundle args = new Bundle();
                args.putSerializable(TenantDetailsDialogFragment.ARG_EDIT_TENANT, editTenant);
                editNameDialogFragment.setArguments(args);
                editNameDialogFragment.show(fm, "fragment_edit_name");
            }
        });

        return view;

    }


    @Override
    public void onStart() {
        super.onStart();
        //TenantModel tenant = (TenantModel)getActivity().getIntent().getSerializableExtra("tenant");
        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateTenantView((TenantModel) args.getSerializable(ARG_SELECTED_TENANT));
        } else if (currentTenant != null) {
            // Set article based on saved instance state defined during onCreateView
            updateTenantView(currentTenant);
        }
    }

    public void updateTenantView(TenantModel tenant ) {
        //TextView article = (TextView) getActivity().findViewById(R.id.tenant_fragment_position);
        currentTenant = tenant;

        LayoutInflater inflater = getActivity().getLayoutInflater();
        ViewGroup container = (ViewGroup) getView();//getView().getRootView();

        TenantDetailLayoutBinding  binding  = DataBindingUtil.inflate(
                inflater, R.layout.tenant_detail_layout, container, false);
        //View view = binding.getRoot();
        binding.setTenant(currentTenant);
        binding.notifyChange();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putSerializable(ARG_SELECTED_TENANT, currentTenant);
    }


}
