package br.com.sigen.cloudpos.synchronization;

import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.sigen.cloudpos.entity.Produto;

public class DBManager extends SQLiteOpenHelper {

	public static final String TABLE_PRODUTO = "tb_produto";
	public static final String COLUMN_ID = "id_produto";
	public static final String COLUMN_CODIGO = "ds_codigo";
	public static final String COLUMN_DESCRICAO = "ds_descricao";
	public static final String COLUMN_UNIDADE_MEDIDA = "ds_unidade_medida";
	public static final String COLUMN_VALOR_UNITARIO = "cr_valor_unitario";

	private static final String DATABASE_NAME = "cloud_pos.db";
	private static final int DATABASE_VERSION = 3;

	public DBManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	private String getCreateTableCommand() {
		StringBuffer sql = new StringBuffer();
		sql.append("create table ");
		sql.append(TABLE_PRODUTO + "( ");

		sql.append(COLUMN_ID + " integer primary key autoincrement, ");
		sql.append(COLUMN_CODIGO + " text not null, ");
		sql.append(COLUMN_DESCRICAO + " text not null, ");
		sql.append(COLUMN_UNIDADE_MEDIDA + " text not null, ");
		sql.append(COLUMN_VALOR_UNITARIO + " REAL not null ");

		sql.append(");");

		return sql.toString();
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(getCreateTableCommand());
	}

	public void sincronizarBanco(List<Produto> produtos) {

		SQLiteDatabase database = getWritableDatabase();

		database.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUTO);
		database.execSQL(getCreateTableCommand());

		try {
			database.beginTransaction();
			for (Iterator<Produto> iterator = produtos.iterator(); iterator
					.hasNext();) {
				Produto produto = iterator.next();
				StringBuffer insertSql = new StringBuffer();
				insertSql.append("insert into " + TABLE_PRODUTO + "( ");
				insertSql.append(COLUMN_CODIGO + ", ");
				insertSql.append(COLUMN_DESCRICAO + ", ");
				insertSql.append(COLUMN_UNIDADE_MEDIDA + ", ");
				insertSql.append(COLUMN_VALOR_UNITARIO);
				insertSql.append(") ");
				insertSql.append("VALUES (");
				insertSql.append("\"" + produto.getCodigo() + "\", ");
				insertSql.append("\"" + produto.getDescricao() + "\", ");
				insertSql.append("\"" + produto.getUnidadeMedida() + "\", ");
				insertSql.append("\"" + produto.getValorUnitario() + "\" ");
				insertSql.append("); ");

				database.execSQL(insertSql.toString());
			}
			database.setTransactionSuccessful();
		} finally {
			database.endTransaction();
			database.close();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
