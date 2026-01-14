/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entities;

import java.math.BigDecimal;
import model.enums.FormaCobranca;
import static model.enums.FormaCobranca.POR_NOITE;
import static model.enums.FormaCobranca.POR_UNIDADE;
import model.enums.TipoServico;

/**
 *
 * @author juuhl
 */
public class ServicoAdicional {

    private String descricao;
    private TipoServico tipoDeServico;
    private BigDecimal precoUnitario;
    private Integer quantidade;
    private FormaCobranca formaDeCobranca;

    public ServicoAdicional(String descricao, TipoServico tipoDeServico, BigDecimal precoUnitario, Integer quantidade, FormaCobranca formaDeCobranca) {
        this.descricao = descricao;
        this.tipoDeServico = tipoDeServico;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
        this.formaDeCobranca = formaDeCobranca;
    }

    public ServicoAdicional() {
    }

    //GETTERS
    public String getDescricao() {
        return descricao;
    }

    public TipoServico getTipoDeServico() {
        return tipoDeServico;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public FormaCobranca getFormaDeCobranca() {
        return formaDeCobranca;
    }

    //SETTERS
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setTipoDeServico(TipoServico tipoDeServico) {
        this.tipoDeServico = tipoDeServico;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void setFormaDeCobranca(FormaCobranca formaDeCobranca) {
        this.formaDeCobranca = formaDeCobranca;
    }

    //FUNCOES
    public BigDecimal calcularTotal(long noitesDaReserva) {
        if (null == this.formaDeCobranca) { // Caso seja FIXO
            return this.precoUnitario;
        } else {
            return switch (this.formaDeCobranca) {
                case POR_NOITE ->
                    this.precoUnitario.multiply(BigDecimal.valueOf(noitesDaReserva));
                case POR_UNIDADE ->
                    this.precoUnitario.multiply(BigDecimal.valueOf(this.quantidade));
                default ->
                    this.precoUnitario;
            };
        }
    }

    @Override
    public String toString() {
        return String.format("- %s (%s): %,.2f Kz x %d unidades",
                descricao, tipoDeServico, precoUnitario, quantidade);
    }
}
