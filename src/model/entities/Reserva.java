/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entities;

import exceptions.DomainException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.enums.EstadoReserva;

/**
 *
 * @author juuhl
 */
public class Reserva {

    private Integer codReserva;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Integer qtdHospedes;
    private LocalDateTime dataCriacao;
    private Cliente cliente;
    private Quarto quarto;
    private EstadoReserva estado;
    private List<Pagamento> pagamentos;
    private List<ServicoAdicional> servicosAdicionais;
    private static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private static int value = 1;

    public Reserva(LocalDateTime checkIn, LocalDateTime checkOut, Integer qtdHospedes, Quarto quarto) {
        this.codReserva = value++;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.qtdHospedes = qtdHospedes;
        this.quarto = quarto;
        this.dataCriacao = LocalDateTime.now();
        this.pagamentos = new ArrayList<>();
        this.servicosAdicionais = new ArrayList<>();
        this.estado = EstadoReserva.CRIADA;
    }

    //GETTERS
    public Integer getCodReserva() {
        return codReserva;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public Integer getQtdHospedes() {
        return qtdHospedes;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public List<Pagamento> getPagamentos() {
        return Collections.unmodifiableList(pagamentos);
    }

    public List<ServicoAdicional> getServicosAdicionais() {
        return Collections.unmodifiableList(servicosAdicionais);
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public static DateTimeFormatter getFmt() {
        return fmt;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public long getNoites() {
        return ChronoUnit.DAYS.between(checkIn, checkOut);
    }

    //SETTERS
    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }

    public void setQtdHospedes(Integer qtdHospedes) {
        this.qtdHospedes = qtdHospedes;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public void adicionarPagamento(Pagamento p) {
        if (p == null) {
           throw new DomainException("Pagamento nao pode ser estar vazio.");
        }
         this.pagamentos.add(p);
    }

    public void adicionarServico(ServicoAdicional s) {
        if (s == null) {
           throw new DomainException("Servico nao pode ser estar vazio.");
        }
        this.servicosAdicionais.add(s);
    }

    //FUNCOES
    public void validar() {
        if ((this.checkOut.toLocalDate().isBefore(this.checkIn.toLocalDate())) || (this.checkOut.toLocalDate().isEqual(this.checkIn.toLocalDate()))) {
            throw new DomainException("Check-out deve ser maior que Check-in.");
        }
        if (this.qtdHospedes > quarto.getCapacidade()) {
            throw new DomainException("Reserva rejeitada por exceder capacidade.");
        }
    }

    public BigDecimal calcularValorHospedagem() {
        long noites = getNoites();
        BigDecimal precoBase = quarto.getPrecoDiarioBase();
        BigDecimal multiplicador = quarto.getTipo().getMultiplicador();

        return precoBase.multiply(BigDecimal.valueOf(noites)).multiply(multiplicador);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append(String.format("RESERVA #%d\n", codReserva));
        sb.append(String.format("Cliente: %s\n", cliente.getNomeCompleto()));
        sb.append(String.format("Periodo: %s ate %s (%d noites)\n",
                checkIn.format(fmt), checkOut.format(fmt), getNoites()));
        sb.append(String.format("Acomodacao: Quarto %d\n", quarto.getNumero()));
        sb.append(String.format("Valor da Hospedagem: %.2f\n", calcularValorHospedagem()));
        sb.append(String.format("Estado da Reserva: %s\n", estado));
        sb.append(String.format("Servicos Adicionais\n"));
        if(servicosAdicionais == null || servicosAdicionais.isEmpty()){
            sb.append("- Sem servicos adicionais prestados.\n");
        }else{
            for(ServicoAdicional s : servicosAdicionais){
                 sb.append(s.toString()).append("\n");
            }
        }
        sb.append(String.format("\nPagamentos\n"));
        if(servicosAdicionais == null || servicosAdicionais.isEmpty()){
            sb.append("- Sem pagamentos.\n");
        }else{
            for(Pagamento p : pagamentos){
                 sb.append(p.toString()).append("\n");
            }
        }
        sb.append("========================================");
        
       
        
        return sb.toString();
    }

}
