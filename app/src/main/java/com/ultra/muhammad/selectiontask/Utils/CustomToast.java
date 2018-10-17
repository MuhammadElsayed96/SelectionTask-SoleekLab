package com.ultra.muhammad.selectiontask.Utils;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ultra.muhammad.selectiontask.R;

public final class CustomToast {
    private static final String TAG = CustomToast.class.getSimpleName();

    public void showToast(Context context, View view, String error) {
        Log.wtf(TAG, "showToast() has been instantiated");

        // Layout Inflater for inflating custom view
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // inflate the layout over view
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) view.findViewById(R.id.toast_root));

        TextView text = layout.findViewById(R.id.toast_error);
        text.setText(error);

        // Set toast gravity and fill horizontal
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);

        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);

        toast.show();
    }
}
