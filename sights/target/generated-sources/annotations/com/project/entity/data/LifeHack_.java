package com.project.entity.data;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(LifeHack.class)
public abstract class LifeHack_ {

	public static volatile SingularAttribute<LifeHack, Long> id;
	public static volatile SingularAttribute<LifeHack, String> text;
	public static volatile SingularAttribute<LifeHack, Place> place;

	public static final String ID = "id";
	public static final String TEXT = "text";
	public static final String PLACE = "place";

}

