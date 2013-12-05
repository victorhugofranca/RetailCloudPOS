package br.com.sigen.cloudpos.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.sigen.cloudpos.entity.Produto;
import br.com.sigen.cloudpos.synchronization.DBManager;

public class ProdutoManager {

	private static ProdutoManager instance;

	private ProdutoManager() {
	}

	public static ProdutoManager getInstance() {
		if (instance == null)
			instance = new ProdutoManager();

		return instance;
	}

	public List<Produto> find(Produto filter, Context context) {
		List<Produto> persistedList = new ArrayList<Produto>();

		DBManager dbManager = new DBManager(context);
		SQLiteDatabase database = dbManager.getReadableDatabase();

		String[] allColumns = { DBManager.COLUMN_CODIGO,
				DBManager.COLUMN_DESCRICAO, DBManager.COLUMN_UNIDADE_MEDIDA,
				DBManager.COLUMN_VALOR_UNITARIO };
		Cursor cursor = database.query(DBManager.TABLE_PRODUTO, allColumns,
				null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Produto produto = new Produto();

			produto.setCodigo(cursor.getString(0));
			produto.setDescricao(cursor.getString(1));
			produto.setUnidadeMedida(cursor.getString(2));
			produto.setValorUnitario(new BigDecimal(cursor.getDouble(3)));

			persistedList.add(produto);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		database.close();

		List<Produto> returnList = new ArrayList<Produto>();
		if (filter.getDescricao() == null || filter.getDescricao().equals("")) {
			returnList.addAll(persistedList);
		} else {
			returnList = containsInList(filter, persistedList);
		}
		return returnList;
	}

	private List<Produto> containsInList(Produto produto, List<Produto> produtos) {
		List<Produto> resultList = new ArrayList<Produto>();

		for (Iterator<Produto> iterator = produtos.iterator(); iterator
				.hasNext();) {
			Produto produtoNaLista = iterator.next();
			if (produtoNaLista.getDescricao().toLowerCase()
					.startsWith(produto.getDescricao().toLowerCase()))
				resultList.add(produtoNaLista);
		}

		return resultList;
	}

}
