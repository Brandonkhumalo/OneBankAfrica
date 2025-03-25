package com.techmania.onebankafrica.auth;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.techmania.onebankafrica.R;

public class Register extends AppCompatActivity {

    //PHONE NUMBER
    private Spinner spinnerCountryCode;
    private TextInputEditText phoneNumberInput;
    String countryCode;

    //ID NUMBER
    private Spinner spinnerCountry;
    private TextInputEditText IdInput;
    private final String DEFAULT_NATION = "🇿🇦 South Africa";
    String country;

    TextInputEditText name;
    TextInputEditText surname;
    Button buttonReg;

    //FILE
    ImageView fileupload;
    TextView selectedFile;
    Uri fileUri;

    private final String DEFAULT_COUNTRY = "🇿🇦 South Africa";
    ActivityResultLauncher<Intent> filePickerLauncher;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference().child("files");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        spinnerCountryCode = findViewById(R.id.spinnerCountryCode);
        phoneNumberInput = findViewById(R.id.phoneNumberInput);

        spinnerCountry = findViewById(R.id.spinnerCountry);
        IdInput = findViewById(R.id.countryInput);

        name = findViewById(R.id.nameRegister);
        surname = findViewById(R.id.surnameRegister);
        buttonReg = findViewById(R.id.buttonRegNext);
        fileupload = findViewById(R.id.FileUpload);
        selectedFile = findViewById(R.id.textSelectedFile);

        //REGISTER
        filePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                fileUri = result.getData().getData();
                selectedFile.setText(fileUri.getLastPathSegment());
            } else {
                Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
            }
        });

        //PHONE NUMBER
        // Spinner Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item
                ,CountryCodes.getCountryNames());
        spinnerCountryCode.setAdapter(adapter);

        int defaultPosition = CountryCodes.getCountryNames().indexOf(DEFAULT_COUNTRY);
        spinnerCountryCode.setSelection(defaultPosition);
        phoneNumberInput.setText(CountryCodes.getCode(DEFAULT_COUNTRY) + " "); // Pre-fill South Africa's code
        phoneNumberInput.setSelection(phoneNumberInput.getText().length()); // Move cursor to end


        // Handle Country Selection
        spinnerCountryCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCountry = parent.getItemAtPosition(position).toString();
                countryCode = CountryCodes.getCode(selectedCountry);

                if (countryCode != null) {
                    phoneNumberInput.setText(countryCode + " ");
                    phoneNumberInput.setSelection(phoneNumberInput.getText().length()); // Move cursor to end
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        fileupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFilePicker();
            }
        });

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty() || surname.getText().toString().isEmpty() ||
                        getFullPhoneNumber().isEmpty() || getFullIDNumber().isEmpty()) {
                    Toast.makeText(Register.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(Register.this, Signup.class);
                    sendFile();
                    i.putExtra("Name", name.getText().toString());
                    i.putExtra("Surname", surname.getText().toString());
                    i.putExtra("MobileNumber", getFullPhoneNumber());
                    i.putExtra("IDNumber", getFullIDNumber());
                    startActivity(i);
                }
            }
        });

        //ID NUMBER
        ArrayAdapter<String> Countryadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item
                ,Countries.getCountryNames());
        spinnerCountry.setAdapter(Countryadapter);

        int defaultPositions = Countries.getCountryNames().indexOf(DEFAULT_NATION);
        spinnerCountry.setSelection(defaultPositions);
        IdInput.setText(Countries.getCode(DEFAULT_NATION) + " "); // Pre-fill South Africa's code
        IdInput.setSelection(IdInput.getText().length()); // Move cursor to end

        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedNation = parent.getItemAtPosition(position).toString();
                country = Countries.getCode(selectedNation);

                if (country != null) {
                    IdInput.setText(country + " ");
                    IdInput.setSelection(IdInput.getText().length()); // Move cursor to end
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private String getRawPhoneNumber() {
        String text = phoneNumberInput.getText().toString();
        return text.replace(countryCode + " ", "").trim();
    }

    public String getFullPhoneNumber() {
        return countryCode + getRawPhoneNumber();
    }

    private String getRawIDNumber() {
        String text = IdInput.getText().toString();
        return text.replace(country + " ", "").trim();
    }

    public String getFullIDNumber() {
        return country + getRawIDNumber();
    }

    //FILE PICKER
    public void OpenFilePicker(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        filePickerLauncher.launch(intent);
    }

    public void sendFile() {
        if (fileUri != null) {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("*/*"); // Set MIME type depending on the file you are sending
            sendIntent.putExtra(Intent.EXTRA_STREAM, fileUri);  // Attach the file URI

            // Start the intent to send the file
            startActivity(Intent.createChooser(sendIntent, "Send file via"));
        } else {
            buttonReg.setEnabled(false);
        }
    }
}