/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import model.enums.EstadoPagamento;
import model.enums.MetodoPagamento;

/**
 *
 * @author juuhl
 */
public class Pagamento {

    private BigDecimal valorPago;
    private LocalDateTime dataPagamento;
    private MetodoPagamento metodo;
    private EstadoPagamento estado;

    public Pagamento(BigDecimal valorPago, LocalDateTime dataPagamento, MetodoPagamento metodo) {
        this.valorPago = valorPago;
        this.dataPagamento = dataPagamento;
        this.metodo = metodo;
        this.estado = EstadoPagamento.PENDENTE;
    }
    
    //GETTERS
    public BigDecimal getValorPago() {
        return valorPago;
    }

    public LocalDateTime getDataPagamento() {
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

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public void setMetodo(MetodoPagamento metodo) {
        this.metodo = metodo;
    }

    public void setEstado(EstadoPagamento estado) {
        this.estado = estado;
    }
   
  
    
}
