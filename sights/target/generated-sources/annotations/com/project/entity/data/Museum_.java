package com.project.entity.data;

import java.time.DayOfWeek;
import java.util.Map;
import javax.annotation.Generated;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Museum.class)
public abstract class Museum_ extends com.project.entity.data.BaseData_ {

	public static volatile SetAttribute<Museum, DayOfWeek> workDays;
	public static volatile SingularAttribute<Museum, String> phone;
	public static volatile SingularAttribute<Museum, String> name;
	public static volatile SingularAttribute<Museum, String> fullAddress;
	public static volatile SingularAttribute<Museum, String> email;
	public static volatile MapAttribute<Museum, DayOfWeek, Map> priceList;

	public static final String WORK_DAYS = "workDays";
	public static final String PHONE = "phone";
	public static final String NAME = "name";
	public static final String FULL_ADDRESS = "fullAddress";
	public static final String EMAIL = "email";
	public static final String PRICE_LIST = "priceList";

}

