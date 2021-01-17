package com.project.entity.data.address;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Country.class)
public abstract class Country_ {

	public static volatile SetAttribute<Country, City> cities;
	public static volatile SingularAttribute<Country, String> name;
	public static volatile SingularAttribute<Country, Integer> id;

	public static final String CITIES = "cities";
	public static final String NAME = "name";
	public static final String ID = "id";

}

