package com.edisonla.evaluacion_desempeno.services;

import com.edisonla.evaluacion_desempeno.dtos.ChangePasswordRequest;
import com.edisonla.evaluacion_desempeno.dtos.NominaUsuarioDto;
import com.edisonla.evaluacion_desempeno.dtos.ResultadoImportacionDto;
import com.edisonla.evaluacion_desempeno.dtos.UsuarioDto;
import com.edisonla.evaluacion_desempeno.entities.Usuario;
import com.edisonla.evaluacion_desempeno.mappers.UsuarioMapper;
import com.edisonla.evaluacion_desempeno.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.edisonla.evaluacion_desempeno.enums.Roles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioService {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final UsuarioRepository repository;
    private final UsuarioMapper usuarioMapper;
    private final JwtService jwtService;

    @Transactional(readOnly = true)
    public boolean checkIsAdmin(String token) {
        Usuario me = repository.findByEmail(jwtService.extractEmail(token))
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con token: " + token));
        return (me.getRoles() != null && me.getRoles().toLowerCase().contains(Roles.ADMIN.toString().toLowerCase()));
    }

    @Transactional(readOnly = true)
    public Iterable<UsuarioDto> getAll(String token) {
        if (this.checkIsAdmin(token)) {
            List<Usuario> usuarios = repository.findAll();
            return usuarios
                    .stream()
                    .map(usuarioMapper::toDto) //method reference reemplazo de (e -> mapper.toDto(e))
                    .toList();
        } else {
            throw new AuthorizationDeniedException("No tiene permisos para realizar esta accion");
        }
    }


    @Transactional(readOnly = true)
    public UsuarioDto whoAmI(String token) {
        Usuario me = repository.findByEmail(jwtService.extractEmail(token))
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con token: " + token));
        return usuarioMapper.toDto(me);
    }

    @Transactional(readOnly = true)
    public UsuarioDto get(String token, Long id) {
        if (this.checkIsAdmin(token)) {
            Usuario found = repository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con token: " + token));
            return usuarioMapper.toDto(found);
        } else {
            throw new AuthorizationDeniedException("No tiene permisos para realizar esta accion");
        }
    }

    @Transactional
    public UsuarioDto update(String token, Long id, UsuarioDto dto) {
        if(checkIsAdmin(token)) {
            Usuario found = repository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con token: " + token));
            Usuario updated = usuarioMapper.toEntity(dto);
            updated.setId(found.getId());
            updated.setUltimaModificacion(new Date());
            Usuario res = repository.save(updated);
            return usuarioMapper.toDto(res);
        } else {
            throw new AuthorizationDeniedException("No tiene permisos para realizar esta accion");
        }
    }

    @Transactional
    public UsuarioDto updateMyself(String token, UsuarioDto dto) {
        Usuario me = repository.findByEmail(jwtService.extractEmail(token))
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con token: " + token));
        Usuario updated = usuarioMapper.toEntity(dto);
        updated.setId(me.getId());
        updated.setUltimaModificacion(new Date());
        Usuario res = repository.save(updated);
        return usuarioMapper.toDto(res);
    }

    @Transactional
    public void delete(String token, Long id) {
        if(checkIsAdmin(token)) {
            Usuario me = repository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con token: " + token));
            repository.delete(me);
        } else {
            throw new AuthorizationDeniedException("No tiene permisos para realizar esta accion");
        }
    }

    @Transactional
    public void deleteMyself(String token) {
        Usuario me = repository.findByEmail(jwtService.extractEmail(token))
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el elemento con token: " + token));
        repository.delete(me);
    }

    @Transactional
    public ResultadoImportacionDto importarNomina(List<NominaUsuarioDto> nomina){
        int total  = nomina.size();
        int creados  = 0 ;
        int actualizados = 0;
        int errores = 0;
        List<String> mensajeError = new ArrayList<>();
        List<Usuario> usuariosCargados = new ArrayList<>();
        for (NominaUsuarioDto dto : nomina){
            try{
                Optional<Usuario> existenteOpt = repository.findByLegajo(dto.legajo());

                if (existenteOpt.isPresent()){
                   Usuario existente = existenteOpt.get();

                    existente.setNombre(dto.nombre());
                    existente.setApellido(dto.apellido());
                    existente.setEmail(dto.email());
                    existente.setIncorporacion(dto.fechaInco());
                    existente.setCuil(dto.cuil());
                    existente.setUltimaModificacion(new Date());

                    repository.save(existente);
                    actualizados++;
                }else{
                    Usuario nuevo = Usuario.builder()
                            .username(dto.email()) // ej: email como username
                            .email(dto.email())
                            .password(dto.cuil()) // su cuil cono password
                            .incorporacion(dto.fechaInco())
                            .legajo(dto.legajo())
                            .cuil(dto.cuil())
                            .nombre(dto.nombre())
                            .apellido(dto.apellido())
                            .creado(new Date())
                            .ultimaModificacion(new Date())
                            .build();

                    usuariosCargados.add(repository.save(nuevo));

                    creados++;
                }

            }catch (Exception e){
                errores ++;
                mensajeError.add("Legajo" +dto.legajo() + ":" + e.getMessage());
            }
        }

        return new ResultadoImportacionDto(
                total,creados,actualizados,errores,mensajeError,usuariosCargados);
    }

    public List<NominaUsuarioDto> leerNominaExcel(MultipartFile file) throws IOException {

        List<NominaUsuarioDto> nomina = new ArrayList<>();

        try(Workbook workbook = WorkbookFactory.create(file.getInputStream())){
            Sheet sheet =  workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null)
                    continue;


                String legajoStr = formatter.formatCellValue(row.getCell(0));
                if (legajoStr == null || legajoStr.isBlank())
                    continue;
                int legajo = Integer.parseInt(legajoStr);


                String cuil = formatter.formatCellValue(row.getCell(1));


                String nombre = formatter.formatCellValue(row.getCell(2));


                String apellido = formatter.formatCellValue(row.getCell(3));


                String email = formatter.formatCellValue(row.getCell(4));


                // fechaInco (LocalDate)
                LocalDate fechaInco = null;
                Cell fechaCell = row.getCell(5);
                if (fechaCell != null) {
                    if (fechaCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(fechaCell)) {
                        // fecha tipo fecha de Excel
                        fechaInco = fechaCell.getDateCellValue()
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
                    } else {
                        // fecha en texto, ej "2024-01-31"
                        String fechaStr = formatter.formatCellValue(fechaCell);
                        if (!fechaStr.isBlank()) {
                            fechaInco = LocalDate.parse(fechaStr, DATE_FORMAT);
                        }
                    }
                }

                NominaUsuarioDto dto = new NominaUsuarioDto(
                        legajo,
                        cuil,
                        nombre,
                        apellido,
                        email,
                        fechaInco
                );

                nomina.add(dto);
            }
        }

        return nomina;
    }


    @Transactional
    public ResultadoImportacionDto importarNominaDesdeExcel(MultipartFile file) throws IOException {
        List<NominaUsuarioDto> nomina = this.leerNominaExcel(file);
        return importarNomina(nomina); // reutilizamos tu lógica existente
    }


}
