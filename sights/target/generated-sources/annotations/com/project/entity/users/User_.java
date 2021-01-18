package com.project.entity.users;

import com.project.entity.data.Comment;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, String> password;
	public static volatile SetAttribute<User, Comment> comments;
	public static volatile SingularAttribute<User, String> phone;
	public static volatile SetAttribute<User, Role> roles;
	public static volatile SingularAttribute<User, String> name;
	public static volatile SingularAttribute<User, Integer> id;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, String> username;

	public static final String PASSWORD = "password";
	public static final String COMMENTS = "comments";
	public static final String PHONE = "phone";
	public static final String ROLES = "roles";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String EMAIL = "email";
	public static final String USERNAME = "username";

}

