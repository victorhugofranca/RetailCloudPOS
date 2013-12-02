package br.com.sigen.cloudpos.entity;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

public class Pagamento {

	private BigDecimal valorTotal;
	private List<ItemPagamento> itensPagamento;
	private BigDecimal saldoPagamento;

	public void addItemPagamento(ItemPagamento itemPagamento) {
		for (Iterator<ItemPagamento> iterator = getItensPagamento().iterator(); iterator
				.hasNext();) {
			ItemPagamento itemPagamentoExistente = iterator.next();
			if (itemPagamentoExistente.getTipoPagamento().equals(
					itemPagamento.getTipoPagamento())) {
				itemPagamentoExistente.setValor(itemPagamentoExistente
						.getValor().add(itemPagamento.getValor()));
				setValorTotal(getValorTotal().add(itemPagamento.getValor()));
				setSaldoPagamento(getSaldoPagamento().subtract(
						itemPagamento.getValor()));
				return;
			}
		}

		itensPagamento.add(itemPagamento);
		setValorTotal(getValorTotal().add(itemPagamento.getValor()));
		setSaldoPagamento(getSaldoPagamento()
				.subtract(itemPagamento.getValor()));
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public List<ItemPagamento> getItensPagamento() {
		return itensPagamento;
	}

	public void setItensPagamento(List<ItemPagamento> itensPagamento) {
		this.itensPagamento = itensPagamento;
	}

	public BigDecimal getSaldoPagamento() {
		return saldoPagamento;
	}

	public void setSaldoPagamento(BigDecimal saldoPagamento) {
		this.saldoPagamento = saldoPagamento;
	}

}
