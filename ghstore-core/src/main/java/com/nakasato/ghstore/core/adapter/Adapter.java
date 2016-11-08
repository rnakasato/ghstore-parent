package com.nakasato.ghstore.core.adapter;

import java.util.List;

public interface Adapter < T, C > {

	public C adapt( T entity );

}
