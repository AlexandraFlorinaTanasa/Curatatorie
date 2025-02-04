package org.entity;

import java.util.Objects;

import javax.persistence.Entity;
@Entity
public class Curatatorie_ecologica extends Curatatorie {
	
	private Integer codCuratatorie;
	private String tipCuratare;
	public Integer getCodCuratatorie() {
		return codCuratatorie;
	}
	public void setCodCuratatorie(Integer codCuratatorie) {
		this.codCuratatorie = codCuratatorie;
	}
	public String getTipCuratare() {
		return tipCuratare;
	}
	public void setTipCuratare(String tipCuratare) {
		this.tipCuratare = tipCuratare;
	}
	public Curatatorie_ecologica(Integer cod, String denumire, Double tarif, Integer codCuratatorie,
			String tipCuratare) {
		super(cod, denumire, tarif);
		this.codCuratatorie = codCuratatorie;
		this.tipCuratare = tipCuratare;
	}
	public Curatatorie_ecologica(Integer codCuratatorie, String tipCuratare) {
		super();
		this.codCuratatorie = codCuratatorie;
		this.tipCuratare = tipCuratare;
	}
	public Curatatorie_ecologica(Integer cod, String denumire, Double tarif) {
		super(cod, denumire, tarif);
	}
	public Curatatorie_ecologica() {
		super();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(codCuratatorie, tipCuratare);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Curatatorie_ecologica other = (Curatatorie_ecologica) obj;
		return Objects.equals(codCuratatorie, other.codCuratatorie) && Objects.equals(tipCuratare, other.tipCuratare);
	}
	@Override
	public String toString() {
		return "Curatatorie_ecologica [codCuratatorie=" + codCuratatorie + ", tipCuratare=" + tipCuratare + "]";
	}
	
	
}