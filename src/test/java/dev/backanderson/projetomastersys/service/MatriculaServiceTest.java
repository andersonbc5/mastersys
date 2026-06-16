package dev.backanderson.projetomastersys.service;

import dev.backanderson.projetomastersys.domain.Aluno;
import dev.backanderson.projetomastersys.domain.Matricula;
import dev.backanderson.projetomastersys.dto.request.MatriculaRequest;
import dev.backanderson.projetomastersys.repository.AlunoRepository;
import dev.backanderson.projetomastersys.repository.MatriculaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MatriculaServiceTest {

    @Mock
    private MatriculaRepository repository;

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private MatriculaService service;


    @Test
    void cadastrar() {

        MatriculaRequest request = new MatriculaRequest(
                "47233289808",
                10
        );

        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setCpf("47233289808");


        Matricula matricula = new Matricula();
        matricula.setId(1L);
        matricula.setDiaVencimento(10);
        matricula.setAluno(aluno);

        when(alunoRepository.findByCpf(request.cpfAluno()))
                .thenReturn(Optional.of(aluno));


        when(repository.save(any(Matricula.class)))
                .thenReturn(matricula);

        var resultado = service.cadastrar(request);

        assertNotNull(resultado);
        assertEquals(10, resultado.diaVencimento());

        verify(repository, times(1))
                .save(any(Matricula.class));


    }

    @Test
    void buscarPorId() {
        Long id = 1L;
        Matricula matricula = new Matricula();
        matricula.setId(id);

        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setCpf("47233289808");
        matricula.setAluno(aluno);
        matricula.setDiaVencimento(10);

        when(repository.findById(id))
                .thenReturn(Optional.of(matricula));

        var resultado = service.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.id());

        verify(repository, times(1))
                .findById(id);

    }
}