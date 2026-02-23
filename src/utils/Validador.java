/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import exceptions.DomainException;
import java.time.LocalDate;

/**
 *
 * @author juuhl
 */

public class Validador {
    public static boolean validarNome(String nome) {
        return nome != null && nome.matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\s]{2,50}$");
    }

    public static boolean validarTelemovel(String telemovel) {
        return telemovel != null && telemovel.matches("^9[0-9]{8}$");
    }

    public static boolean validarEmail(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,3}$");
    }
    
    public static boolean validarDocumento(String documento) {
        return documento != null && (documento.matches("^[0-9]{9}[A-Z]{2}[0-9]{3}$") || documento.matches("^[A-Z]{1,2}[0-9]{6,9}$"));
    }
    
    public static void validarDatas(LocalDate checkIn, LocalDate checkOut) {
        if ((checkOut.isBefore(checkIn)) || (checkOut.isEqual(checkIn))) {
            throw new DomainException("Check-out deve ser maior que Check-in.");
        }
        if(checkOut.getYear() < LocalDate.now().getYear()  || checkOut.getYear() < LocalDate.now().getYear() ){
              throw new DomainException("As reservas so podem ser efectuadas a partir do ano atual.");
        }
    }
    
}