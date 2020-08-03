package com.example.policebureauapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class PermissionFormActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button submitButton;
    EditText applicantNameEditText;
    EditText affiliatedOrganizationEditText;
    EditText applicantAddressEditText;
    EditText applicantMobileNoEditText;
    EditText organizationMobileNoEditText;
    EditText organizationAddressEditText;
    EditText permissionDetailEditText;
    Spinner permissionTypeSpinner;
    Spinner permissionStatusSpinner;
    DatabaseReference databaseReference;
    SharedPreferences sharedPreferences;
    String username;
    EditText permissionStartDate;
    EditText permissionEndDate;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_form);

        toolbar = (Toolbar) findViewById(R.id.permissionFormToolbar);
        setSupportActionBar(toolbar);
        setTitle("Permission Form");
        initialize();
    }

    public void initialize() {
        submitButton = (Button) findViewById(R.id.permissionsSubmitButton);
        applicantNameEditText = (EditText) findViewById(R.id.permissionApplicantNameEditText);
        affiliatedOrganizationEditText = (EditText) findViewById(R.id.affiliatedOrganizationEditText);
        applicantAddressEditText = (EditText) findViewById(R.id.permissionApplicantAddEditText);
        organizationMobileNoEditText = (EditText) findViewById(R.id.permissionOrganizationNoEditText);
        applicantMobileNoEditText = (EditText) findViewById(R.id.permissionMobileEditText);
        organizationAddressEditText = (EditText) findViewById(R.id.permissionOrganizationAddEditText);
        permissionDetailEditText = (EditText) findViewById(R.id.permissionDetailEditText);
        permissionStartDate = (EditText) findViewById(R.id.permissionStartDateEditText);
        permissionEndDate = (EditText) findViewById(R.id.permissionEndDateEditText);
        permissionTypeSpinner = (Spinner) findViewById(R.id.permissionTypeSpinner);
        permissionStatusSpinner = (Spinner) findViewById(R.id.permissionStatusSpinner);
        sharedPreferences = getSharedPreferences("com.example.policebureauapp", MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        databaseReference = FirebaseDatabase.getInstance().getReference("Registered Permissions").child(username);
        builder = new AlertDialog.Builder(this);

        permissionStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePicker(permissionStartDate);
            }
        });

        permissionEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker(permissionEndDate);
            }
        });
    }

    private void DatePicker(final EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(PermissionFormActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editText.setText(dayOfMonth + "/" + month + "/" + year);
                    }
                }, year, month, day);
        dialog.show();
    }

    public void onSubmit(View view) {
        if (applicantNameEditText.getText().toString().trim().matches("")) {
            Toast.makeText(this, "Enter Applicant's Name", Toast.LENGTH_SHORT).show();
            applicantNameEditText.requestFocus();
        } else if (applicantMobileNoEditText.getText().toString().trim().matches("")) {
            Toast.makeText(this, "Enter Applicant's Mobile Number", Toast.LENGTH_SHORT).show();
            applicantMobileNoEditText.requestFocus();
        } else if (applicantMobileNoEditText.getText().toString().trim().length() != 10) {
            Toast.makeText(this, "Mobile Number should be of 10 digits", Toast.LENGTH_SHORT).show();
            applicantMobileNoEditText.requestFocus();
        } else if (applicantAddressEditText.getText().toString().trim().matches("")) {
            Toast.makeText(this, "Enter Applicant's Address", Toast.LENGTH_SHORT).show();
            applicantAddressEditText.requestFocus();
        } else if (permissionStartDate.getText().toString().trim().matches("")) {
            Toast.makeText(this, "Enter Starting Date of Event", Toast.LENGTH_SHORT).show();
            DatePicker(permissionStartDate);
        } else if (permissionTypeSpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Select Permission Type", Toast.LENGTH_SHORT).show();
            permissionTypeSpinner.performClick();
        } else if (permissionStatusSpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Select Status of Permission", Toast.LENGTH_SHORT).show();
            permissionStatusSpinner.performClick();
        } else if (permissionDetailEditText.getText().toString().trim().matches("")) {
            Toast.makeText(this, "Enter Fir Detail", Toast.LENGTH_SHORT).show();
            permissionDetailEditText.requestFocus();
        } else if (username.matches("")) {
            Toast.makeText(this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
        } else {
            String name = applicantNameEditText.getText().toString().trim();
            String organisation = affiliatedOrganizationEditText.getText().toString().trim();
            String applicantMobileNo = applicantMobileNoEditText.getText().toString().trim();
            String organizationMobileNo = organizationMobileNoEditText.getText().toString().trim();
            String applicantAddress = applicantAddressEditText.getText().toString().trim();
            String organizationAddress = organizationAddressEditText.getText().toString().trim();
            String startDate = permissionStartDate.getText().toString().trim();
            String endDate = permissionEndDate.getText().toString().trim();
            String type = (String) permissionTypeSpinner.getSelectedItem();
            String status = (String) permissionStatusSpinner.getSelectedItem();
            String detail = permissionDetailEditText.getText().toString().trim();


            View view1 = this.getCurrentFocus();
            if (view1 != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
            }
            if (submitButton.getText().toString().matches("Submit")) {
                offFocus();
                submitButton.setText("Final Submit");
                setTitle("Check Complaint Details");
                Toast.makeText(this, "Check Complaint details", Toast.LENGTH_SHORT).show();
            } else {
                PermissionClass currentPermission = new PermissionClass(name,organisation,applicantMobileNo,organizationMobileNo,
                        applicantAddress,organizationAddress,startDate,endDate,type ,status,detail);
                String PermissionId = databaseReference.push().getKey();
                setDialogBox(PermissionId, currentPermission);
            }
        }

    }



    public void offFocus() {
        applicantNameEditText.setFocusable(false);
        affiliatedOrganizationEditText.setFocusable(false);
        applicantAddressEditText.setFocusable(false);
        organizationMobileNoEditText.setFocusable(false);
        applicantMobileNoEditText.setFocusable(false);
        organizationAddressEditText.setFocusable(false);
        permissionDetailEditText.setFocusable(false);
        permissionStartDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        permissionEndDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        permissionTypeSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        permissionStatusSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    public void setDialogBox(final String permissionId, final PermissionClass currentPermission) {
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseReference.child(permissionId).setValue(currentPermission);
                setDialogBoxToShowFirId(permissionId);
            }
        });
        builder.setNegativeButton("Cancel",null);
        builder.setTitle("Register Permission  !!!");
        builder.setMessage("Are you sure?");
        builder.show();
    }

    public void setDialogBoxToShowFirId(String permissionId) {
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setTitle("Permission Registered  !!!");
        builder.setMessage("Save Permission Id For Future Reference \n\n\nFir Id :   "+permissionId);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        builder.show();
    }
}
