package com.edisonla.evaluacion_desempeno.controllers;


import com.edisonla.evaluacion_desempeno.dtos.NominaUsuarioDto;
import com.edisonla.evaluacion_desempeno.dtos.ResultadoImportacionDto;
import com.edisonla.evaluacion_desempeno.dtos.UsuarioDto;
import com.edisonla.evaluacion_desempeno.services.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    UsuarioService service;

    private static final String urlBase = "/api/usuarios";

    @GetMapping
    public ResponseEntity<Object> whoAmI(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) {
        try {
            UsuarioDto dto = service.whoAmI(token);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/list")
    public ResponseEntity<Object> list(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) {
        try {
            Iterable<UsuarioDto> list = service.getAll(token);
            return ResponseEntity.ok(list);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (AuthorizationDeniedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateMyself(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token,
                             @RequestBody UsuarioDto usuarioDto) {
        try {
            UsuarioDto dto = service.updateMyself(token, usuarioDto);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteMyself(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) {
        try {
            service.deleteMyself(token);
            return null;
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token,
                                      @PathVariable Long id) {
        try {
            UsuarioDto dto = service.get(token, id);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (AuthorizationDeniedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token,
                                         @PathVariable Long id,
                                         @RequestBody UsuarioDto usuarioDto) {
        try {
            UsuarioDto dto = service.update(token, id, usuarioDto);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (AuthorizationDeniedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token,
                                         @PathVariable Long id) {
        try {
            service.delete(token, id);
            return null;
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (AuthorizationDeniedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/nomina")
    public ResponseEntity<ResultadoImportacionDto> importarNomina(@RequestBody List<NominaUsuarioDto> nomina){
        ResultadoImportacionDto res = service.importarNomina(nomina);
        return ResponseEntity.ok(res);
    }

    @PostMapping(
            value = "/nomina/excel",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> importarNominaExcel(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(buildResultadoError("El archivo está vacío"));
        }

        try {
            ResultadoImportacionDto res = service.importarNominaDesdeExcel(file);
            return ResponseEntity.ok(res);

        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildResultadoError("Error leyendo el archivo Excel: " + e.getMessage()));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildResultadoError("Error en la importación de nómina: " + e.getMessage()));
        }
    }

    private ResultadoImportacionDto buildResultadoError(String mensaje) {
        return new ResultadoImportacionDto(
                0,                         // total
                0,                         // creados
                0,                         // actualizados
                1,                         // errores
                List.of(mensaje),          // mensajes de error
                List.of()                  // usuariosCargados
        );
    }
}
