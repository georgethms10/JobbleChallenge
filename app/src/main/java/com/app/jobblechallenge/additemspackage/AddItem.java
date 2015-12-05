package com.app.jobblechallenge.additemspackage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.jobblechallenge.R;
import com.app.jobblechallenge.utils.AppPrefes;
import com.app.jobblechallenge.utils.GenericActivity;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.parse.ParseFile;
import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddItem extends GenericActivity<AddItemsBusinessLogic> implements Validator.ValidationListener, addItemCallback {

    @Bind(R.id.item_image)
    ImageView itemImage;

    @NotEmpty
    @Bind(R.id.item_name)
    EditText itemName;

    @NotEmpty
    @Bind(R.id.item_desc)
    EditText itemDesc;
    @Bind(R.id.create_item)
    AppCompatButton createItem;
    @Bind(R.id.btn_camera)
    AppCompatButton btnCamera;
    @Bind(R.id.btn_gallery)
    AppCompatButton btnGallery;

    public ParseFile file;
    addItemCallback callback;
    private Validator validator;
    AppPrefes appPrefes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, AddItemsBusinessLogic.class);
        setContentView(R.layout.activity_add_item);
        ButterKnife.bind(this);
        validator = new Validator(this);
        callback = this;
        validator.setValidationListener(this);
        appPrefes = new AppPrefes(AddItem.this);


    }


    @OnClick(R.id.btn_gallery)
    public void gallery() {
        Crop.pickImage(this);
    }

    @OnClick(R.id.btn_camera)
    public void camera() {
        startCamera();
    }

    /**
     * start camera
     */
    public void startCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, Crop.REQUEST_PICK);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK
                && data != null) {
            beginCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void beginCrop(Uri source) {
        File file = new File(getCacheDir(), "cropped");
        Uri destination = Uri.fromFile(file);
        Crop.of(source, destination).asSquare().start(this);
    }


    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            Uri imageUri = Crop.getOutput(result);
            saveBackground(imageUri);
            itemImage.setImageBitmap(null);
            Picasso.with(AddItem.this).load(new File(Crop.getOutput(result).getPath())).into(itemImage);
        } else if (resultCode == Crop.RESULT_ERROR) {
            showMessage(Crop.getError(result).getMessage(), itemImage);
        }
    }

    private void saveBackground(final Uri imageUri) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] image = stream.toByteArray();
                    file = new ParseFile("item" + System.currentTimeMillis() + ".png", image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;


            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                System.out.println("parse file " + file);
            }
        }.execute();
    }

    @Override
    public void onValidationSucceeded() {

        if (file == null) {
            showMessage("Select an image", itemImage);
        } else {
            getOps().saveItem(itemName.getText().toString(), itemDesc.getText().toString(), appPrefes.getData("category"), file, callback);
        }
    }

    /**
     * @param errors
     * @purpose UI validation failed
     */
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
                YoYo.with(Techniques.Shake)
                        .duration(700)
                        .playOn(view);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    @OnClick(R.id.create_item)
    public void CreateItem() {
        validator.validate();
    }

    //item added to parse
    @Override
    public void itemAdded(String message) {

        finish();

    }

    @Override
    public void error(String message) {
        showMessage(message, itemImage);
    }
}
