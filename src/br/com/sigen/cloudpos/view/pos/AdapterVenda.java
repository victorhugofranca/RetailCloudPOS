package br.com.sigen.cloudpos.view.pos;

import java.math.BigDecimal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.sigen.cloudpos.entity.ItemVenda;
import br.com.sigen.cloudpos.entity.Produto;
import br.com.sigen.cloudpos.entity.Venda;
import br.com.sigen.cloudpos.exception.BusinessException;
import br.com.sigen.cloudpos.view.R;
import br.com.sigen.cloudpos.view.component.NumberTextView;

public class AdapterVenda extends ArrayAdapter<ItemVenda> {

	private Context context;
	private Venda venda;
	private NumberTextView textViewTotalizador;
	private NumberTextView txtDesconto;

	public AdapterVenda(Context context, Venda venda,
			NumberTextView textViewTotalizador, NumberTextView txtDesconto) {

		super(context, R.layout.item_venda_row_layout, venda.getItensVenda());
		this.context = context;

		this.venda = venda;

		this.textViewTotalizador = textViewTotalizador;
		this.txtDesconto = txtDesconto;

	}

	public Venda getVenda() {
		return this.venda;
	}

	public void realizarDesconto(BigDecimal valorDesconto)
			throws BusinessException {
		venda.realizarDesconto(valorDesconto);
		notifyDataSetChanged();
	}

	public void realizarDescontoItem(int position, BigDecimal valorDesconto)
			throws BusinessException {
		venda.realizarDescontoItem(position, valorDesconto);
		notifyDataSetChanged();
	}

	@Override
	public void add(ItemVenda itemVenda) {
		venda.addItemVenda(itemVenda);
		notifyDataSetChanged();
	}

	public void add(Produto produto) {
		ItemVenda itemVenda = new ItemVenda(produto, BigDecimal.ONE);
		venda.addItemVenda(itemVenda);
		notifyDataSetChanged();
	}

	@Override
	public void insert(ItemVenda itemVenda, int index) {
		venda.addItemVenda(itemVenda);
		notifyDataSetChanged();
	}

	public void remove(int position) {
		ItemVenda itemVenda = getItem(position);
		venda.removeItemVenda(itemVenda);
		notifyDataSetChanged();
	}

	@Override
	public void notifyDataSetChanged() {
		textViewTotalizador.setText(venda.getValorTotal());
		txtDesconto.setText(venda.getValorDescontos());
		super.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.item_venda_row_layout, parent,
				false);
		TextView textView = (TextView) rowView.findViewById(R.id.lblProdutos);
		textView.setText(venda.getItensVenda().get(position).getProduto()
				.getDescricao());

		TextView textView2 = (TextView) rowView
				.findViewById(R.id.lblTipoPagamento);
		textView2.setText(venda.getItensVenda().get(position).getProduto()
				.getUnidadeMedida());

		NumberTextView textView3 = (NumberTextView) rowView
				.findViewById(R.id.lblValorFormaPagamento);
		textView3.setText(venda.getItensVenda().get(position)
				.getValorUnitario());

		return rowView;
	}
}
