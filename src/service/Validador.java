/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

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
    
    public static boolean validarBI(String bi) {
        return bi != null && bi.matches("^[0-9]{9}[A-Z]{2}[0-9]{3}$");
    }
    
    public static boolean validarPassaporte(String passaporte) {
        return passaporte != null && passaporte.matches("^[A-Z]{1,2}[0-9]{6,9}$");
    }
}