/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entities;

import exceptions.DomainException;
import java.io.Serializable;
import utils.Validador;

/**
 *
 * @author Isabel Marques, Jussana Paim, Norberto Cassoma, Oldmar Filindo
 */
public class Cliente implements Serializable {

    private Integer id;
    private String nomeCompleto;
    private String telefone;
    private String email;
    private String documento;

    public Cliente(String nomeCompleto, String documento) {
        if (Validador.validarNome(nomeCompleto)) {
            this.nomeCompleto = nomeCompleto;
        }else{
            throw new DomainException("Nome Invalido!");
        }
        if (Validador.validarDocumento(documento)) {
            this.documento = documento;
        }else{
             throw new DomainException("Documento Invalido!");
        }
    }

    public Cliente(String nomeCompleto, String telefone, String email, String documento) {
        if (Validador.validarNome(nomeCompleto)) {
            this.nomeCompleto = nomeCompleto;
        } else {
            throw new DomainException("Nome invalido!");
        }
        if (Validador.validarDocumento(documento)) {
            this.documento = documento;
        } else {
            throw new DomainException("Documento invalido!");
        }
        if (Validador.validarTelemovel(telefone)) {
            this.telefone = telefone;
        }
        if (Validador.validarEmail(email)) {
            this.email = email;
        }
    }

    public Cliente() {
    }

    //GETTERS
    public Integer getId() {
        return id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getDocumento() {
        return documento;
    }

    //SETTERS
    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("%s [Documento: %s]", nomeCompleto, documento);
    }

}
