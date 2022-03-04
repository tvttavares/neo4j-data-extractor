package com.tvttavares.neo4jdataextractor.format;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.tvttavares.neo4jdataextractor.neo4j.model.UserVehicleDTO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

public class UserVehicleCsvDataFormat implements DataFormat {

    private static final String FILE_NAME = "user_vehicleId.csv";

    List<UserVehicleDTO> userVehicleList;

    public UserVehicleCsvDataFormat(List<UserVehicleDTO> userVehicleList) {
        this.userVehicleList = userVehicleList;
    }

    public void generateFile(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=utf-8");
        response.setHeader(CONTENT_DISPOSITION, "attachment; filename=\"" + FILE_NAME + "\"");
        byte[] data = getData();

        response.getOutputStream().write(data);
    }

    @Override
    public byte[] getData() throws JsonProcessingException {
        CsvMapper mapper = new CsvMapper();
        mapper.disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
        CsvSchema schema = mapper.schemaFor(UserVehicleDTO.class);
        String data = mapper.writer(schema).writeValueAsString(userVehicleList);

        StringBuilder builder = new StringBuilder();

        builder.append(generateHeaders());
        builder.append(System.lineSeparator());
        builder.append(data);

        return builder.toString().getBytes();
    }


    private String generateHeaders() {
        StringBuilder builder = new StringBuilder();
        builder.append("id,vehicleId");
        return builder.toString();
    }
}
