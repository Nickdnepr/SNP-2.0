package test.homework.nick.snp20.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import test.homework.nick.snp20.R;
import test.homework.nick.snp20.view.ViewModel;
import test.homework.nick.snp20.view.activity.MainActivity;

/**
 * Created by Nick on 27.10.16.
 */
public class BottomSheetTestingFragment extends Fragment implements ViewModel{

    MainActivity activity;
    Button openButton;
    Button closeButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.testing_bottomsheet_fragment_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity= (MainActivity) getActivity();

        openButton = (Button) getView().findViewById(R.id.open_bottom_sheet_button);
        closeButton = (Button) getView().findViewById(R.id.close_bottom_sheet_button);

        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openSheet();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.closeSheet();
            }
        });
    }

    @Override
    public void initControlElements() {

    }

    @Override
    public void setupControlElements() {

    }
}
