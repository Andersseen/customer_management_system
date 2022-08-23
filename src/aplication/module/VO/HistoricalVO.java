package aplication.module.VO;


public class HistoricalVO {
    private int id;
    private int id_customer;
    private String name;
    private String lastName;
    private String historical;


    public HistoricalVO(){
        this.id_customer = id_customer;
        this.name = name;
        this.lastName = lastName;
        this.historical = historical;

    }

	public HistoricalVO(int id_customer, String name, String lastName) {
        this.id_customer = id_customer;
		this.name = name;
		this.lastName = lastName;
		this.historical = historical;

	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public String getName() {
        return name;
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

    public String getHistorical() {
        return historical;
    }

    public void setHistorical(String historical) {
        this.historical = historical;
    }

    @Override
    public String toString() {
        return "HistoricalVO{" +
                "id=" + id +
                ", id_customer=" + id_customer +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
