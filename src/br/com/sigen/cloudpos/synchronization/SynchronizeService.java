package br.com.sigen.cloudpos.synchronization;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import br.com.sigen.cloudpos.Messages;
import br.com.sigen.cloudpos.entity.Produto;
import br.com.sigen.cloudpos.exception.SystemException;

public class SynchronizeService extends AsyncTask<String, String, String> {

	private Context context;

	public SynchronizeService(Context context) {
		this.context = context;
	}

	@Override
	protected String doInBackground(String... uri) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response;
		String responseString = null;
		try {
			response = httpclient.execute(new HttpGet(uri[0]));
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				responseString = out.toString();
				return responseString;
			} else {
				// Closes the connection.
				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
		} catch (ClientProtocolException e) {
			Log.e(getClass().getName(), e.getMessage());
			throw new SystemException(Messages.SERVER_CONNECTION_ERROR);
		} catch (IOException e) {
			Log.e(getClass().getName(), e.getMessage());
			throw new SystemException(Messages.SERVER_CONNECTION_ERROR);
		}
	}

	@Override
	protected void onPostExecute(String result) {
		DBManager dbManager = new DBManager(context);

		List<Produto> produtos = new ArrayList<Produto>();

		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(result);
			for (int i = 0; i < jsonArray.length(); i++) {
				String codigo = jsonArray.getJSONObject(i).getString("codigo");
				String descricao = jsonArray.getJSONObject(i).getString(
						"descricao");
				String unidadeMedida = jsonArray.getJSONObject(i).getString(
						"unidadeMedida");
				BigDecimal valorUnitario = new BigDecimal(jsonArray
						.getJSONObject(i).getString("valorUnitario"));

				Produto produto = new Produto();
				produto.setCodigo(codigo);
				produto.setDescricao(descricao);
				produto.setUnidadeMedida(unidadeMedida);
				produto.setValorUnitario(valorUnitario);
				produtos.add(produto);
			}
			dbManager.sincronizarBanco(produtos);
		} catch (JSONException e) {
			Toast.makeText(context, Messages.SYNC_ERROR, Toast.LENGTH_SHORT)
					.show();
			Log.e(getClass().getName(), e.getMessage());
			throw new SystemException(Messages.SYNC_ERROR);
		}
	}
}
