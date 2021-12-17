package co.zip.candidate.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Describes a user on the zip co. platform
 */
@Entity
@Table(name="user")
public class User extends PersistableEntity {

    private String email;
    
    @Column(name="monthly_salary", precision=12, scale=2)
    private Float monthlySalary;
    
    @Column(name="monthly_expenses", precision=12, scale=2)
    private Float monthlyExpenses;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Float getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(Float monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    public Float getMonthlyExpenses() {
        return monthlyExpenses;
    }

    public void setMonthlyExpenses(Float monthlyExpenses) {
        this.monthlyExpenses = monthlyExpenses;
    }
    
}
