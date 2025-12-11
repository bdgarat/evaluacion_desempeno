package com.edisonla.evaluacion_desempeno.dtos;

public record ChangePasswordRequest(String oldPassword, String newPassword, String repeatNewPassword) {
}
