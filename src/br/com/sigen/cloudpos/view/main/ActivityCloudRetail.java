package br.com.sigen.cloudpos.view.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.com.sigen.cloudpos.synchronization.SynchronizeService;
import br.com.sigen.cloudpos.view.R;
import br.com.sigen.cloudpos.view.pos.ActivityPosApp;

public class ActivityCloudRetail extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cloud_retail);

		configVisualComponents();
	}

	private void configVisualComponents() {
		configAtualizarButton();
		configPOSButton();
	}

	private void configPOSButton() {
		Button btnPOS = (Button) findViewById(R.id.btnPOS);
		btnPOS.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getBaseContext(),
						ActivityPosApp.class);
				startActivity(intent);
			}
		});
	}

	private void configAtualizarButton() {
		Button btnAtualizar = (Button) findViewById(R.id.btnAtualizar);
		btnAtualizar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new SynchronizeService(getBaseContext())
						.execute("http://192.168.1.4:8080/knockout-app/rest/produto/load");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cloud_retail, menu);
		return true;
	}

}
