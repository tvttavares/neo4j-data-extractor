package com.tvttavares.neo4jdataextractor.format;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.tvttavares.neo4jdataextractor.neo4j.model.UserPaymentDTO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

public class UserPaymentCsvDataFormat implements DataFormat {

    private static final String FILE_NAME = "user_paymentId.csv";

    List<UserPaymentDTO> userPaymentList;

    public UserPaymentCsvDataFormat(List<UserPaymentDTO> userPaymentList) {
        this.userPaymentList = userPaymentList;
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
        CsvSchema schema = mapper.schemaFor(UserPaymentDTO.class);
        String data = mapper.writer(schema).writeValueAsString(userPaymentList);

        StringBuilder builder = new StringBuilder();

        builder.append(generateHeaders());
        builder.append(System.lineSeparator());
        builder.append(data);

        return builder.toString().getBytes();
    }


    private String generateHeaders() {
        StringBuilder builder = new StringBuilder();
        builder.append("id,paymentId");
        return builder.toString();
    }
}
