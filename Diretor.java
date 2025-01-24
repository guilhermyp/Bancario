public class Diretor extends Funcionario {
    public Diretor(String nome, String endereco, String rg, String cpf, String telefone, String matricula, String senha) {
        super(nome, endereco, rg, cpf, telefone, matricula, senha);
    }

    public void cadastrarGerente(Gerente gerente) {
        Banco.getGerentes().add(gerente);
    }

    public void excluirGerente(Gerente gerente) {
        Banco.getGerentes().remove(gerente);
    }
}
