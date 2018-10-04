/**
 * 
 */
package com.avaya.services.whitelist.db;

import javax.persistence.Column;

/**
 * @author jlramirez
 *
 */
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaya.collaboration.util.logger.Logger;

@Entity
@Table
public class Team {
	Logger logger = Logger.getLogger(getClass());	
	
@Id
// @GeneratedValue(strategy = GenerationType.AUTO) 	
	
	@Column(name = "membernum")
    private int membernum;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "email")
    private String email;
    @Column(name = "homephone")
    private String homephone;
    @Column(name = "team")
    private String team;
    @Column(name = "mobilecarrier")
    private String mobilecarrier;
    @Column(name = "zipcode")
    private String zipcode;
    @Column(name = "preference")
    private String preference;
    
    public Team(int membernum, String firstname, String lastname, 
    		String email, String homephone, String team, 
    		String mobilecarrier, String zipcode, String preference) 
            {
    
    	super();

    	logger.info("Team.java: Right at the entrance");
 
    	this.membernum = membernum;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.homephone = homephone;
        this.team = team;
        this.mobilecarrier = mobilecarrier;
        this.zipcode = zipcode;
        this.preference = preference;

		
        
        
     }
	public Team(){
		super();
		
		}
    
    
    public int getMembernum() {
		return membernum;
	}
	public void setMembernum(int membernum) {
		this.membernum = membernum;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHomephone() {
		return homephone;
	}
	public void setHomephone(String homephone) {
		this.homephone = homephone;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getMobilecarrier() {
		return mobilecarrier;
	}
	public void setMobilecarrier(String mobilecarrier) {
		this.mobilecarrier = mobilecarrier;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getPreference() {
		return preference;
	}
	public void setPreference(String preference) {
		this.preference = preference;
		logger.info("Team.java right after setPreference : " + this.preference);

	}
	@Override
	   public String toString() {		
	      return 
	      "Team [membernum =" + membernum + 
	      ", firstname=" + firstname + 
	      ", lastname=" + lastname + 
	      ", email=" + email + 
	      ", homephone =" + homephone + 
	      ", team =" + team + 
	      ", mobilecarrier =" + mobilecarrier + 
	      ", zipcode =" + zipcode +
	      ", preference =" + preference + 
	      "]";
	   }
	
}
