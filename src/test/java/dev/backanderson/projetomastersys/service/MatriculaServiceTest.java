package dev.backanderson.projetomastersys.service;

import dev.backanderson.projetomastersys.domain.Aluno;
import dev.backanderson.projetomastersys.domain.Matricula;
import dev.backanderson.projetomastersys.dto.MatriculaRequest;
import dev.backanderson.projetomastersys.repository.AlunoRepository;
import dev.backanderson.projetomastersys.repository.MatriculaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
                1L,
                10
        );

        Aluno aluno = new Aluno();
        aluno.setId(1L);


        Matricula matricula = new Matricula();
        matricula.setId(1L);
        matricula.setDiaVencimento(10);
        matricula.setAluno(aluno);

        when(alunoRepository.findById(anyLong()))
                .thenReturn(java.util.Optional.of(aluno));


        when(repository.save(any(Matricula.class)))
                .thenReturn(matricula);

        var resultado = service.cadastrar(request);

        assertNotNull(resultado);
        assertEquals(10, resultado.diaVencimento());

        verify(repository, times(1))
                .save(any(Matricula.class));


    }
}