package com.purebros.care.customer.main.datasources.user.dao;

import com.purebros.care.customer.main.datasources.user.dto.User;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.GenericStoredProcedure;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
public class UserDao {

    private DataSource userDataSource;

    public void setDataSource(DataSource dataSource) {
        this.userDataSource = dataSource;
    }

    public Optional<User> findUser(String userName, String password){

        StoredProcedure procedure = new GenericStoredProcedure();
        procedure.setDataSource(userDataSource);
        procedure.setSql("User_GetData");
        procedure.declareParameter(new SqlParameter("in_UserName", Types.VARCHAR));
        procedure.declareParameter(new SqlParameter("in_Password", Types.VARCHAR));
        Map<String, Object> result = procedure.execute(userName, password);


        // Get #result-set-1 as result to parse it because we can't use method get(key)
        // if stored procedure isn't return value as OUT parameter
        ArrayList res = (ArrayList) result.get("#result-set-1");

        // if user not found
        if(res.isEmpty()) {
            return Optional.empty();
        } else {
            Map userData = (Map) res.get(0);
            User user = User.builder()
                    .id((Integer)      userData.get("IdUser"))
                    .name((String)     userData.get("UserName"))
                    .company((String)  userData.get("CompanyName"))
                    .email((String)    userData.get("Email"))
                    .number((String)   userData.get("String"))
                    .created_at((Date) userData.get("InsertDate"))
                    .build();

            return Optional.of(user);
        }
    }
}
