package com.example.indianeditor;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final int PICK_MEDIA = 100;
    private static final int REQUEST_PERMISSIONS = 101;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHome();
    }

    private void showHome() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(32, 28, 32, 28);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setBackgroundColor(Color.rgb(17,17,17));

        TextView title = new TextView(this);
        title.setText("IndianEditor");
        title.setTextColor(Color.WHITE);
        title.setTextSize(30);
        title.setGravity(Gravity.CENTER);
        root.addView(title, new LinearLayout.LayoutParams(-1, 70));

        TextView subtitle = new TextView(this);
        subtitle.setText("Fast mobile video editor • Android");
        subtitle.setTextColor(Color.LTGRAY);
        subtitle.setTextSize(16);
        subtitle.setGravity(Gravity.CENTER);
        root.addView(subtitle, new LinearLayout.LayoutParams(-1, 60));

        Button importButton = button("Import Video / Photo");
        importButton.setOnClickListener(v -> openMediaPicker());
        root.addView(importButton, params());

        Button cameraButton = button("Record Video");
        cameraButton.setOnClickListener(v -> requestCamera());
        root.addView(cameraButton, params());

        TextView info = new TextView(this);
        info.setText("Project foundation is ready. Import media to start your editing workflow.");
        info.setTextColor(Color.GRAY);
        info.setTextSize(14);
        info.setGravity(Gravity.CENTER);
        info.setPadding(10, 30, 10, 10);
        root.addView(info, new LinearLayout.LayoutParams(-1, -2));

        setContentView(root);
    }

    private Button button(String text) {
        Button b = new Button(this);
        b.setText(text);
        b.setTextSize(16);
        b.setTextColor(Color.WHITE);
        b.setAllCaps(false);
        b.setBackgroundColor(Color.rgb(255,109,0));
        return b;
    }

    private LinearLayout.LayoutParams params() {
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, 58);
        p.setMargins(0, 12, 0, 0);
        return p;
    }

    private void openMediaPicker() {
        Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        i.setType("video/*");
        i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(i, PICK_MEDIA);
    }

    private void requestCamera() {
        if (android.os.Build.VERSION.SDK_INT >= 23 && checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSIONS);
        } else {
            Toast.makeText(this, "Camera permission granted. Add your camera editor here.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_MEDIA && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            Toast.makeText(this, uri != null ? "Media imported" : "Media selected", Toast.LENGTH_SHORT).show();
        }
    }
}
