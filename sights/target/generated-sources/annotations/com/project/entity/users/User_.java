package com.project.entity.users;

import com.project.entity.users.User.Status;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, String> photoURL;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, String> phone;
	public static volatile SingularAttribute<User, String> name;
	public static volatile SingularAttribute<User, Boolean> isOnline;
	public static volatile SingularAttribute<User, Long> id;
	public static volatile SingularAttribute<User, String> login;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, Status> status;

	public static final String PHOTO_UR_L = "photoURL";
	public static final String PASSWORD = "password";
	public static final String PHONE = "phone";
	public static final String NAME = "name";
	public static final String IS_ONLINE = "isOnline";
	public static final String ID = "id";
	public static final String LOGIN = "login";
	public static final String EMAIL = "email";
	public static final String STATUS = "status";

}

