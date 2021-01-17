package com.project.entity.data;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Museum.class)
public abstract class Museum_ extends com.project.entity.data.BaseData_ {

	public static volatile SingularAttribute<Museum, String> phone;
	public static volatile SingularAttribute<Museum, String> fullAddress;
	public static volatile SetAttribute<Museum, NeedDate> dates;
	public static volatile SingularAttribute<Museum, String> email;

	public static final String PHONE = "phone";
	public static final String FULL_ADDRESS = "fullAddress";
	public static final String DATES = "dates";
	public static final String EMAIL = "email";

}

