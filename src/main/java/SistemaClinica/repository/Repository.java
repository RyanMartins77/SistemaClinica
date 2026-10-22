package SistemaClinica.repository;

import SistemaClinica.exception.ChaveInexistente;
import SistemaClinica.exception.ChaveJaExistenteException;

import java.util.*;

public class Repository<K,V> {
    private Map<K,V> listas = new HashMap<>();

    public void adicionar(K chave, V valor)throws ChaveJaExistenteException{
        if (listas.containsKey(chave)){
            throw new ChaveJaExistenteException("chave ja registrada. ");
        }
        listas.put(chave,valor);

    }
    public void remove(K valor)throws ChaveInexistente{
        if (listas.remove(valor) == null){
            throw new ChaveInexistente("Essa chave é inexistente");
        }
    }

    public Optional<V> buscar(K value){
       return Optional.ofNullable(listas.get(value));
    }
    public Collection<V> valores(){
        return  listas.values();
    }
    public void listar(){
        for (Map.Entry<K,V> teste : listas.entrySet() ){
            System.out.println(teste.getKey() + " " +  teste.getValue());
        }
    }
}
