package br.com.sigen.cloudpos.entity;

import java.math.BigDecimal;

import br.com.sigen.cloudpos.Messages;
import br.com.sigen.cloudpos.exception.BusinessException;

public class ItemVenda {

	private String indice;
	private Produto produto;
	private BigDecimal quantidade;
	private BigDecimal valorUnitario;
	private BigDecimal valorTotal;

	public ItemVenda() {
		// TODO Auto-generated constructor stub
	}

	public ItemVenda(Produto produto, BigDecimal quantidade) {
		this.produto = produto;
		this.quantidade = quantidade;
		this.valorUnitario = produto.getValorUnitario();
		this.valorTotal = quantidade.multiply(produto.getValorUnitario());
	}

	public Boolean podeRealizarDesconto(BigDecimal desconto) {
		return valorTotal.compareTo(desconto) > 0 ? true : false;
	}

	public void realizarDesconto(BigDecimal valorDesconto)
			throws BusinessException {
		if (!podeRealizarDesconto(valorDesconto))
			throw new BusinessException(
					Messages.IMPOSSIVEL_REALIZAR_DESCONTO_ITEM);

		setValorUnitario(getValorUnitario().subtract(valorDesconto));
		setValorTotal(getValorUnitario().multiply(getQuantidade()));
	}

	public String getIndice() {
		return indice;
	}

	public void setIndice(String indice) {
		this.indice = indice;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

}
