package SistemaClinica.domain;

public class Medico {
    private String nome;
    private String crm;
    private String especialidade;

    public Medico(String nome, String crm, String especialidade) {
        if (!nome.matches("^[A-Za-z]+$"))
            throw new IllegalArgumentException("nome invalido. ");
        if (!crm.matches("^\\d{4,6}\\-\\d{2}/[A-Z]{2}$"))
            throw new IllegalArgumentException("CRM INVALIDO. ");
        if (!especialidade.matches("^[A-Za-z]*$"))
            throw new IllegalArgumentException("especialidade invalido. ");
        this.nome = nome;
        this.crm = crm;
        this.especialidade = especialidade;
    }

    public String getNome() {
        return nome;
    }

    public String getCrm() {
        return crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    @Override
    public String toString() {
        return "Medico{" +
                "nome='" + nome + '\'' +
                ", crm='" + crm + '\'' +
                ", especialidade='" + especialidade + '\'' +
                '}';
    }
}
