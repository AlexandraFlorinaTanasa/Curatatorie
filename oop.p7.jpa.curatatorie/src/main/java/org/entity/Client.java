package org.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.AUTO;
@Entity
public class Client {
	@Id
	@GeneratedValue(strategy = AUTO)
	private Integer id;
	private String nume;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNume() {
		return nume;
	}
	public void setNume(String nume) {
		this.nume = nume;
	}
	public Client(Integer id, String nume) {
		super();
		this.id = id;
		this.nume = nume;
	}
	public Client() {
		super();
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, nume);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return Objects.equals(id, other.id) && Objects.equals(nume, other.nume);
	}
	@Override
	public String toString() {
		return "Client [id=" + id + ", nume=" + nume + "]";
	}
	
}
