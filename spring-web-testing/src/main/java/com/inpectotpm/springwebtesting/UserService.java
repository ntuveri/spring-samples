package com.inpectotpm.springwebtesting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.text.html.Option;

import org.springframework.stereotype.Service;

@Service
public class UserService {

	private static List<User> users;

	static {
		users = new ArrayList<>();

		User gino = new User();
		gino.setId(1);
		gino.setUsername("gino");
		gino.setEmail("gino@inpeco.com");
		gino.setCreatedAt(new Date());
		users.add(gino);

		User mino = new User();
		mino.setId(2);
		mino.setUsername("mino");
		mino.setEmail("mino@inpeco.com");
		mino.setCreatedAt(new Date());
		users.add(mino);

		User lino = new User();
		lino.setId(3);
		lino.setUsername("mino");
		lino.setEmail("mino@inpeco.com");
		lino.setCreatedAt(new Date());
		lino.setDeleted(true);
		users.add(lino);
	}

	public <S extends User> S save(S user) {

		Optional<User> matchingUser = findById(user.getId());
		if(matchingUser.isPresent()) {
			users.remove(matchingUser);
		}
		users.add(user);
		return user;
	}

	public Optional<User> findById(Integer id) {
		List<User> matchingUsers = users.stream().filter(u -> u.getId() == id).collect(Collectors.toList());
		if(matchingUsers.size() > 1) {
			throw new IllegalArgumentException("Found more than one user having id " + id);
		}

		return matchingUsers.stream().findAny();
	}


	public Iterable<User> findAll() {
		return users;
	}

	public void deleteById(Integer id) {

		List<User> matchingUsers = users.stream().filter(u -> u.getId() == id).collect(Collectors.toList());
		if(matchingUsers.size() > 1) {
			throw new IllegalArgumentException("Found more than one user having id " + id);
		}

		if(matchingUsers.size() == 0) {
			throw new IllegalArgumentException("No users found having id " + id);
		}

		Optional<User> matchingUser = matchingUsers.stream().findAny();
		users.remove(matchingUser);
	}
}
