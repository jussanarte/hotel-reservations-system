/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import exceptions.DomainException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import model.entities.*;
import model.enums.*;
import service.FinanceiroService;
import service.ReservaService;

/**
 *
 * @author juuhl
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ReservaService rs = new ReservaService();
        FinanceiroService fs = new FinanceiroService();
        Quarto q2 = new Quarto(TipoQuarto.DELUXE, new BigDecimal(25000), 20);
        Cliente c = new Cliente("jussana", "ASD");
        Reserva r = new Reserva(LocalDateTime.now(), LocalDateTime.now().plusDays(3), 4, q2);

        r.setCliente(c);

        try {
            rs.adicionarServico(r, new ServicoAdicional("ETC", TipoServico.LAVANDARIA, new BigDecimal(2000), 4, FormaCobranca.POR_UNIDADE));
            rs.adicionarServico(r, new ServicoAdicional("ETC", TipoServico.TRANSPORTE, new BigDecimal(20000), 4, FormaCobranca.FIXO));
            rs.adicionarServico(r, new ServicoAdicional("ETC", TipoServico.PEQUENO_ALMOCO, new BigDecimal(5000), 4, FormaCobranca.POR_NOITE));
             
            rs.processarPagamento(r, new Pagamento(fs.calcularTotalReserva(r), LocalDateTime.now(), MetodoPagamento.TPA, EstadoPagamento.CONFIRMADO));

           rs.confirmarReserva(r);

        } catch (DomainException e) {
            System.err.println(e.getMessage());

        }
    }
}
