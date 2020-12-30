package com.project.entity.data;

import com.project.entity.data.address.City;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Place.class)
public abstract class Place_ {

	public static volatile SingularAttribute<Place, LifeHack> lifeHack;
	public static volatile SingularAttribute<Place, City> city;
	public static volatile SingularAttribute<Place, Long> id;

	public static final String LIFE_HACK = "lifeHack";
	public static final String CITY = "city";
	public static final String ID = "id";

}

