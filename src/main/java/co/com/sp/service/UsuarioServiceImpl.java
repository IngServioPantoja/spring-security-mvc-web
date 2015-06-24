package co.com.sp.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.com.sp.dao.UserDao;
import co.com.sp.domain.UsuarioRol;

@Service
@Transactional
public class UsuarioServiceImpl implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		// Programmatic transaction management
		/*
		return transactionTemplate.execute(new TransactionCallback<UserDetails>() {

			public UserDetails doInTransaction(TransactionStatus status) {
				com.mkyong.users.model.User user = userDao.findByUserName(username);
				List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole());

				return buildUserForAuthentication(user, authorities);
			}

		});*/
		
		co.com.sp.domain.Usuario user = userDao.findByUserName(username);
		List<GrantedAuthority> authorities = buildUserAuthority(user.getUsuarioRoles());

		return buildUserForAuthentication(user, authorities);
		

	}

	// Converts com.mkyong.users.model.User user to
	// org.springframework.security.core.userdetails.User
	private User buildUserForAuthentication(co.com.sp.domain.Usuario user, List<GrantedAuthority> authorities) {
		return new User(user.getUsername(), user.getPassword(), user.isActivo(), true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(List<UsuarioRol> userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		for (UsuarioRol userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRol().getSigla()));
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}