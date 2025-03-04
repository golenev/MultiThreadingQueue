package jdbc;

import io.qameta.allure.Step;
import models.Offices;

import java.security.PublicKey;
import java.util.List;
import java.util.Random;

import static jdbc.DatabaseConfigurator.getJdbcTemplate;
import static util.SleepUtil.sleepRandomTime;

public class Queries {

    public List<Offices> getListOffices() {
        return getJdbcTemplate().query("select * from offices", new Offices());
    }

    @Step("Добавляем в базу офис {office}")
    public void insertIntoOffices(Offices office) {
        sleepRandomTime();
        getJdbcTemplate()
                .update("""
                        insert into offices (office_id, office_name)
                        values (? , ?)
                        """, office.getOfficeId(), office.getOfficeName());
    }

    @Step("Удаляем из базы офис {office}")
    public void deleteOfficeById(Long officeId) {
        sleepRandomTime();
        getJdbcTemplate()
                .update("delete from offices where office_id = %d".formatted(officeId));
    }

}
