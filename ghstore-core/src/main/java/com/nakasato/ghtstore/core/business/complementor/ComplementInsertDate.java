package com.nakasato.ghtstore.core.business.complementor;

import java.util.Date;

import com.nakasato.ghstore.domain.AbstractDomainEntity;
import com.nakasato.ghtstore.core.business.Complementor;

public class ComplementInsertDate extends Complementor {

	@ Override
	public String complement( AbstractDomainEntity entity ) {
		entity.setInsertDate( new Date() );
		return null;
	}

}
