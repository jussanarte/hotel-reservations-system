/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import model.enums.EstadoQuarto;
import model.enums.TipoQuarto;

/**
 *
 * @author juuhl
 */
public class Quarto implements Serializable {

    private Integer numero;
    private TipoQuarto tipo;
    private BigDecimal precoDiarioBase;
    private Integer capacidade;
    private EstadoQuarto estado;
    private static int contador = 1;

    public Quarto(TipoQuarto tipo, BigDecimal precoDiarioBase, Integer capacidade) {
        this.numero = contador++;
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
    
    public static void sincronizarContador(int novoValor) {
        contador = novoValor;
    }

    @Override
    public String toString() {
        return String.format("Quarto %d (%s) - Preco Base: %,.2f Kz | Capacidade Maxima: %d pessoas [%s]",
                numero, tipo, precoDiarioBase, capacidade, estado);
    }

}
