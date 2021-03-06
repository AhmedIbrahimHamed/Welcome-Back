package com.project.graduation.welcomeback.Home.MissingStepper;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.project.graduation.welcomeback.Home.Data.DataManger;
import com.project.graduation.welcomeback.Home.Data.Report;
import com.project.graduation.welcomeback.R;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;

/**
 * Created by Ahmed on 5/6/2017.
 */

public class ReportMissingStep1 extends Fragment implements BlockingStep {

    public static ReportMissingStep1 newInstance(){
        return new ReportMissingStep1();
    }

    private DataManger mDataManager;

    private EditText mNameEditText;

    private EditText mAgeEditText;

    private RadioButton mGenderButton;

    private RadioGroup mGenderGroup;

    private ArrayList<String> mReportArray;

    String mName,mGender,mAge;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.report_missing_step1_fragment,container,false);
        mDataManager = (DataManger) getActivity();
        mNameEditText = (EditText) view.findViewById(R.id.missing_step1_name_editText);
        mAgeEditText = (EditText) view.findViewById(R.id.missing_step1_age_editText);
        mGenderGroup = (RadioGroup) view.findViewById(R.id.missing_step1_gender_radioGroup);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DataManger) {
            mDataManager = (DataManger) context;
        } else {
            throw new IllegalStateException("Activity must implement DataManager interface!");
        }
    }


    @Override
    public VerificationError verifyStep() {
        mName = mNameEditText.getText().toString();
        mAge = mAgeEditText.getText().toString();
        int idButton = mGenderGroup.getCheckedRadioButtonId();
        mGenderButton = (RadioButton) view.findViewById(idButton);
        mGender = mGenderButton.getText().toString();
        if(mName.length() < 1){
            return new VerificationError("Please Enter Full Name");
        }else if (mAge.equals("") || Integer.parseInt(mAge) == 0 ){
            return new VerificationError("Please Enter Age");
        }

        return null;
    }
    @Override
    public void onSelected() {
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        Toast.makeText(getActivity(),error.getErrorMessage(),Toast.LENGTH_LONG).show();
    }

    @Override
    @UiThread
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
        mReportArray = new ArrayList<>();
        mReportArray.add(0,mName);
        mReportArray.add(1,mAge);
        mReportArray.add(2,mGender);
        mDataManager.saveData(mReportArray);
        callback.goToNextStep();
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        callback.complete();
    }

    @Override
    @UiThread
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }
}
