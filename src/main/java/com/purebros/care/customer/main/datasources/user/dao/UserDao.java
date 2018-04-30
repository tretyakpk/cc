package com.purebros.care.customer.main.datasources.user.dao;

import com.purebros.care.customer.main.datasources.user.dto.CSP;
import com.purebros.care.customer.main.datasources.user.dto.Role;
import com.purebros.care.customer.main.datasources.user.dto.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.GenericStoredProcedure;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedCaseInsensitiveMap;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Optional;


@Service
public class UserDao {

    private final DataSource userDataSource;

    @Autowired
    public UserDao(DataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    public Optional<User> findUser(String userName, String password){

        StoredProcedure procedure = new GenericStoredProcedure();
        procedure.setDataSource(userDataSource);
        procedure.setSql("User_GetData");
        procedure.declareParameter(new SqlParameter("in_UserName", Types.VARCHAR));
        procedure.declareParameter(new SqlParameter("in_Password", Types.VARCHAR));
        Map<String, Object> result = procedure.execute(userName, password);

        ArrayList res = (ArrayList) result.get("#result-set-1");

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

            user.setRoles(this.findUserRole(user.getId()));
            user.setCsps(this.findUserCsps(user.getId()));

            return Optional.of(user);
        }
    }

    private ArrayList<Role> findUserRole(Integer userId){

        StoredProcedure procedure = new GenericStoredProcedure();
        procedure.setDataSource(userDataSource);
        procedure.setSql("UserFunctions_GetData");
        procedure.declareParameter(new SqlParameter("in_IdUser", Types.INTEGER));
        Map<String, Object> result = procedure.execute(userId);

        ArrayList res = (ArrayList) result.get("#result-set-1");

        ArrayList<Role> roles = new ArrayList<>();

        res.forEach(v -> {
            Role role = Role.builder()
                    .role((String) ((LinkedCaseInsensitiveMap) v).get("RoleName")
                            .toString().toUpperCase().replace(' ', '_')
                    )
                    .build();
            roles.add(role);
        });

        return roles;
    }

    private ArrayList<CSP> findUserCsps(Integer userId) {

        StoredProcedure procedure = new GenericStoredProcedure();
        procedure.setDataSource(userDataSource);
        procedure.setSql("User_GetAllParameters");
        procedure.declareParameter(new SqlParameter("in_IdUser", Types.INTEGER));
        Map<String, Object> result = procedure.execute(userId);

        ArrayList res = (ArrayList) result.get("#result-set-1");

        ArrayList<CSP> csps = new ArrayList<>();

        res.forEach(v -> {
            CSP csp = CSP.builder()
                    .id(Integer.parseInt(((LinkedCaseInsensitiveMap) v).get("Value").toString()))
                    .build();
            csps.add(csp);
        });

        return csps;

    }
}
