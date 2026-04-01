package com.decadance.Back_DecaDance_PFI.dto.response;

public record UserResponseDTO(
    Long idUser,
    String username,
    String email,
    String image,
    String role,
    Integer played,
    Integer victories,
    Integer successes,
    Integer fails,
    Boolean isDeezerLinked
) {

}