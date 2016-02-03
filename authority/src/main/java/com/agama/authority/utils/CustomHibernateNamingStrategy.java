package com.agama.authority.utils;

import org.hibernate.cfg.ImprovedNamingStrategy;


public class CustomHibernateNamingStrategy
	extends ImprovedNamingStrategy {

	private static final long serialVersionUID = 4674532118559124654L;

	@Override
	public String foreignKeyColumnName(
		String propertyName,
		String propertyEntityName,
		String propertyTableName,
		String referencedColumnName ) {

		return columnName( propertyName ) + "_id";
	}

	@Override
	public String classToTableName(
		String className ) {

		return toLowerCase( pluralize( super.classToTableName( className ) ) );
	}

	// 为了确保windows和Unix兼容，将所有表的名字小写
	private String toLowerCase(
		String name ) {

		return name.toLowerCase();
	}

	private String pluralize(
		String name ) {

		StringBuilder p = new StringBuilder( name );
		/*if ( name.endsWith( "y" ) ) {
			p.deleteCharAt( p.length() - 1 );
			p.append( "ies" );
		} else {
			p.append( 's' );
		}*/
		return p.toString();
	}
}