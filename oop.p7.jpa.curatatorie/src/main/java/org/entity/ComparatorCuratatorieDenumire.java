package org.entity;

import java.util.Comparator;

public class ComparatorCuratatorieDenumire implements Comparator<Curatatorie> {
	
	
	@Override
	public int compare(Curatatorie c1,Curatatorie c2) {
		return c1.getDenumire().compareTo(c2.getDenumire());
	}
	
}

