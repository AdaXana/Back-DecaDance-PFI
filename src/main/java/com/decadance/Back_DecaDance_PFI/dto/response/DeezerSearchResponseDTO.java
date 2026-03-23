package com.decadance.Back_DecaDance_PFI.dto.response;

import java.util.List;

public record DeezerSearchResponseDTO(
    List<DataResponseDTO> data
) {}