package com.decadance.Back_DecaDance_PFI.dto.external;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class ExportifyCsvDTO  {
    
    @CsvBindByPosition(position = 1) 
    private String trackName;

    @CsvBindByPosition(position = 3) 
    private String artistName;

}