package aplication.module.VO;

import java.sql.Date;

public class CustomerVO {
    private int id;
    private String name;
    private String lastName;
    private String sex;
    private Date birthday;
    private String phone;
    private String email;
    private String note;
    private Date date;

    public CustomerVO(){
        this.name = name;
        this.lastName = lastName;
        this.sex = sex;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
        this.note = note;
        this.date = date;
    }

	public CustomerVO( String name, String lastName, String sex, Date birthday, String phone, String email,
			String note, Date date) {

		this.name = name;
		this.lastName = lastName;
		this.sex = sex;
		this.birthday = birthday;
		this.phone = phone;
		this.email = email;
		this.note = note;
		this.date = date;
	}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday2) {
        this.birthday = birthday2;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "CustomerDAO [id=" + id + ", name=" + name + ", lastName=" + lastName + ", sex=" + sex + ", birthday="
                + birthday + ", phone=" + phone + ", email=" + email + ", note=" + note + ", date=" + date + "]";
    }


}
