package com.nakasato.ghtstore.core.business;

import org.apache.commons.lang3.StringUtils;

import com.nakasato.ghstore.domain.AbstractDomainEntity;

/**
 * 
 * @author rafae Descrição:
 */
public abstract class FieldsValidator<T extends AbstractDomainEntity> extends Validator<T> {
	private static final String STANDARD_MESSAGE = "O(s) campo(s) é(são) obrigatório(s): \n";
	
	private StringBuilder sbMessaage;
	
	protected void appendMsg(String message) {
		if (sbMessaage == null) {
			sbMessaage = new StringBuilder();
			sbMessaage.append(STANDARD_MESSAGE);
		}
		if (sbMessaage.length() > STANDARD_MESSAGE.length()) {
			sbMessaage.append(", ");
		}
		sbMessaage.append(message);
	}

	protected String getMessage() {
		String msg = null;
		boolean isNull = sbMessaage == null;
		boolean isEmpty = !isNull && StringUtils.isEmpty(sbMessaage.toString());
		
		if(!isNull && !isEmpty){
			sbMessaage.append(".");
			msg = sbMessaage.toString();
		}

		return msg;
	}
}
