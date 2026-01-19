/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author norbe
 */
public class SerializacaoService {
    public static <T> void gravar(String file, List<T> lista) throws FileNotFoundException, IOException{
        ObjectOutputStream oos;
        try (FileOutputStream fos = new FileOutputStream(file)) {
            oos = new ObjectOutputStream(fos);
            oos.writeObject(lista);
        }
        oos.close();
    }
    
    public static <T> List<T> carregar(String file) throws FileNotFoundException, IOException, ClassNotFoundException{
        List<T> lista = new ArrayList<>();
        ObjectInputStream ois;
        try (FileInputStream fis = new FileInputStream(file)) {
            ois = new ObjectInputStream(fis);
            lista = (List<T>) ois.readObject();
        }
        ois.close();
        
        return lista;
    }
    
    public static <T> List<T> carregarFicheiros(String fileName) throws FileNotFoundException, ClassNotFoundException, IOException{
        List<T> lista = null;
        try {
           lista = service.SerializacaoService.carregar(fileName);
        } catch (IOException e) {
            lista = new ArrayList<>();
            service.SerializacaoService.gravar(fileName, lista);
        }
        return lista;

    }
            
}
