/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import exceptions.DomainException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import model.entities.*;
import model.enums.*;

/**
 *
 * @author juuhl
 */
public class ReservaService {

    FinanceiroService fs = new FinanceiroService();

    public void confirmarReserva(Reserva r) {

        r.validar();

        boolean quartoIndisponivel = r.getQuarto().getEstado() != EstadoQuarto.ATIVO;

        BigDecimal saldo = fs.calcularSaldo(r);
        boolean saldoInsuficiente = saldo.compareTo(BigDecimal.ZERO) < 0;

        if (quartoIndisponivel || saldoInsuficiente) {
            throw new DomainException("Não foi possível confirmar a reserva. Verifique o estado do quarto ("
                    + r.getQuarto().getEstado() + ") ou o saldo financeiro.");
        }

        r.setEstado(EstadoReserva.CONFIRMADA);
        System.out.println("Reserva #" + r.getCodReserva() + " confirmada para o cliente " + r.getCliente().getNomeCompleto());
    }

    public void cancelarReserva(Reserva r) {
        if(r.getEstado() == EstadoReserva.CHECKED_IN || r.getEstado() == EstadoReserva.CHECKED_OUT){
            throw new DomainException("Nao e possivle cancelar");
        }
        r.setEstado(EstadoReserva.CANCELADA);
        System.out.println("Reserva #" + r.getCodReserva() + " cancelada para o cliente " + r.getCliente().getNomeCompleto());
    }

    public void realizarCheckIn(Reserva r) {
        if (!LocalDateTime.now().isEqual(r.getCheckIn())) {
            throw new DomainException("Nao foi possivel fazer check-in.");
        }
        r.setEstado(EstadoReserva.CHECKED_IN);
        r.getQuarto().setEstado(EstadoQuarto.INATIVO);
        System.out.println("Check-in realizado. Quarto " + r.getQuarto().getNumero() + " agora está OCUPADO.");
    }

    public void realizarCheckOut(Reserva r) {
        r.setEstado(EstadoReserva.CHECKED_OUT);
        r.getQuarto().setEstado(EstadoQuarto.ATIVO);
        System.out.println("Check-out realizado. Quarto " + r.getQuarto().getNumero() + " liberado.");
    }

    public void processarPagamento(Reserva r, Pagamento p) {
        if (p == null) {
            throw new DomainException("Pagamento não pode ser nulo.");
        }
        r.getPagamentos().add(p);
    }

    public void adicionarServico(Reserva r, ServicoAdicional s) {
        if (s == null) {
            throw new DomainException("Serviço não pode ser nulo.");
        }
        r.getServicosAdicionais().add(s);
    }
}
