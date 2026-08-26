/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entities;

import exceptions.DomainException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import model.enums.EstadoPagamento;
import model.enums.MetodoPagamento;

/**
 *
 * @author Isabel Marques, Jussana Paim, Norberto Cassoma, Oldmar Filindo
 */
public class Pagamento implements Serializable {

    private BigDecimal valorPago;
    private LocalDate dataPagamento;
    private MetodoPagamento metodo;
    private EstadoPagamento estado;

    public Pagamento(BigDecimal valorPago, LocalDate dataPagamento, MetodoPagamento metodo) {
        this.valorPago = valorPago;
        if (dataPagamento.getYear() < LocalDate.now().getYear()) {
            throw new DomainException("O pagamento deve ser feito a partir do ano atual.");
        } else {
            this.dataPagamento = dataPagamento;
        }

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

    public void setMetodo(MetodoPagamento metodo) {
        this.metodo = metodo;
    }

    @Override
    public String toString() {
        return String.format("- %s %.2f Kz [%s]",
                dataPagamento.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), valorPago, metodo);
    }

}
