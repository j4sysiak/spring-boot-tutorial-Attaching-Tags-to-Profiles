package pl.jaceksysiak.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.owasp.html.PolicyFactory;
 

@Entity
@Table(name="profile")
public class Profile {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@OneToOne(targetEntity=SiteUser.class)
	@JoinColumn(name="user_id", nullable=false)
	private SiteUser user;
	
	@Column(name="about", length=500)
	@Size(max=500, message="{editprofile.about.size}")
	private String about;
	
	@Column(name = "photo_directory", length = 20)
	private String photoDirectory;

	@Column(name = "photo_name", length = 20)
	private String photoName;

	@Column(name = "photo_extension", length = 5)
	private String photoExtension;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="profile_interests", 
					joinColumns={ @JoinColumn(name="profile_id") }, 
							inverseJoinColumns = { @JoinColumn(name="interest_id") } )
	@OrderColumn(name="display_order")
	private Set<Interest> interests;	
	
	public Profile() {	
	}
	 
	public Profile(SiteUser user) {
		this.user = user;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SiteUser getUser() {
		return user;
	}

	public void setUser(SiteUser user) {
		this.user = user;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}
	

	public String getPhotoDirectory() {
		return photoDirectory;
	}

	public void setPhotoDirectory(String photoDirectory) {
		this.photoDirectory = photoDirectory;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public String getPhotoExtension() {
		return photoExtension;
	}

	public void setPhotoExtension(String photoExtension) {
		this.photoExtension = photoExtension;
	}
	
	
	public Set<Interest> getInterests() {
		return interests;
	}

	public void setInterests(Set<Interest> interests) {
		this.interests = interests;
	}

	/* Create a profile that is suitable for displaying via JSP */
	public void safeCopyFrom(Profile other) {
		
		if (other.about != null) {
			this.about = other.about;
		}
		
		if(other.interests != null) {
			this.interests = other.interests;
		}
	}

	/* Create a profile that is suitable for saving */
	public void safeMergeFrom(Profile webProfile, PolicyFactory htmlPolicy) {
		if (webProfile.about != null) {
			this.about = htmlPolicy.sanitize(webProfile.about);
		}
	}
	
	
	public void setPhotoDetails(FileInfo info) {
		photoDirectory = info.getSubDirectory();
		photoExtension = info.getExtension();
		photoName = info.getBasename();
	}
	
	public Path getPhoto(String baseDirectory) {
		
		if(photoName == null) {
			return null;
		}
		
		return Paths.get(baseDirectory, photoDirectory, photoName + "." +  photoExtension);
	}

	public void addInterest(Interest interest) {
		interests.add(interest);
	}

	public void removeInterest(String interestName) {
		interests.remove(new Interest(interestName));
	}
 
}













































