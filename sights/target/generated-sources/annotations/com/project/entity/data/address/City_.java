package com.project.entity.data.address;

import com.project.entity.data.Excursion;
import com.project.entity.data.Museum;
import com.project.entity.data.Sight;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(City.class)
public abstract class City_ {

	public static volatile SingularAttribute<City, Country> country;
	public static volatile SetAttribute<City, Museum> museums;
	public static volatile SetAttribute<City, Sight> sights;
	public static volatile SingularAttribute<City, String> name;
	public static volatile SetAttribute<City, Excursion> excursions;
	public static volatile SingularAttribute<City, Long> id;

	public static final String COUNTRY = "country";
	public static final String MUSEUMS = "museums";
	public static final String SIGHTS = "sights";
	public static final String NAME = "name";
	public static final String EXCURSIONS = "excursions";
	public static final String ID = "id";

}

