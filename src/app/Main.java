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
        Quarto q = new Quarto(TipoQuarto.STANDARD, new BigDecimal(25000), 2);
        Cliente c = new Cliente();
        Reserva r = new Reserva(LocalDateTime.now(), LocalDateTime.now().plusDays(3), 4, q);
        r.setCliente(c);
        
        try {
            System.out.println("Noites: " + r.getNoites());
            System.out.println("Valor da hospedagem: " + r.calcularValorHospedagem());
            System.out.println("Data: " + r.getCheckIn().format(Reserva.getFmt()));
            System.out.println("Data: " + r.getCheckOut().format(Reserva.getFmt()));
            
            System.out.println(r);
            
        } catch (DomainException e) {
            System.err.println(e.getMessage());
        }
        
    }
    
}
