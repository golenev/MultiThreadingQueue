package jdbc;

import models.Offices;

import java.util.List;

import static jdbc.DatabaseConfigurator.getJdbcTemplate;

public class Queries {

    public List<Offices> getListOffices () {
        return getJdbcTemplate().query("select * from offices", new Offices());
    }

    public void insertIntoOffices (Offices office) {
         getJdbcTemplate()
                 .update("""
                         insert into offices (office_id, office_name)
                         values (%d , '%s')
                         """.formatted(office.getOfficeId(), office.getOfficeName()));
    }

}
