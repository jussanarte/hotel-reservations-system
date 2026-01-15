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
        return this.formaDeCobranca.calcular(precoUnitario, this.quantidade, noitesDaReserva);
    }

    @Override
    public String toString() {
        String detalheCobranca = switch (formaDeCobranca) {
            case POR_NOITE ->
                String.format("(Preco por noite: %.2f Kz)", precoUnitario);
            case POR_UNIDADE ->
                String.format("(Preco unitario: %.2f Kz x %d qtd)", precoUnitario, quantidade);
            case FIXO ->
                String.format("(Taxa fixa: %.2f Kz)", precoUnitario);
            default ->
                String.format("(%.2f Kz)", precoUnitario);
        };

        return String.format("- %s [%s] %s",
                descricao, tipoDeServico, detalheCobranca);
    }
}
