package SistemaBiblioteca;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Biblioteca {
    ArrayList<Livro> livros;
    ArrayList<Usuario> usuarios;
    ArrayList<Emprestimo> emprestimos;

    public Biblioteca() {
        this.livros = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.emprestimos = new ArrayList<>();
    }
    public void AddLivro(Livro l){
        livros.add(l);
        salvar();
        System.out.println("livro cadastrado com sucesso");
    }
    public void cadastrarUsuario(Usuario usuario){
        usuarios.add(usuario);
        System.out.println("usuario  cadastrado com sucesso");
    }
    public boolean pesquisarCPF(String cpf){
        for (Emprestimo e1 : emprestimos){
            if (e1.usuario.getCpf().equals(cpf)){
                return true;
            }
        }
        return false;
    }
    public Usuario EmprestimoCPF(String cpf){
        for (Usuario u1 : usuarios){
            if (u1.getCpf().equals(cpf)){
                return u1;
            }
        }
        return null;
    }
    public Livro pesquisarLivro(String titulo) {
        for (Livro l1 : livros){
            if (l1.getTitulo().equalsIgnoreCase(titulo)){
                return l1;
            }
        }
        return null;
    }
    public void EmprestarLivro(String cpf, String titulo){
        Usuario usuario = EmprestimoCPF(cpf);
        Livro livro = pesquisarLivro(titulo);
        if (usuario == null){
            System.out.println("usuario nao encontrado");
            return;
        }
        if (livro == null){
            System.out.println("livro nao encontrado");
            return;
        }
        if (livro.isEmprestado()){
            System.out.println("esse livro ja foi emprestado");
            return;
        }
        if (pesquisarCPF(cpf)){
            System.out.println("esse cpf ja pegou um livro");
            return;
        }
        livro.emprestar();
        emprestimos.add(new Emprestimo(usuario, livro));
        System.out.println("livro emprestado com sucesso");

    }
    public void Devolver(String cpf){
        for (int i = 0; i < emprestimos.size(); i++) {
            if (emprestimos.get(i).getUsuario().getCpf().equals(cpf)){
                emprestimos.get(i).getLivro().devolver();
                emprestimos.remove(i);
                System.out.println("Livro devolvido com sucesso!");
                return;
            }
        }
        System.out.println("Esse CPF não possui livro emprestado.");
    }

    public void salvar(){
        try(FileWriter fw = new FileWriter("Livros.txt")){
            for (Livro l1 : livros){
                fw.write(l1.getAutor() +";" +  l1.getAutor()+ ";" +l1.isEmprestado());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
