package com.nakasato.ghstore.core.adapter;

public interface Adapter < T, C > {

	public C adapt( T entity );

}
