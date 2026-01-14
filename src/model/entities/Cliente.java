/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entities;

/**
 *
 * @author juuhl
 */
public class Cliente {

    private Integer id;
    private String nomeCompleto;
    private String telefone;
    private String email;
    private String documento;

    public Cliente(String nomeCompleto, String documento) {
        this.nomeCompleto = nomeCompleto;
        this.documento = documento;
    }

    public Cliente(String nomeCompleto, String telefone, String email, String documento) {
        this.nomeCompleto = nomeCompleto;
        this.telefone = telefone;
        this.email = email;
        this.documento = documento;
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
        return String.format("Cliente: %s [Doc: %s]", nomeCompleto, documento);
    }

}
