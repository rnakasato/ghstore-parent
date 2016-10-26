package com.nakasato.ghstore.web.mb.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.nakasato.ghstore.core.util.FormatUtils;
import com.nakasato.ghstore.domain.product.Tag;

@ManagedBean(name = "formatterMB")
@SessionScoped
public class FormatterMB {
	public String formatCurrency(Double price) {
		return FormatUtils.formatToCurrency(price);
	}

	public String formatTagList(List<Tag> tagList) {
		if (tagList != null) {
			StringBuilder sb = new StringBuilder();
			int size = tagList.size();
			int cont = 1;
			for (Tag tag : tagList) {
				sb.append(tag.getDescription());
				if (cont == tagList.size()) {
					sb.append(".");
				} else {
					sb.append(", ");
				}
				cont++;
			}
			return sb.toString();
		}
		return "";
	}

	public String formatDate(Date date) {
		String formattedDate = null;
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			formattedDate = sdf.format(date);
		}
		return formattedDate;
	}
	
	public String formatCEP(String value){
			return FormatUtils.formatString(value, "#####-###");
	}

	
	
}
