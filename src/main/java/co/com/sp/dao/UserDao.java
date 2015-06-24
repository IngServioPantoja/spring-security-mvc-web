package co.com.sp.dao;

import co.com.sp.domain.Usuario;

public interface UserDao {

	Usuario findByUserName(String username);

}