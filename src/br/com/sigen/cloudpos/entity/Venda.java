package br.com.sigen.cloudpos.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import br.com.sigen.cloudpos.Messages;
import br.com.sigen.cloudpos.exception.BusinessException;

public class Venda implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal valorTotal;
	private BigDecimal valorDescontos;

	private List<ItemVenda> itensVenda;

	private Pagamento pagamento;

	public Boolean podeRealizarDesconto(BigDecimal desconto) {
		return valorTotal.compareTo(desconto) > 0 ? true : false;
	}

	public Boolean podeEncerrar() {
		return itensVenda.isEmpty() ? false : true;
	}

	public void realizarDescontoItem(int position, BigDecimal valorDesconto)
			throws BusinessException {
		ItemVenda itemVenda = getItensVenda().get(position);
		itemVenda.realizarDesconto(valorDesconto);
		setValorTotal(getValorTotal().subtract(valorDesconto));
	}

	public void realizarDesconto(BigDecimal valorDesconto)
			throws BusinessException {

		if (!podeRealizarDesconto(valorDesconto))
			throw new BusinessException(
					Messages.IMPOSSIVEL_REALIZAR_DESCONTO_VENDA);

		setValorTotal(getValorTotal().subtract(valorDesconto));
		setValorDescontos(getValorDescontos().add(valorDesconto));
	}

	public void addItemVenda(ItemVenda itemVenda) {
		itensVenda.add(itemVenda);
		valorTotal = valorTotal.add(itemVenda.getValorTotal());
	}

	public void removeItemVenda(ItemVenda itemVenda) {
		itensVenda.remove(itemVenda);
		valorTotal = valorTotal.subtract(itemVenda.getValorTotal());
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public List<ItemVenda> getItensVenda() {
		return itensVenda;
	}

	public void setItensVenda(List<ItemVenda> itensVenda) {
		this.itensVenda = itensVenda;
	}

	public BigDecimal getValorDescontos() {
		return valorDescontos;
	}

	public void setValorDescontos(BigDecimal valorDescontos) {
		this.valorDescontos = valorDescontos;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

}
