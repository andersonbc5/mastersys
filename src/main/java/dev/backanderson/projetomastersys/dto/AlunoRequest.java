package dev.backanderson.projetomastersys.dto;

import dev.backanderson.projetomastersys.domain.Aluno;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record AlunoRequest(
        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 150, message = "Nome deve ter no máximo 150 caracteres")
        String nome,

        @CPF(message = "CPF inválido")
        @NotBlank(message = "CPF é obrigatório")
        String cpf,

        @Past(message = "Data de nascimento deve ser no passado")
        LocalDate dataNascimento,

        @Size(max = 1, message = "Sexo deve ter no máximo 1 caractere")
        String sexo,

        @Size(max = 30, message = "Telefone deve ter no máximo 30 caracteres")
        String telefone,

        @Size(max = 30, message = "Celular deve ter no máximo 30 caracteres")
        String celular,

        @Email(message = "Email inválido")
        @Size(max = 150, message = "Email deve ter no máximo 150 caracteres")
        String email,
        String observacao,

        @Size(max = 150, message = "Endereço deve ter no máximo 150 caracteres")
        String endereco,

        @Size(max = 20, message = "Número deve ter no máximo 20 caracteres")
        String numero,

        @Size(max = 100, message = "Complemento deve ter no máximo 100 caracteres")
        String complemento,

        @Size(max = 100, message = "Bairro deve ter no máximo 100 caracteres")
        String bairro,

        @Size(max = 100, message = "Cidade deve ter no máximo 100 caracteres")
        String cidade,

        @Size(max = 2, message = "Estado deve ter no máximo 2 caracteres")
        String estado,

        @Size(max = 20, message = "CEP deve ter no máximo 20 caracteres")
        String cep
) {

    public Aluno toEntity() {
        Aluno aluno = new Aluno();
        preencher(aluno);
        return aluno;
    }

    public void preencher(Aluno aluno){
        aluno.setNome(nome);
        aluno.setCpf(cpf);
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
