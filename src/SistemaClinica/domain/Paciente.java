package SistemaClinica.domain;

public class Paciente {
    private String nome;
    private String cpf;
    private int idade;
    private String telefone;

    public Paciente(String nome, String cpf, int idade, String telefone) {
        if (!cpf.matches("^(\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2})$"))
            throw new IllegalArgumentException("cpf invalido");
        if (!nome.matches("^[A-Za-z]+$")){
            throw new IllegalArgumentException("Nome invalido.");
        }
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public int getIdade() {
        return idade;
    }

    public String getTelefone() {
        return telefone;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", idade=" + idade +
                ", telefone='" + telefone + '\'' +
                '}';
    }
}
