package com.project.entity.data.address;

import com.project.entity.data.BaseData;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(City.class)
public abstract class City_ {

	public static volatile SingularAttribute<City, Country> country;
	public static volatile SetAttribute<City, BaseData> baseData;
	public static volatile SingularAttribute<City, String> name;
	public static volatile SingularAttribute<City, Long> id;

	public static final String COUNTRY = "country";
	public static final String BASE_DATA = "baseData";
	public static final String NAME = "name";
	public static final String ID = "id";

}

