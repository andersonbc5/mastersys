package dev.backanderson.projetomastersys.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> tratarErroValidacao(MethodArgumentNotValidException ex) {
        List<String> mensagem = ex.getBindingResult()
                .getFieldErrors()
                .stream().
                map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .toList();

        ErroResponse erroResponse = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação",
                mensagem
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResponse);

    }

    //422
    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ErroResponse> tratarRegraNegocio(RegraDeNegocioException ex) {
        ErroResponse erroResponse = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de regra de negócio",
                List.of(ex.getMessage())
        );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erroResponse);

    }

    //404
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> tratarRecursoNaoEncontrado(RecursoNaoEncontradoException ex) {
        ErroResponse erroResponse = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Recurso não encontrado",
                List.of(ex.getMessage())
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroResponse);

    }


    //409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroResponse> tratarViolacaoIntegridade(DataIntegrityViolationException ex) {
        String causa = ex.getRootCause() != null ? ex.getRootCause().getMessage().toLowerCase() : "";

        String mensagem;
        if (causa.contains("email") || causa.contains("unique") || causa.contains("duplicate")) {
            mensagem = "Já existe um registro com esses dados";
        } else if (causa.contains("foreign key") || causa.contains("constraint")) {
            mensagem = "Operação não permitida: registro vinculado a outro recurso";
        } else {
            mensagem = "Violação de integridade de dados";
        }

        ErroResponse erroResponse = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Conflito de dados",
                List.of(mensagem)
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erroResponse);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErroResponse> handleAuthorizationDenied(
            AuthorizationDeniedException ex) {

        ErroResponse erroResponse = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                "Acesso negado",
                List.of("Você não possui permissão para acessar este recurso")
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(erroResponse);
    }



    //Erro 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> tratarErroGenerico(Exception ex) {
        log.error("Erro interno do servidor", ex);

        ErroResponse erroResponse = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno do servidor",
                List.of("Ocorreu um erro inesperado, tente novamente mais tarde")
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erroResponse);
    }




}
