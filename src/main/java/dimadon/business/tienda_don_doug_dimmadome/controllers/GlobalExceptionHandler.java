package dimadon.business.tienda_don_doug_dimmadome.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dimadon.business.tienda_don_doug_dimmadome.exceptions.CredencialesIncorrectasException;
import dimadon.business.tienda_don_doug_dimmadome.exceptions.UsuarioInactivoException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Manejar Credenciales Incorrectas
    @ExceptionHandler(CredencialesIncorrectasException.class)
    public ResponseEntity<Map<String, String>> handleCredencialesIncorrectas(CredencialesIncorrectasException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED); // 401
    }

    // Manejar Usuario Inactivo
    @ExceptionHandler(UsuarioInactivoException.class)
    public ResponseEntity<Map<String, String>> handleUsuarioInactivo(UsuarioInactivoException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN); // 403
    }

    // Manejar Excepciones Generales
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        ex.printStackTrace();
        Map<String, String> response = new HashMap<>();
        response.put("message", "Ha ocurrido un error inesperado");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); // 500
    }
}
