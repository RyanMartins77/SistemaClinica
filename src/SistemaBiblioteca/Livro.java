package SistemaBiblioteca;

public class Livro {
    private String titulo;
    private String autor;
    private boolean emprestado;


    public Livro(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
        this.emprestado = false;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean emprestar(){
        return emprestado = true;
    }
    public boolean devolver(){
        return emprestado = false;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public boolean isEmprestado() {
        return emprestado;
    }
    public void exibirInfo() {
        System.out.println("Título: " + titulo);
        System.out.println("Autor: " + autor);
        System.out.println("Status: " + (emprestado ? "Emprestado" : "Disponível"));
        System.out.println("-------------------------");
    }
}
