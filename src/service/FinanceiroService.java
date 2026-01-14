/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.math.BigDecimal;
import model.entities.Pagamento;
import model.entities.Reserva;
import model.entities.ServicoAdicional;
import model.enums.EstadoPagamento;

/**
 *
 * @author juuhl
 */
public class FinanceiroService {

    public BigDecimal calcularTotalServicos(Reserva r) {
        BigDecimal total = BigDecimal.ZERO;
        long noites = r.getNoites();
        
        for (ServicoAdicional servico : r.getServicosAdicionais()){
           total = total.add(servico.calcularTotal(noites));
        }
        return total;
    }

    public BigDecimal calcularTotalReserva(Reserva r) {
        return new BigDecimal(0);
    }

    public BigDecimal calcularSaldo(Reserva r) {
        BigDecimal totalReserva = calcularTotalReserva(r);
        BigDecimal totalPago = calcularTotalPago(r);
        return totalReserva.subtract(totalPago);

    }

    public BigDecimal calcularTotalPago(Reserva r) {
        BigDecimal totalPago = BigDecimal.ZERO;
        for (Pagamento p : r.getPagamentos()) {
   
            if (p.getEstado() == EstadoPagamento.CONFIRMADO) {
                totalPago = totalPago.add(p.getValorPago());
            }
        }
        return totalPago;
    }

}
