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
public class Offices implements RowMapper<Offices> {

    private Long officeId;
    private String officeName;

    @Override
    public Offices mapRow(ResultSet rs, int rowNum) throws SQLException {
        BeanPropertyRowMapper<Offices> rowMapper = new BeanPropertyRowMapper<>(Offices.class);
        return rowMapper.mapRow(rs, rowNum);

    }
}
