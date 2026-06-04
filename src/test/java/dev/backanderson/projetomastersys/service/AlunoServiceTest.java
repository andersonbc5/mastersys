package dev.backanderson.projetomastersys.service;

import dev.backanderson.projetomastersys.domain.Aluno;
import dev.backanderson.projetomastersys.dto.AlunoRequest;
import dev.backanderson.projetomastersys.repository.AlunoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoService alunoService;

    @Test
    void cadastrar() {

        AlunoRequest request = new AlunoRequest(
                "Anderson",
                "12345678910",
                LocalDate.of(1997, 12, 31),
                "M",
                "19999999999",
                "199999999992",
                "anderson@gmail.com",
                "Iniciante",
                "Rua a",
                "300",
                "casa",
                "centro",
                "campinas",
                "SP",
                "13186531");

        Aluno alunoSalvo = new Aluno();
        alunoSalvo.setId(1L);
        alunoSalvo.setNome(request.nome());

        when(alunoRepository.save(any(Aluno.class)))
                .thenReturn(alunoSalvo);

        var resultado = alunoService.cadastrar(request);

        assertNotNull(resultado);
        assertEquals("Anderson", resultado.nome());

        verify(alunoRepository, times(1))
                .save(any(Aluno.class));


    }

    @Test
    void listarPorId() {
        Long id = 1L;
        Aluno aluno = new Aluno();
        aluno.setId(id);
        aluno.setNome("Anderson");

        when(alunoRepository.findById(id))
                .thenReturn(java.util.Optional.of(aluno));

        var resultado = alunoService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals("Anderson", resultado.nome());

        verify(alunoRepository, times(1))
                .findById(id);

    }



}