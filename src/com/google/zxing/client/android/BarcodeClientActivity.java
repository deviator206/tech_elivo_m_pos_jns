package com.google.zxing.client.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.mpos.R;

public class BarcodeClientActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button barcode_button = (Button) findViewById(R.id.barcode_button);
		barcode_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						"com.google.zxing.client.android.SCAN");
				// intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
				// intent.putExtra("SCAN_MODE", "ONE_D_MODE");
				intent.putExtra("PROMPT_MESSAGE",
						"Vishal....barcode is getting scanned..be patient!");
				// intent.putExtra("SCAN_MODE", "DATA_MATRIX_MODE");
				// startActivityForResult(intent, 0);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String contents = intent.getStringExtra("SCAN_RESULT");
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				Toast.makeText(
						this,
						"Scan successful..Contents=" + contents + "  format="
								+ format, Toast.LENGTH_SHORT).show();
			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel } }
			}
		}
	}
}
