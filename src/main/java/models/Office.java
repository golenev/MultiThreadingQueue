package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Office implements RowMapper<Office> {

    private Long officeId;
    private String officeName;

    @Override
    public Office mapRow(ResultSet rs, int rowNum) throws SQLException {
        BeanPropertyRowMapper<Office> rowMapper = new BeanPropertyRowMapper<>(Office.class);
        return rowMapper.mapRow(rs, rowNum);
    }
}
