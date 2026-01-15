/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import exceptions.DomainException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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
        boolean saldoPositivo = saldo.compareTo(BigDecimal.ZERO) > 0;

        if (quartoIndisponivel || saldoPositivo) {
            throw new DomainException("Reserva nao confirmada.");
        }

        r.setEstado(EstadoReserva.CONFIRMADA);
        System.out.println("Reserva #" + r.getCodReserva() + " confirmada para o cliente " + r.getCliente().getNomeCompleto());
    }

    public void cancelarReserva(Reserva r) {
        if (r.getEstado() == EstadoReserva.CHECKED_IN || r.getEstado() == EstadoReserva.CHECKED_OUT) {
            throw new DomainException("Operacao rejeitada.");
        }
        r.setEstado(EstadoReserva.CANCELADA);
        System.out.println("Reserva #" + r.getCodReserva() + " cancelada para o cliente " + r.getCliente().getNomeCompleto());
    }

    public void realizarCheckIn(Reserva r) {
        if (!LocalDateTime.now().toLocalDate().isEqual(r.getCheckIn().toLocalDate())) {
            throw new DomainException("Nao foi possivel fazer check-in.");
        }

        if (r.getEstado() == EstadoReserva.CANCELADA) {
            throw new DomainException("Nao e possível fazer check-in de uma reserva cancelada.");
        }

        r.setEstado(EstadoReserva.CHECKED_IN);
        r.getQuarto().desativarQuarto();
        System.out.println("Check-in confirmado para " + r.getCliente().getNomeCompleto());
    }

    public void realizarCheckOut(Reserva r) {
        if (r.getEstado() != EstadoReserva.CHECKED_IN) {
            throw new DomainException("So pode fazer check-out apos fazer check-in");
        }
        
        if (!LocalDateTime.now().toLocalDate().isEqual(r.getCheckOut().toLocalDate())) {
            throw new DomainException("Nao foi possivel fazer check-out.");
        }
        
        r.setEstado(EstadoReserva.CHECKED_OUT);
        r.getQuarto().repararQuarto();
        System.out.println("Check-out realizado. Quarto " + r.getQuarto().getNumero() + " liberado.");
    }

    public void processarPagamento(Reserva r, Pagamento p) {
        r.adicionarPagamento(p);
    }

    public void adicionarServico(Reserva r, ServicoAdicional s) {
        r.adicionarServico(s);
    }

    public void verificarSobreposicaoDatas(Reserva r, List<Reserva> reservasExistentes) {

    }
}
