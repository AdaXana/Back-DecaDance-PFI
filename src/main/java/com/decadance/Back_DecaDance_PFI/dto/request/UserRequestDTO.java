package com.decadance.Back_DecaDance_PFI.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(

    @NotBlank(message = "El nombre de usuario es obligatorio") 
    @Size(min = 2, max = 50, message = "El usuario debe tener entre 2 y 50 caracteres")
    String username,
    
    @NotBlank(message = "El email es obligatorio") 
    @Email(message = "El formato del email no es válido") 
    String email,

    @NotBlank(message = "La contraseña es obligatoria")  
    @Size(min = 8, max = 15, message = "La contraseña debe tener entre 8 y 12 caracteres") 
    String password,


    String image
) {

}