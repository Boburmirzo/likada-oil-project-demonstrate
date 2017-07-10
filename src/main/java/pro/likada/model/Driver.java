package pro.likada.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by abuca on 04.03.17.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="DRIVERS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Driver {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="TITLE")
    private String title;

    @Column(name="FIRST_NAME")
    private String firstName;

    @Column(name="LAST_NAME")
    private String lastName;

    @Column(name="PATRONYMIC")
    private String patronymic;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DATE_OF_BIRTH")
    private Date dateOfBirth;

    @Column(name="DOC_SERIAL")
    private String docSerial;

    @Column(name="DOC_NUMBER")
    private String docNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DOC_GIVEN_DATE")
    private Date docGivenDate;

    @Column(name="DOC_GIVEN_FROM")
    private String docGivenFrom;

    @Column(name="DOC_CODE")
    private String docCode;

    @Column(name="DOC_DESCRIPTION")
    private String docDescription;

    @Column(name="PHONE_1")
    private String phone1;

    @Column(name="PHONE_2")
    private String phone2;

    @Column(name="PHONE_EMERGENCY")
    private String phoneEmergency;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "drivers")
    private Set<Vehicle> vehicles;

    @OneToMany(mappedBy = "driver")
    private Set<Order> ordersOfDriver;

    @Override
    public String toString() {
        return "DRIVER[" +
                "id=" + id +
                ", TITLE='" + title +
                ", FIRST_NAME='" + firstName +
                ", LAST_NAME='" + lastName +
                ", PATRONYMIC='" + patronymic +
                ", DATE_OF_BIRTH=" + dateOfBirth +
                ", DOC_SERIAL='" + docSerial +
                ", DOC_NUMBER='" + docNumber +
                ", DOC_GIVEN_DATE=" + docGivenDate +
                ", DOC_GIVEN_FROM='" + docGivenFrom +
                ", DOC_CODE='" + docCode +
                ", DOC_DESCRIPTION='" + docDescription +
                ", PHONE_1='" + phone1 +
                ", PHONE_2='" + phone2 +
                ", PHONE_EMERGENCY='" + phoneEmergency +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Driver)) return false;

        Driver driver = (Driver) o;

        if (id != null ? !id.equals(driver.id) : driver.id != null) return false;
        if (title != null ? !title.equals(driver.title) : driver.title != null) return false;
        if (firstName != null ? !firstName.equals(driver.firstName) : driver.firstName != null) return false;
        if (lastName != null ? !lastName.equals(driver.lastName) : driver.lastName != null) return false;
        if (patronymic != null ? !patronymic.equals(driver.patronymic) : driver.patronymic != null) return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(driver.dateOfBirth) : driver.dateOfBirth != null) return false;
        if (docSerial != null ? !docSerial.equals(driver.docSerial) : driver.docSerial != null) return false;
        if (docNumber != null ? !docNumber.equals(driver.docNumber) : driver.docNumber != null) return false;
        if (docGivenDate != null ? !docGivenDate.equals(driver.docGivenDate) : driver.docGivenDate != null)
            return false;
        if (docGivenFrom != null ? !docGivenFrom.equals(driver.docGivenFrom) : driver.docGivenFrom != null)
            return false;
        if (docCode != null ? !docCode.equals(driver.docCode) : driver.docCode != null) return false;
        if (docDescription != null ? !docDescription.equals(driver.docDescription) : driver.docDescription != null)
            return false;
        if (phone1 != null ? !phone1.equals(driver.phone1) : driver.phone1 != null) return false;
        if (phone2 != null ? !phone2.equals(driver.phone2) : driver.phone2 != null) return false;
        if (phoneEmergency != null ? !phoneEmergency.equals(driver.phoneEmergency) : driver.phoneEmergency != null)
            return false;
        return description != null ? description.equals(driver.description) : driver.description == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (patronymic != null ? patronymic.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (docSerial != null ? docSerial.hashCode() : 0);
        result = 31 * result + (docNumber != null ? docNumber.hashCode() : 0);
        result = 31 * result + (docGivenDate != null ? docGivenDate.hashCode() : 0);
        result = 31 * result + (docGivenFrom != null ? docGivenFrom.hashCode() : 0);
        result = 31 * result + (docCode != null ? docCode.hashCode() : 0);
        result = 31 * result + (docDescription != null ? docDescription.hashCode() : 0);
        result = 31 * result + (phone1 != null ? phone1.hashCode() : 0);
        result = 31 * result + (phone2 != null ? phone2.hashCode() : 0);
        result = 31 * result + (phoneEmergency != null ? phoneEmergency.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String middleName) {
        this.patronymic = middleName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDocSerial() {
        return docSerial;
    }

    public void setDocSerial(String docSerial) {
        this.docSerial = docSerial;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public Date getDocGivenDate() {
        return docGivenDate;
    }

    public void setDocGivenDate(Date docGivenDate) {
        this.docGivenDate = docGivenDate;
    }

    public String getDocGivenFrom() {
        return docGivenFrom;
    }

    public void setDocGivenFrom(String docGivenFrom) {
        this.docGivenFrom = docGivenFrom;
    }

    public String getDocCode() {
        return docCode;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

    public String getDocDescription() {
        return docDescription;
    }

    public void setDocDescription(String docDescription) {
        this.docDescription = docDescription;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getPhoneEmergency() {
        return phoneEmergency;
    }

    public void setPhoneEmergency(String phoneEmergency) {
        this.phoneEmergency = phoneEmergency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Vehicle> getVehicleList(){
        if(vehicles != null){
            return new ArrayList<>(vehicles);
        }
        else{
            return null;
        }
    }

    public void setVehicleList(List<Vehicle> vehicleList){
        if(vehicleList != null) {
            this.vehicles = new HashSet<>(vehicleList);
        }
        else {
            this.vehicles = null;
        }
    }

    public Set<Order> getOrdersOfDriver() {
        return ordersOfDriver;
    }

    public void setOrdersOfDriver(Set<Order> ordersOfDriver) {
        this.ordersOfDriver = ordersOfDriver;
    }

    public String getStringOfVehicleGovNumbers(){
        StringBuilder sb = new StringBuilder();
        List<String> govNumberList = getVehicleList().stream()
                .map(vehicle -> vehicle.getTruck().getGovNumber())
                .sorted()
                .collect(Collectors.toList());
        for(String govNumber : govNumberList){
            sb.append(govNumber);
            sb.append(',');
        }
        if(sb.length()>0){
            sb.delete(sb.length()-1,sb.length());
        }
        return sb.toString();
    }
}
