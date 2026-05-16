package dev.backanderson.projetomastersys.dto;

import dev.backanderson.projetomastersys.domain.Aluno;

import java.time.LocalDate;

public record AlunoRequest(
        String nome,
        LocalDate dataNascimento,
        String sexo,
        String telefone,
        String celular,
        String email,
        String observacao,
        String endereco,
        String numero,
        String complemento,
        String cidade,
        String bairro,
        String estado,
        String cep
) {

    public Aluno toEntity() {
        Aluno aluno = new Aluno();
        preencher(aluno);
        return new Aluno();
    }

    public void preencher(Aluno aluno){
        aluno.setNome(nome);
        aluno.setDataNascimento(dataNascimento);
        aluno.setSexo(sexo);
        aluno.setTelefone(telefone);
        aluno.setCelular(celular);
        aluno.setEmail(email);
        aluno.setObservacao(observacao);
        aluno.setEndereco(endereco);
        aluno.setNumero(numero);
        aluno.setComplemento(complemento);
        aluno.setCidade(cidade);
        aluno.setBairro(bairro);
        aluno.setEstado(estado);
        aluno.setCep(cep);

    }
}
