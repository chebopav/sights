package com.project.entity.data.address;

import com.project.entity.data.Place;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(City.class)
public abstract class City_ {

	public static volatile SingularAttribute<City, Country> country;
	public static volatile SetAttribute<City, Place> places;
	public static volatile SingularAttribute<City, Long> id;

	public static final String COUNTRY = "country";
	public static final String PLACES = "places";
	public static final String ID = "id";

}

