package co.com.psl.training.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter @NoArgsConstructor(access=AccessLevel.PROTECTED) @ToString
public class IdType {

	@Id
	private String code;
	
	private String name;
	
	public IdType(String code, String name) {
		this.code = code;
		this.name = name;
	}
}
