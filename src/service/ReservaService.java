/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import exceptions.DomainException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import model.entities.*;
import model.enums.*;

/**
 *
 * @author juuhl
 */
public class ReservaService {

    private FinanceiroService fs = new FinanceiroService();

    public void confirmarReserva(Reserva r, List<Reserva> reservasConfirmadasExistentes) {
        
        boolean haSobreposicao = verificarSobreposicaoDatas(r, reservasConfirmadasExistentes);
        boolean quartoIndisponivel = r.getQuarto().getEstado() != EstadoQuarto.ATIVO;

        BigDecimal saldo = fs.calcularSaldo(r);
        boolean saldoPositivo = saldo.compareTo(BigDecimal.ZERO) > 0;

        if (quartoIndisponivel || saldoPositivo) {
            throw new DomainException("Reserva #" + r.getCodReserva() + " nao confirmada por quarto indisponivel ou saldo maior que 0");
        }
        if (haSobreposicao) {
            throw new DomainException("Reserva #" + r.getCodReserva() + " sobrepoe outra reserva existente");
        }

        r.setEstado(EstadoReserva.CONFIRMADA);
        reservasConfirmadasExistentes.add(r);
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
        if (!LocalDate.now().isEqual(r.getCheckIn())) {
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

        if (!LocalDate.now().isEqual(r.getCheckOut())) {
            throw new DomainException("Nao foi possivel fazer check-out.");
        }

        r.setEstado(EstadoReserva.CHECKED_OUT);
        r.getQuarto().repararQuarto();
        System.out.println("Check-out realizado. Quarto " + r.getQuarto().getNumero() + " liberado.");
    }

    public void processarPagamento(Reserva r, Pagamento p) {
        if (r.getEstado() == EstadoReserva.CANCELADA) {
            throw new DomainException("Nao e possivel adicionar pagamentos a uma reserva cancelada!");
        }
        r.adicionarPagamento(p);
    }

    public void adicionarServico(Reserva r, ServicoAdicional s) {
        if (r.getEstado() == EstadoReserva.CANCELADA) {
            throw new DomainException("Nao e possivel adicionar servicos adicionais a uma reserva cancelada!");
        }
        r.adicionarServico(s);
    }

    public boolean verificarSobreposicaoDatas(Reserva r, List<Reserva> reservasConfirmadasExistentes) {
        boolean sobreposto = false;
        for (Reserva a : reservasConfirmadasExistentes) {
            if (a.getEstado() == EstadoReserva.CONFIRMADA || a.getEstado() == EstadoReserva.CHECKED_IN) {
                if (a.getQuarto().getNumero().equals(r.getQuarto().getNumero())) {
                    sobreposto = a.getCheckIn().isBefore(r.getCheckOut()) && r.getCheckIn().isBefore(a.getCheckOut());
                    if (sobreposto) {
                        throw new DomainException(
                                "Conflito de datas: o quarto " + r.getQuarto().getNumero()
                                + " ja possui uma reserva confirmada entre "
                                + a.getCheckIn() + " e "
                                + a.getCheckOut()
                        );
                    }
                }
            }
        }
        return sobreposto;
    }
}
