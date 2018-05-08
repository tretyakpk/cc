package com.purebros.care.customer.main.service;

import com.purebros.care.customer.main.dto.CSP;
import com.purebros.care.customer.main.dto.Role;
import com.purebros.care.customer.main.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

@Service
public class UserServiceImpl implements UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String DATA_STORAGE = "#result-set-1";

    private final DataSource userDataSource;

    private final Environment env;

    @Autowired
    public UserServiceImpl(DataSource userDataSource, Environment env) {
        this.userDataSource = userDataSource;
        this.env = env;
    }

    public User findUser(String userName, String password){
        StoredProcedure procedure = new GenericStoredProcedure();
        procedure.setDataSource(userDataSource);
        procedure.setSql("User_GetData");
        procedure.declareParameter(new SqlParameter("in_UserName", Types.VARCHAR));
        procedure.declareParameter(new SqlParameter("in_Password", Types.VARCHAR));
        Map<String, Object> result = procedure.execute(userName, password);
        ArrayList res = (ArrayList) result.get(DATA_STORAGE);

        return getUser(res);
    }

    private User getUser(ArrayList res) {
        if(res.isEmpty()) {
            return null;
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

            user.setRoles(this.getUserRoles(user));
            //user.setCsps(this.getUserCsps(user));

            return user;
        }
    }


    private ArrayList<Role> getUserRoles(User user){

        StoredProcedure procedure = new GenericStoredProcedure();
        procedure.setDataSource(userDataSource);
        procedure.setSql("UserFunctions_GetDataByIdTool");
        procedure.declareParameter(new SqlParameter("in_IdUser", Types.INTEGER));
        procedure.declareParameter(new SqlParameter("in_IdTool", Types.INTEGER));
        Map<String, Object> result = procedure.execute(user.getId(), env.getProperty("tool-id"));

        ArrayList res = (ArrayList) result.get(DATA_STORAGE);

        ArrayList<Role> roles = new ArrayList<>();

        res.forEach(v -> {
            Role role = Role.builder()
                    .role((String) ((LinkedCaseInsensitiveMap) v).get("FunctionName")
                            .toString().toUpperCase().replace(' ', '_')
                    )
                    .build();
            if(!roles.contains(role))
                roles.add(role);
        });

        return roles;
    }

    private ArrayList<CSP> getUserCsps(User user) {

        StoredProcedure procedure = new GenericStoredProcedure();
        procedure.setDataSource(userDataSource);
        procedure.setSql("User_GetAllParameters");
        procedure.declareParameter(new SqlParameter("in_IdUser", Types.INTEGER));
        Map<String, Object> result = procedure.execute(user.getId());
        ArrayList res = (ArrayList) result.get(DATA_STORAGE);
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
