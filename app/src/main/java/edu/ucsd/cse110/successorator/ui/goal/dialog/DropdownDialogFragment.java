package edu.ucsd.cse110.successorator.ui.goal.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.FragmentDialogDropdownBinding;
import edu.ucsd.cse110.successorator.ui.date.DateFragment;
import edu.ucsd.cse110.successorator.ui.date.PendingFragment;
import edu.ucsd.cse110.successorator.ui.date.RecurringFragment;
import edu.ucsd.cse110.successorator.ui.date.TomorrowDataFragment;
import edu.ucsd.cse110.successorator.ui.goal.GoalListFragment;
import edu.ucsd.cse110.successorator.ui.goal.PendingGoalFragment;
import edu.ucsd.cse110.successorator.ui.goal.RecurringListFragment;
import edu.ucsd.cse110.successorator.ui.goal.TomorrowGoalListFragment;


public class DropdownDialogFragment extends DialogFragment {
    private MainViewModel activityModel;
    private FragmentDialogDropdownBinding view;

    DropdownDialogFragment(){ }

    public static DropdownDialogFragment newInstance(){
        var fragment = new DropdownDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        this.view = FragmentDialogDropdownBinding.inflate(getLayoutInflater());

        return new AlertDialog.Builder(getActivity())
                .setTitle("Select A Page")
                .setView(view.getRoot())
                .setPositiveButton("Go To", this::onPositiveButtonClick)
                .setNegativeButton("Cancel", this::onNegativeButtonClick)
                .create();
    }

    private void onPositiveButtonClick(DialogInterface dialog, int which){
        if(view.radioButton2.isChecked()){
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.goalList, GoalListFragment.newInstance())
                    .replace(R.id.date_fragment_container, DateFragment.newInstance())
                    .commit();
            //Tomorrow
        }else if(view.radioButton3.isChecked()){
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.goalList, TomorrowGoalListFragment.newInstance())
                    .replace(R.id.date_fragment_container, TomorrowDataFragment.newInstance())
                    .commit();
            //Pending
        }else if(view.radioButton4.isChecked()){
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.goalList, PendingGoalFragment.newInstance())
                    .replace(R.id.date_fragment_container, PendingFragment.newInstance())
                    .commit();
        }else if (view.radioButton5.isChecked()){
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.goalList, RecurringListFragment.newInstance())
                    .replace(R.id.date_fragment_container, RecurringFragment.newInstance())
                    .commit();
        }
        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which){
        dialog.cancel();
    }

}

