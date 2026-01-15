/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entities;

import java.math.BigDecimal;
import model.enums.EstadoQuarto;
import model.enums.TipoQuarto;

/**
 *
 * @author juuhl
 */
public class Quarto {

    private Integer numero;
    private TipoQuarto tipo;
    private BigDecimal precoDiarioBase;
    private Integer capacidade;
    private EstadoQuarto estado;
    private static int value = 1;

    public Quarto(TipoQuarto tipo, BigDecimal precoDiarioBase, Integer capacidade) {
        this.numero = value++;
        this.tipo = tipo;
        this.precoDiarioBase = precoDiarioBase;
        this.capacidade = capacidade;
        this.estado = EstadoQuarto.ATIVO;
    }

    public Integer getNumero() {
        return numero;
    }

    public TipoQuarto getTipo() {
        return tipo;
    }

    public BigDecimal getPrecoDiarioBase() {
        return precoDiarioBase;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public EstadoQuarto getEstado() {
        return estado;
    }

    public void setTipo(TipoQuarto tipo) {
        this.tipo = tipo;
    }

    public void setPrecoDiarioBase(BigDecimal precoDiarioBase) {
        this.precoDiarioBase = precoDiarioBase;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public void ativarQuarto() {
        this.estado = EstadoQuarto.ATIVO;
    }

    public void desativarQuarto() {
        this.estado = EstadoQuarto.INATIVO;
    }

    public void repararQuarto() {
        this.estado = EstadoQuarto.MANUNTENCAO;
    }

    @Override
    public String toString() {
        return String.format("Quarto %d (%s) - Preço Base: %,.2f Kz | Cap: %d pessoas [%s]",
                numero, tipo, precoDiarioBase, capacidade, estado);
    }

}
