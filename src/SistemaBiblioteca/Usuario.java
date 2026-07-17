package SistemaBiblioteca;

public class Usuario {
    private String nome;
    private String cpf;

    public Usuario(String nome, String cpf) {
    if (!cpf.matches(("^\\d{11}$")))
        throw new IllegalArgumentException("cpf invalido.digite novamente");
    this.nome = nome;
    this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }
}
