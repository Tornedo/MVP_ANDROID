package com.tsl.app.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.tsl.app.AlertCallback;
import com.tsl.app.view.Processing;


public   class BaseActivity extends AppCompatActivity implements Processing {

    protected String TAG = this.getClass().getName();

    private Context context;
    private ProgressDialog progressDialog;
    private boolean isProgress = false;

    public BaseActivity() {

    }

    /**
     * Set context and other pre-init task we will add here
     *
     * @param activity
     */
    protected void setContext(Activity activity) {
        context = activity;

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(true);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    // ToDo: cancel is not working
                    dialog.cancel();
                }
            });
        }
    }

    @Override
    public void showProcessing() {
        showProcessing("Please Wait...", "Loading");
    }

    @Override
    public void showProcessing(String message, String title) {
        if (!isProgress) {
            isProgress = true;
            progressDialog = ProgressDialog.show(context, message, title);
        }
    }

    @Override
    public void hideProcessing() {
        try {
            if (isProgress && progressDialog != null) {
                progressDialog.dismiss();
                isProgress = false;
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    public void showAlert(String title, String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton(getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        dialog.dismiss();

                    }
                });
        alert.show();
    }

    public void showAlert(String title, String message, final AlertCallback callback) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton(getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (callback != null) callback.run();
                        dialog.dismiss();
                    }
                });
        alert.show();
    }

    protected void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    protected void showSnackBar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    protected void showView(View view, boolean show) {
        if (view != null) {
            view.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    protected void setViewAndChildrenEnabled(View view, boolean enabled) {
        if (view instanceof EditText || view instanceof Spinner) {
            view.setEnabled(enabled);
        }

        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                setViewAndChildrenEnabled(child, enabled);
            }
        }
    }

    protected int getIndex(Spinner spinner, String myString) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }

    protected void keypadOffonStart() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}