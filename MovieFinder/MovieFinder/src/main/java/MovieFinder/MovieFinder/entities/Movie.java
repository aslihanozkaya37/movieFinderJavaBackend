package MovieFinder.MovieFinder.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="Movie")
@Data
public class Movie {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="id")
	Long id;
	@Column (name="name")
	String name;
	@Column (name="type")
	String type;
	@Column (name="year")
	String year;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getActors() {
		return actors;
	}
	public void setActors(String actors) {
		this.actors = actors;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	@Column (name="actors")
	String actors;
	@Column (name="imagePath")
	String imagePath;
	
}
