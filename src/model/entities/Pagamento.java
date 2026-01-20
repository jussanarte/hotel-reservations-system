/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import model.enums.EstadoPagamento;
import model.enums.MetodoPagamento;

/**
 *
 * @author juuhl
 */
public class Pagamento implements Serializable {

    private BigDecimal valorPago;
    private LocalDate dataPagamento;
    private MetodoPagamento metodo;
    private EstadoPagamento estado;

    public Pagamento(BigDecimal valorPago, LocalDate dataPagamento, MetodoPagamento metodo) {
        this.valorPago = valorPago;
        this.dataPagamento = dataPagamento;
        this.metodo = metodo;
        this.estado = EstadoPagamento.CONFIRMADO;
    }
    
    public Pagamento(BigDecimal valorPago, LocalDate dataPagamento, MetodoPagamento metodo, EstadoPagamento estado) {
        this.valorPago = valorPago;
        this.dataPagamento = dataPagamento;
        this.metodo = metodo;
        this.estado = estado;
    }
    
    //GETTERS
    public BigDecimal getValorPago() {
        return valorPago;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public MetodoPagamento getMetodo() {
        return metodo;
    }

    public EstadoPagamento getEstado() {
        return estado;
    }

    //SETTERS
    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public void setMetodo(MetodoPagamento metodo) {
        this.metodo = metodo;
    }

    public void setEstado(EstadoPagamento estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return String.format("- %s %.2f Kz [%s]",
                dataPagamento.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), valorPago, metodo);
    }
    
    
    
}
